package model;

import java.time.LocalDate;

public class SupplierStatisticsByImportedQuantity2 extends SupplierStatistics2 {
    private LocalDate startDate;
    private LocalDate endDate;
    private long totalImportedQuantity;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getTotalImportedQuantity() {
        return totalImportedQuantity;
    }

    public void setTotalImportedQuantity(long totalImportedQuantity) {
        this.totalImportedQuantity = totalImportedQuantity;
    }
}
