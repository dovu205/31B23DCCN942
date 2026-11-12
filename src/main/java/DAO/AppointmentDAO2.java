package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.Appointment2;
import model.Customer2;

public class AppointmentDAO2 extends DAO2 {
    private final int capacity = Integer.parseInt(setting("GARAMAN_SLOT_CAPACITY", "1"));

    public boolean checkAppointmentTime(LocalDate appointmentDate, String appointmentTime)
            throws SQLException {
        var slot = appointmentDate.atTime(LocalTime.parse(appointmentTime));
        try (var connection = openConnection()) {
            return isAvailable(connection, slot);
        }
    }

    public boolean createAppointment(Appointment2 appointment) throws SQLException {
        var slot = appointment.getAppointmentDateTime();
        try (var connection = openConnection()) {
            String lock = connection.getCatalog() + ":slot:" + slot;
            try (var statement = connection.prepareStatement("SELECT GET_LOCK(?, 10)")) {
                statement.setString(1, lock);
                try (var row = statement.executeQuery()) {
                    if (!row.next() || row.getInt(1) != 1) throw new SQLException("Không lấy được khóa khung giờ");
                }
            }
            // Khóa theo khung giờ được giữ đến sau commit, rồi giải phóng khi đóng kết nối.
            connection.setAutoCommit(false);
            try {
                if (!isAvailable(connection, slot)) {
                    connection.rollback();
                    return false;
                }
                insertWithNewCode(connection, appointment);
                connection.commit();
                return true;
            } catch (SQLException | RuntimeException error) {
                connection.rollback();
                throw error;
            }
        }
    }

    private boolean isAvailable(Connection connection, LocalDateTime slot) throws SQLException {
        if (capacity < 1 || !slot.isAfter(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))) return false;
        String sql = """
                SELECT COUNT(*) FROM tblAppointment2
                WHERE appointmentDateTime = ? AND status <> 'CANCELLED'
                """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setObject(1, slot);
            try (var row = statement.executeQuery()) {
                row.next();
                return row.getInt(1) < capacity;
            }
        }
    }

    private void insertWithNewCode(Connection connection, Appointment2 appointment) throws SQLException {
        String sql = """
                INSERT INTO tblAppointment2
                (appointmentCode, appointmentDateTime, note, status, customerUsername)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setObject(2, appointment.getAppointmentDateTime());
            statement.setString(3, appointment.getNote());
            statement.setString(4, appointment.getStatus());
            statement.setString(5, appointment.getCustomer().getUsername());
            for (int attempt = 0; attempt < 3; attempt++) {
                String code = "LH-" + UUID.randomUUID().toString().replace("-", "").substring(0, 20);
                statement.setString(1, code);
                try {
                    statement.executeUpdate();
                    appointment.setAppointmentCode(code);
                    return;
                } catch (SQLException error) {
                    if (error.getErrorCode() != 1062 || attempt == 2) throw error;
                }
            }
        }
    }

    public List<Appointment2> getAppointmentsByCustomer(Customer2 customer) throws SQLException {
        var appointments = new ArrayList<Appointment2>();
        String sql = """
                SELECT id, appointmentCode, appointmentDateTime, note, status
                FROM tblAppointment2 WHERE customerUsername = ? ORDER BY appointmentDateTime DESC
                """;
        try (var connection = openConnection(); var statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getUsername());
            try (var rows = statement.executeQuery()) {
                while (rows.next()) {
                    var appointment = new Appointment2();
                    appointment.setId(rows.getInt("id"));
                    appointment.setAppointmentCode(rows.getString("appointmentCode"));
                    appointment.setAppointmentDateTime(rows.getObject("appointmentDateTime", LocalDateTime.class));
                    appointment.setNote(rows.getString("note"));
                    appointment.setStatus(rows.getString("status"));
                    appointment.setCustomer(customer);
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }
}
