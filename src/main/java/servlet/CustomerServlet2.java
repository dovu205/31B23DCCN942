package servlet;

import DAO.CustomerDAO2;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/customer")
public class CustomerServlet2 extends WebSupport {
    private final CustomerDAO2 dao = new CustomerDAO2();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        handle(req, res, "CUSTOMER", () -> loadCustomer(req, res));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        handle(req, res, "CUSTOMER", () -> {
            if ("load".equals(req.getAttribute("garamanInternalAction"))) {
                loadCustomer(req, res);
            } else if ("update".equals(req.getParameter("action"))) {
                update(req, res);
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        });
    }

    private void loadCustomer(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, SQLException {
        var customer = dao.getCustomerByUsername((String) req.getSession().getAttribute("username"));
        if (customer == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("customer", customer);
        view(req, res, "customer/PersonalInformationView2");
    }

    private void update(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException, SQLException {
        if (req.getSession().getAttribute("appointmentDateTime") == null) {
            res.sendRedirect(req.getContextPath() + "/appointment");
            return;
        }
        var customer = dao.getCustomerByUsername((String) req.getSession().getAttribute("username"));
        if (customer == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            customer.setFullName(text(req, "fullName", 255));
            customer.setDateOfBirth(date(req, "dateOfBirth"));
            customer.setAddress(text(req, "address", 255));
            customer.setEmail(text(req, "email", 255));
            customer.setPhoneNumber(text(req, "phoneNumber", 30));
            if (customer.getFullName().isBlank() || customer.getAddress().isBlank()) {
                throw new IllegalArgumentException("personal information");
            }
        } catch (java.time.DateTimeException | IllegalArgumentException error) {
            req.setAttribute("customer", customer);
            req.setAttribute("message", "Thông tin cá nhân không hợp lệ.");
            view(req, res, "customer/PersonalInformationView2");
            return;
        }
        if (!dao.updatePersonalInformation(customer)) {
            req.setAttribute("customer", customer);
            req.setAttribute("message", "Chưa cập nhật được thông tin. Vui lòng thử lại.");
            view(req, res, "customer/PersonalInformationView2");
            return;
        }
        req.getSession().setAttribute("customer", customer);
        req.setAttribute("updatedCustomer", customer);
        forward(req, res, "/appointment", "create");
    }
}
