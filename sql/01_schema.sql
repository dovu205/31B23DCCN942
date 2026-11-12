CREATE DATABASE IF NOT EXISTS garaman CHARACTER SET utf8mb4;
USE garaman;

CREATE TABLE IF NOT EXISTS tblMember2 (
  username VARCHAR(255) PRIMARY KEY, password VARCHAR(255) NOT NULL,
  fullName VARCHAR(255) NOT NULL, dateOfBirth DATE,
  address VARCHAR(255), email VARCHAR(255), phoneNumber VARCHAR(30)
);
CREATE TABLE IF NOT EXISTS tblEmployee2 (
  username VARCHAR(255) PRIMARY KEY, employeeCode VARCHAR(30) NOT NULL UNIQUE,
  jobPosition VARCHAR(100), FOREIGN KEY (username) REFERENCES tblMember2(username)
);
CREATE TABLE IF NOT EXISTS tblCustomer2 (
  username VARCHAR(255) PRIMARY KEY, customerCode VARCHAR(30) NOT NULL UNIQUE,
  FOREIGN KEY (username) REFERENCES tblMember2(username)
);
CREATE TABLE IF NOT EXISTS tblManager2 (
  username VARCHAR(255) PRIMARY KEY, FOREIGN KEY (username) REFERENCES tblEmployee2(username)
);
CREATE TABLE IF NOT EXISTS tblWarehouseStaff2 (
  username VARCHAR(255) PRIMARY KEY, FOREIGN KEY (username) REFERENCES tblEmployee2(username)
);
CREATE TABLE IF NOT EXISTS tblSalesStaff2 (
  username VARCHAR(255) PRIMARY KEY, FOREIGN KEY (username) REFERENCES tblEmployee2(username)
);
CREATE TABLE IF NOT EXISTS tblTechnician2 (
  username VARCHAR(255) PRIMARY KEY, FOREIGN KEY (username) REFERENCES tblEmployee2(username)
);
CREATE TABLE IF NOT EXISTS tblSupplier2 (
  id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL,
  address VARCHAR(255), phoneNumber VARCHAR(30), email VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS tblService2 (
  id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL,
  price DECIMAL(18,2) NOT NULL, description VARCHAR(1000)
);
CREATE TABLE IF NOT EXISTS tblSparePart2 (
  id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL,
  price DECIMAL(18,2) NOT NULL, description VARCHAR(1000), stockQuantity INT NOT NULL
);
CREATE TABLE IF NOT EXISTS tblInvoice2 (
  id INT AUTO_INCREMENT PRIMARY KEY, invoiceCode VARCHAR(30) NOT NULL UNIQUE,
  invoiceDate DATE NOT NULL, paymentStatus VARCHAR(30) NOT NULL,
  customerUsername VARCHAR(255) NOT NULL, salesStaffUsername VARCHAR(255) NOT NULL,
  FOREIGN KEY (customerUsername) REFERENCES tblCustomer2(username),
  FOREIGN KEY (salesStaffUsername) REFERENCES tblSalesStaff2(username)
);
CREATE TABLE IF NOT EXISTS tblAppointment2 (
  id INT AUTO_INCREMENT PRIMARY KEY, appointmentCode VARCHAR(30) NOT NULL UNIQUE,
  appointmentDateTime TIMESTAMP NOT NULL, note VARCHAR(1000), status VARCHAR(30) NOT NULL,
  customerUsername VARCHAR(255) NOT NULL,
  FOREIGN KEY (customerUsername) REFERENCES tblCustomer2(username),
  INDEX idx_appointment_slot (appointmentDateTime, status)
);
CREATE TABLE IF NOT EXISTS tblImportInvoice2 (
  id INT AUTO_INCREMENT PRIMARY KEY, importInvoiceCode VARCHAR(30) NOT NULL UNIQUE,
  importDate DATE NOT NULL, createdDate DATE NOT NULL,
  supplierId INT NOT NULL, warehouseStaffUsername VARCHAR(255) NOT NULL,
  FOREIGN KEY (supplierId) REFERENCES tblSupplier2(id),
  FOREIGN KEY (warehouseStaffUsername) REFERENCES tblWarehouseStaff2(username),
  INDEX idx_import_supplier_date (supplierId, importDate)
);
CREATE TABLE IF NOT EXISTS tblInvoiceServiceDetail2 (
  id INT AUTO_INCREMENT PRIMARY KEY, unitPrice DECIMAL(18,2) NOT NULL, unit VARCHAR(30),
  note VARCHAR(1000), invoiceId INT NOT NULL, serviceId INT NOT NULL,
  FOREIGN KEY (invoiceId) REFERENCES tblInvoice2(id), FOREIGN KEY (serviceId) REFERENCES tblService2(id)
);
CREATE TABLE IF NOT EXISTS tblInvoiceSparePartDetail2 (
  id INT AUTO_INCREMENT PRIMARY KEY, quantity INT NOT NULL, unitPrice DECIMAL(18,2) NOT NULL,
  unit VARCHAR(30), note VARCHAR(1000), invoiceId INT NOT NULL, sparePartId INT NOT NULL,
  FOREIGN KEY (invoiceId) REFERENCES tblInvoice2(id), FOREIGN KEY (sparePartId) REFERENCES tblSparePart2(id)
);
CREATE TABLE IF NOT EXISTS tblImportInvoiceDetail2 (
  id INT AUTO_INCREMENT PRIMARY KEY, importQuantity INT NOT NULL, importUnitPrice DECIMAL(18,2) NOT NULL,
  importInvoiceId INT NOT NULL, sparePartId INT NOT NULL,
  FOREIGN KEY (importInvoiceId) REFERENCES tblImportInvoice2(id),
  FOREIGN KEY (sparePartId) REFERENCES tblSparePart2(id)
);
CREATE TABLE IF NOT EXISTS tblTechnicianAssignment2 (
  id INT AUTO_INCREMENT PRIMARY KEY, assignedDateTime TIMESTAMP NOT NULL,
  status VARCHAR(30) NOT NULL, note VARCHAR(1000), technicianUsername VARCHAR(255) NOT NULL,
  invoiceId INT NOT NULL, serviceId INT NOT NULL,
  FOREIGN KEY (technicianUsername) REFERENCES tblTechnician2(username),
  FOREIGN KEY (invoiceId) REFERENCES tblInvoice2(id), FOREIGN KEY (serviceId) REFERENCES tblService2(id)
);
