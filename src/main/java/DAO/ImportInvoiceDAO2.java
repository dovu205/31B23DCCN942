package DAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.ImportInvoice2;
import model.Supplier2;
import model.SupplierStatisticsByImportedQuantity2;
import model.WarehouseStaff2;

public class ImportInvoiceDAO2 extends DAO2 {
    public SupplierStatisticsByImportedQuantity2[] getSupplierStatisticsByImportedQuantity(
            LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = """
                SELECT s.id, s.name, s.address, s.phoneNumber, s.email,
                       COUNT(DISTINCT i.id) AS importCount, SUM(d.importQuantity) AS totalQuantity,
                       SUM(d.importQuantity * d.importUnitPrice) AS totalAmount
                FROM tblSupplier2 s
                JOIN tblImportInvoice2 i ON i.supplierId = s.id
                JOIN tblImportInvoiceDetail2 d ON d.importInvoiceId = i.id
                WHERE i.importDate BETWEEN ? AND ?
                GROUP BY s.id, s.name, s.address, s.phoneNumber, s.email
                ORDER BY totalQuantity DESC, s.id
                """;
        var result = new ArrayList<SupplierStatisticsByImportedQuantity2>();
        try (var connection = openConnection(); var statement = connection.prepareStatement(sql)) {
            statement.setObject(1, startDate);
            statement.setObject(2, endDate);
            try (var rows = statement.executeQuery()) {
                while (rows.next()) {
                    var item = new SupplierStatisticsByImportedQuantity2();
                    SupplierDAO2.populateSupplier(item, rows);
                    item.setStartDate(startDate);
                    item.setEndDate(endDate);
                    item.setImportCount(rows.getInt("importCount"));
                    item.setTotalImportedQuantity(rows.getLong("totalQuantity"));
                    item.setTotalImportAmount(rows.getBigDecimal("totalAmount"));
                    result.add(item);
                }
            }
        }
        return result.toArray(SupplierStatisticsByImportedQuantity2[]::new);
    }

    public ImportInvoice2[] getImportInvoicesBySupplier(
            Supplier2 supplier, LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = """
                SELECT i.id, i.importInvoiceCode, i.importDate, i.createdDate,
                       w.username, e.employeeCode, m.fullName,
                       SUM(d.importQuantity) AS totalQuantity,
                       SUM(d.importQuantity * d.importUnitPrice) AS totalAmount
                FROM tblImportInvoice2 i
                JOIN tblWarehouseStaff2 w ON w.username = i.warehouseStaffUsername
                JOIN tblEmployee2 e ON e.username = w.username
                JOIN tblMember2 m ON m.username = e.username
                JOIN tblImportInvoiceDetail2 d ON d.importInvoiceId = i.id
                WHERE i.supplierId = ? AND i.importDate BETWEEN ? AND ?
                GROUP BY i.id, i.importInvoiceCode, i.importDate, i.createdDate,
                         w.username, e.employeeCode, m.fullName
                ORDER BY i.importDate, i.id
                """;
        var result = new ArrayList<ImportInvoice2>();
        try (var connection = openConnection(); var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, supplier.getId());
            statement.setObject(2, startDate);
            statement.setObject(3, endDate);
            try (var rows = statement.executeQuery()) {
                while (rows.next()) {
                    var staff = new WarehouseStaff2();
                    staff.setUsername(rows.getString("username"));
                    staff.setEmployeeCode(rows.getString("employeeCode"));
                    staff.setFullName(rows.getString("fullName"));
                    var invoice = new ImportInvoice2();
                    invoice.setId(rows.getInt("id"));
                    invoice.setImportInvoiceCode(rows.getString("importInvoiceCode"));
                    invoice.setImportDate(rows.getObject("importDate", LocalDate.class));
                    invoice.setCreatedDate(rows.getObject("createdDate", LocalDate.class));
                    invoice.setSupplier(supplier);
                    invoice.setWarehouseStaff(staff);
                    invoice.setTotalQuantity(rows.getLong("totalQuantity"));
                    invoice.setTotalAmount(rows.getBigDecimal("totalAmount"));
                    result.add(invoice);
                }
            }
        }
        return result.toArray(ImportInvoice2[]::new);
    }
}
