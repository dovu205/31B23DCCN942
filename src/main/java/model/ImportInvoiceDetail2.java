package model;

import java.math.BigDecimal;

public class ImportInvoiceDetail2 {
    private int id;
    private int importQuantity;
    private BigDecimal importUnitPrice;
    private ImportInvoice2 importInvoice;
    private SparePart2 sparePart;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(int importQuantity) {
        this.importQuantity = importQuantity;
    }

    public BigDecimal getImportUnitPrice() {
        return importUnitPrice;
    }

    public void setImportUnitPrice(BigDecimal importUnitPrice) {
        this.importUnitPrice = importUnitPrice;
    }

    public ImportInvoice2 getImportInvoice() {
        return importInvoice;
    }

    public void setImportInvoice(ImportInvoice2 importInvoice) {
        this.importInvoice = importInvoice;
    }

    public SparePart2 getSparePart() {
        return sparePart;
    }

    public void setSparePart(SparePart2 sparePart) {
        this.sparePart = sparePart;
    }

    public BigDecimal getLineAmount() {
        return importUnitPrice.multiply(BigDecimal.valueOf(importQuantity));
    }
}
