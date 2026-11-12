package model;

import java.math.BigDecimal;

public class SupplierStatistics2 extends Supplier2 {
    private int importCount;
    private BigDecimal totalImportAmount;

    public int getImportCount() {
        return importCount;
    }

    public void setImportCount(int importCount) {
        this.importCount = importCount;
    }

    public BigDecimal getTotalImportAmount() {
        return totalImportAmount;
    }

    public void setTotalImportAmount(BigDecimal totalImportAmount) {
        this.totalImportAmount = totalImportAmount;
    }
}
