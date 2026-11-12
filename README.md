# GaraMan — Cài đặt hai module

Cấu trúc theo Chương 5 của giáo trình và Package Diagram GaraMan.

## 1. Tổ chức dự án

| Trong dự án | Vai trò |
| --- | --- |
| `src/main/java/model` | Thực thể và đối tượng kết quả của hai module |
| `src/main/java/DAO` | DAO2 quản lý cấu hình JDBC và năm lớp DAO |
| `src/main/java/servlet` | Năm Servlet nhận GET/POST và lớp hỗ trợ dùng nội bộ |
| `src/main/webapp/view/customer` | Bốn JSP của module đặt lịch |
| `src/main/webapp/view/manager` | Năm JSP của module thống kê |
| `sql` | Lược đồ 17 bảng và dữ liệu mẫu |

Tên Java `DAO` phân biệt hoa/thường. `view` là thư mục JSP trong WAR. File WAR chứa JDBC Driver và JSTL trong WEB-INF/lib. Servlet API do Tomcat cung cấp. Đích triển khai là Tomcat 9 (javax.servlet) trên Java 17 trở lên. Khi mở bằng Eclipse, chọn Import → Existing Maven Projects.

Các lớp dùng LocalDate cho ngày, LocalDateTime cho lịch hẹn và BigDecimal cho tiền. DAO thống kê trả mảng theo kịch bản, còn chi tiết bên trong thực thể ImportInvoice2 dùng List. DAO dùng câu lệnh tham số hóa và mở kết nối theo thao tác. Tổng tiền và tổng lượng nhập được tính từ chi tiết.

## 2. Cài đặt và chạy

1. Trên MySQL, chạy `sql/01_schema.sql`. Để chạy thử với dữ liệu của bài, chạy thêm `sql/02_demo.sql` một lần trên CSDL thực hành mới.
2. Đặt biến môi trường của tiến trình Tomcat:
   - `GARAMAN_DB_URL=jdbc:mysql://localhost:3306/garaman`
   - `GARAMAN_DB_USER` và `GARAMAN_DB_PASSWORD` theo tài khoản MySQL trên máy.
   - `GARAMAN_SLOT_CAPACITY` là số lịch tối đa cho một thời điểm hẹn, mặc định 1 vì đề chưa quy định số cụ thể.
   - Chỉ khi chạy thử cục bộ, đặt `GARAMAN_DEMO_CUSTOMER=customer_demo` và `GARAMAN_DEMO_MANAGER=manager_demo`. Hai cấu hình này bỏ qua bước đăng nhập. Khi tích hợp, bỏ hai biến demo và để chức năng đăng nhập hiện có đặt `session.username` và `session.role` (`CUSTOMER` hoặc `MANAGER`).
3. Chạy `mvn clean package` từ thư mục có pom.xml để tạo `target/GaraMan.war`, hoặc dùng dist/GaraMan.war đã đóng gói.
4. Chép GaraMan.war vào thư mục webapps của Tomcat. Mở `http://localhost:8080/GaraMan/`. Khi dùng demo, mở hai vai trò ở hai phiên trình duyệt riêng.

## 3. Hai luồng nghiệp vụ

### Đặt lịch hẹn trực tuyến

CustomerMainView2 → AppointmentView2 → AppointmentServlet2 (checkTime) → CustomerServlet2 (load) → PersonalInformationView2 → CustomerServlet2 (update) → AppointmentServlet2 (create) → SaveAppointmentView2 → CustomerMainView2.

Ngày giờ được giữ trong session. Bước create chỉ nhận chuyển tiếp nội bộ sau khi cập nhật khách hàng thành công. Servlet đóng gói Appointment2 trước khi gọi DAO lưu. DAO kiểm tra sức chứa lần cuối trong giao dịch, dùng khóa theo khung giờ và thử mã mới khi mã lịch bị trùng. Các ứng dụng ghi lịch cùng CSDL cần dùng cùng cơ chế khóa theo khung giờ.

### Thống kê nhà cung cấp theo lượng hàng nhập

ManagerMainView2 → ReportView2 → SupplierServlet2 → SupplierStatisticsView2 → ImportInvoiceServlet2 (statistics, invoices) → SupplierImportInvoicesView2 → ImportInvoiceDetailServlet2 → ImportInvoiceDetailView2 → SupplierStatisticsView2.

DAO gộp dữ liệu theo nhà cung cấp trong khoảng importDate đã chọn. Servlet giữ kết quả và khoảng ngày để truy cập danh sách, mở chi tiết theo id hóa đơn và quay lại thống kê.

## 4. Ca chạy thử

| Ca | Kết quả cần thấy |
| --- | --- |
| Chọn giờ tương lai còn chỗ và xác nhận hồ sơ | Hiện mã lịch hẹn và ghi một dòng tblAppointment2 |
| Chọn lại cùng thời điểm khi đã đủ sức chứa | Báo khung giờ không còn khả dụng |
| Gửi ngày hoặc giờ sai định dạng | Hiện thông báo và giữ trang đặt lịch |
| Tìm 01/09/2026 đến 15/09/2026 trên dữ liệu mẫu | Honda, 2 hóa đơn, tổng lượng 1.250 |
| Mở HD001 | Hai dòng 200 và 300, tổng lượng 500, tổng tiền 125.000.000 |
| Chọn khoảng không có hóa đơn | Hiện thông báo không có dữ liệu |
| Quay lại từ chi tiết hóa đơn | Khôi phục khoảng ngày và kết quả thống kê trong session |
