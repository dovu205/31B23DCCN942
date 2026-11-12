package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import model.ImportInvoice2;
import model.ImportInvoiceDetail2;
import model.SparePart2;

public class ImportInvoiceDetailDAO2 extends DAO2 {
    public ImportInvoiceDetail2[] getImportDetails(ImportInvoice2 importInvoice) throws SQLException {
        String sql = """
                SELECT d.id, d.importQuantity, d.importUnitPrice, p.id AS partId, p.name,
                       p.price, p.description, p.stockQuantity
                FROM tblImportInvoiceDetail2 d JOIN tblSparePart2 p ON p.id = d.sparePartId
                WHERE d.importInvoiceId = ? ORDER BY d.id
                """;
        var result = new ArrayList<ImportInvoiceDetail2>();
        try (var connection = openConnection(); var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, importInvoice.getId());
            try (var rows = statement.executeQuery()) {
                while (rows.next()) {
                    var part = new SparePart2();
                    part.setId(rows.getInt("partId"));
                    part.setName(rows.getString("name"));
                    part.setPrice(rows.getBigDecimal("price"));
                    part.setDescription(rows.getString("description"));
                    part.setStockQuantity(rows.getInt("stockQuantity"));
                    var detail = new ImportInvoiceDetail2();
                    detail.setId(rows.getInt("id"));
                    detail.setImportQuantity(rows.getInt("importQuantity"));
                    detail.setImportUnitPrice(rows.getBigDecimal("importUnitPrice"));
                    detail.setImportInvoice(importInvoice);
                    detail.setSparePart(part);
                    result.add(detail);
                }
            }
        }
        return result.toArray(ImportInvoiceDetail2[]::new);
    }
}
