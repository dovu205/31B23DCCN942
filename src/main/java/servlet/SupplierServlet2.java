package servlet;

import DAO.SupplierDAO2;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/suppliers")
public class SupplierServlet2 extends WebSupport {
    private final SupplierDAO2 dao = new SupplierDAO2();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        handle(req, res, "MANAGER", () -> {
            String action = req.getParameter("action");
            if ("home".equals(action) || "report".equals(action)) {
                view(req, res, "manager/" + ("home".equals(action) ? "ManagerMainView2" : "ReportView2"));
                return;
            }
            var session = req.getSession();
            if (!"back".equals(action)) {
                session.setAttribute("hiddenSuppliers", dao.getAllSuppliers());
                session.removeAttribute("outStatistics");
                session.removeAttribute("startDate");
                session.removeAttribute("endDate");
                session.removeAttribute("outImportInvoices");
            }
            view(req, res, "manager/SupplierStatisticsView2");
        });
    }
}
