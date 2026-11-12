package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ImportInvoice2 {
    private int id;
    private String importInvoiceCode;
    private LocalDate importDate;
    private LocalDate createdDate;
    private long totalQuantity;
    private BigDecimal totalAmount;
    private Supplier2 supplier;
    private WarehouseStaff2 warehouseStaff;
    private List<ImportInvoiceDetail2> importInvoiceDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImportInvoiceCode() {
        return importInvoiceCode;
    }

    public void setImportInvoiceCode(String importInvoiceCode) {
        this.importInvoiceCode = importInvoiceCode;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Supplier2 getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier2 supplier) {
        this.supplier = supplier;
    }

    public WarehouseStaff2 getWarehouseStaff() {
        return warehouseStaff;
    }

    public void setWarehouseStaff(WarehouseStaff2 warehouseStaff) {
        this.warehouseStaff = warehouseStaff;
    }

    public List<ImportInvoiceDetail2> getImportInvoiceDetails() {
        return importInvoiceDetails;
    }

    public void setImportInvoiceDetails(List<ImportInvoiceDetail2> importInvoiceDetails) {
        this.importInvoiceDetails = importInvoiceDetails;
    }
}
