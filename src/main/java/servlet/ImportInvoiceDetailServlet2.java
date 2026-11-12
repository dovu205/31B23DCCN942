package servlet;

import DAO.ImportInvoiceDetailDAO2;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ImportInvoice2;

@WebServlet("/import-invoice-details")
public class ImportInvoiceDetailServlet2 extends WebSupport {
    private final ImportInvoiceDetailDAO2 dao = new ImportInvoiceDetailDAO2();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        handle(req, res, "MANAGER", () -> {
            int invoiceId;
            try {
                invoiceId = id(req, "invoiceId");
            } catch (IllegalArgumentException error) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            var invoices = (ImportInvoice2[]) req.getSession().getAttribute("outImportInvoices");
            if (invoices != null) {
                for (var invoice : invoices) {
                    if (invoice.getId() == invoiceId) {
                        req.setAttribute("importInvoice", invoice);
                        req.setAttribute("outImportDetails", dao.getImportDetails(invoice));
                        view(req, res, "manager/ImportInvoiceDetailView2");
                        return;
                    }
                }
            }
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        });
    }
}
