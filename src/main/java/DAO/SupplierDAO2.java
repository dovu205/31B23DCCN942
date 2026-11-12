package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Supplier2;

public class SupplierDAO2 extends DAO2 {
    public Supplier2[] getAllSuppliers() throws SQLException {
        var suppliers = new ArrayList<Supplier2>();
        String sql = "SELECT id, name, address, phoneNumber, email FROM tblSupplier2 ORDER BY name, id";
        try (var connection = openConnection(); var statement = connection.prepareStatement(sql);
             var rows = statement.executeQuery()) {
            while (rows.next()) {
                var supplier = new Supplier2();
                populateSupplier(supplier, rows);
                suppliers.add(supplier);
            }
        }
        return suppliers.toArray(Supplier2[]::new);
    }

    static void populateSupplier(Supplier2 supplier, ResultSet row) throws SQLException {
        supplier.setId(row.getInt("id"));
        supplier.setName(row.getString("name"));
        supplier.setAddress(row.getString("address"));
        supplier.setPhoneNumber(row.getString("phoneNumber"));
        supplier.setEmail(row.getString("email"));
    }
}
