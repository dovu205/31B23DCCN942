USE garaman;
-- Chạy một lần trên CSDL thực hành mới.
START TRANSACTION;
INSERT INTO tblMember2 (username,password,fullName,dateOfBirth,address,email,phoneNumber) VALUES
 ('customer_demo','DEMO_ONLY','Nguyễn Văn A','2005-01-01','1 Nguyễn Trãi','a@example.test','0912345678'),
 ('manager_demo','DEMO_ONLY','Trần Thị B',NULL,NULL,NULL,NULL),
 ('warehouse_demo','DEMO_ONLY','Lê Văn C',NULL,NULL,NULL,NULL);
INSERT INTO tblCustomer2 VALUES ('customer_demo','KH001');
INSERT INTO tblEmployee2 VALUES ('manager_demo','NV001','Quản lý'),('warehouse_demo','NV002','Nhân viên kho');
INSERT INTO tblManager2 VALUES ('manager_demo');
INSERT INTO tblWarehouseStaff2 VALUES ('warehouse_demo');
INSERT INTO tblSupplier2 (id,name,address) VALUES (1,'Honda','Hà Nội');
INSERT INTO tblSparePart2 (id,name,price,stockQuantity) VALUES
 (1,'Bộ lọc dầu',250000,0),(2,'Má phanh',250000,0);
INSERT INTO tblImportInvoice2 (id,importInvoiceCode,importDate,createdDate,supplierId,warehouseStaffUsername) VALUES
 (1,'HD001','2026-09-05','2026-09-05',1,'warehouse_demo'),
 (2,'HD002','2026-09-10','2026-09-10',1,'warehouse_demo');
INSERT INTO tblImportInvoiceDetail2 (importInvoiceId,sparePartId,importQuantity,importUnitPrice) VALUES
 (1,1,200,250000),(1,2,300,250000),(2,1,750,250000);
COMMIT;
