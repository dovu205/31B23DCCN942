package servlet;

import DAO.ImportInvoiceDAO2;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.SupplierStatisticsByImportedQuantity2;

@WebServlet("/import-invoices")
public class ImportInvoiceServlet2 extends WebSupport {
    private final ImportInvoiceDAO2 dao = new ImportInvoiceDAO2();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        handle(req, res, "MANAGER", () -> {
            if (!"statistics".equals(req.getParameter("action"))) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            LocalDate start;
            LocalDate end;
            var session = req.getSession();
            try {
                start = date(req, "startDate");
                end = date(req, "endDate");
            } catch (java.time.DateTimeException | IllegalArgumentException error) {
                session.removeAttribute("outStatistics");
                session.removeAttribute("startDate");
                session.removeAttribute("endDate");
                session.removeAttribute("outImportInvoices");
                req.setAttribute("message", "Ngày không đúng định dạng.");
                view(req, res, "manager/SupplierStatisticsView2");
                return;
            }
            var results = dao.getSupplierStatisticsByImportedQuantity(start, end);
            session.setAttribute("startDate", start);
            session.setAttribute("endDate", end);
            session.setAttribute("outStatistics", results);
            session.removeAttribute("outImportInvoices");
            view(req, res, "manager/SupplierStatisticsView2");
        });
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        handle(req, res, "MANAGER", () -> {
            if (!"invoices".equals(req.getParameter("action"))) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int supplierId;
            try {
                supplierId = id(req, "supplierId");
            } catch (IllegalArgumentException error) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            var session = req.getSession();
            var statistics = (SupplierStatisticsByImportedQuantity2[]) session.getAttribute("outStatistics");
            if (statistics != null) {
                for (var supplier : statistics) {
                    if (supplier.getId() == supplierId) {
                        var invoices = dao.getImportInvoicesBySupplier(supplier, supplier.getStartDate(),
                                supplier.getEndDate());
                        session.setAttribute("outImportInvoices", invoices);
                        req.setAttribute("supplier", supplier);
                        view(req, res, "manager/SupplierImportInvoicesView2");
                        return;
                    }
                }
            }
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        });
    }
}
