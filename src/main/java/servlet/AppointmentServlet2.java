package servlet;

import DAO.AppointmentDAO2;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Appointment2;
import model.Customer2;

@WebServlet("/appointment")
public class AppointmentServlet2 extends WebSupport {
    private final AppointmentDAO2 dao = new AppointmentDAO2();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        handle(req, res, "CUSTOMER", () -> {
            String page = "home".equals(req.getParameter("action"))
                    ? "customer/CustomerMainView2" : "customer/AppointmentView2";
            view(req, res, page);
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        handle(req, res, "CUSTOMER", () -> {
            if ("create".equals(req.getAttribute("garamanInternalAction"))) {
                createAppointment(req, res);
            } else if ("checkTime".equals(req.getParameter("action"))) {
                checkTime(req, res);
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        });
    }

    private void checkTime(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, SQLException {
        LocalDate date;
        LocalTime time;
        String note;
        try {
            date = date(req, "appointmentDate");
            time = LocalTime.parse(text(req, "appointmentTime", 5));
            note = text(req, "note", 1000);
        } catch (java.time.DateTimeException | IllegalArgumentException error) {
            req.setAttribute("message", "Ngày hoặc giờ đặt lịch không hợp lệ.");
            view(req, res, "customer/AppointmentView2");
            return;
        }
        if (!dao.checkAppointmentTime(date, time.toString())) {
            unavailable(req, res);
            return;
        }
        req.getSession().setAttribute("appointmentDateTime", date.atTime(time));
        req.getSession().setAttribute("appointmentNote", note);
        forward(req, res, "/customer", "load");
    }

    private void createAppointment(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, SQLException {
        var session = req.getSession();
        var slot = (LocalDateTime) session.getAttribute("appointmentDateTime");
        var customer = (Customer2) req.getAttribute("updatedCustomer");
        if (slot == null || customer == null) {
            unavailable(req, res);
            return;
        }
        if (!dao.checkAppointmentTime(slot.toLocalDate(), slot.toLocalTime().toString())) {
            unavailable(req, res);
            return;
        }
        var appointment = new Appointment2();
        appointment.setCustomer(customer);
        appointment.setAppointmentDateTime(slot);
        appointment.setNote((String) session.getAttribute("appointmentNote"));
        appointment.setStatus("PENDING");
        if (!dao.createAppointment(appointment)) {
            unavailable(req, res);
            return;
        }
        session.removeAttribute("appointmentDateTime");
        session.removeAttribute("appointmentNote");
        req.setAttribute("appointment", appointment);
        view(req, res, "customer/SaveAppointmentView2");
    }

    private void unavailable(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        req.getSession().removeAttribute("appointmentDateTime");
        req.getSession().removeAttribute("appointmentNote");
        req.setAttribute("message", "Khung giờ không còn khả dụng. Vui lòng chọn lại.");
        view(req, res, "customer/AppointmentView2");
    }
}
