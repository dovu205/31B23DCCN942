package DAO;

import java.sql.SQLException;
import java.time.LocalDate;
import model.Customer2;

public class CustomerDAO2 extends DAO2 {
    public Customer2 getCustomerByUsername(String username) throws SQLException {
        String sql = """
                SELECT c.username, c.customerCode, m.fullName, m.dateOfBirth,
                       m.address, m.email, m.phoneNumber
                FROM tblCustomer2 c JOIN tblMember2 m ON m.username = c.username
                WHERE c.username = ?
                """;
        try (var connection = openConnection(); var statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (var row = statement.executeQuery()) {
                if (!row.next()) return null;
                var customer = new Customer2();
                customer.setUsername(row.getString("username"));
                customer.setCustomerCode(row.getString("customerCode"));
                customer.setFullName(row.getString("fullName"));
                customer.setDateOfBirth(row.getObject("dateOfBirth", LocalDate.class));
                customer.setAddress(row.getString("address"));
                customer.setEmail(row.getString("email"));
                customer.setPhoneNumber(row.getString("phoneNumber"));
                return customer;
            }
        }
    }

    public boolean updatePersonalInformation(Customer2 customer) throws SQLException {
        String sql = """
                UPDATE tblMember2 m JOIN tblCustomer2 c ON c.username = m.username
                SET m.fullName = ?, m.dateOfBirth = ?, m.address = ?, m.email = ?, m.phoneNumber = ?
                WHERE c.username = ?
                """;
        try (var connection = openConnection(); var statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getFullName());
            statement.setObject(2, customer.getDateOfBirth());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getPhoneNumber());
            statement.setString(6, customer.getUsername());
            return statement.executeUpdate() == 1;
        }
    }
}
