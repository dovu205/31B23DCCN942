<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>Chi tiết hóa đơn nhập</title></head>
<body><main>
<h1>Chi tiết hóa đơn nhập</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<dl>
  <dt>Mã hóa đơn</dt><dd><c:out value="${importInvoice.importInvoiceCode}"/></dd>
  <dt>Ngày nhập</dt><dd><c:out value="${importInvoice.importDate}"/></dd>
  <dt>Ngày lập</dt><dd><c:out value="${importInvoice.createdDate}"/></dd>
  <dt>Nhà cung cấp</dt><dd><c:out value="${importInvoice.supplier.name}"/></dd>
  <dt>Nhân viên kho</dt><dd><c:out value="${importInvoice.warehouseStaff.fullName}"/>
      (<c:out value="${importInvoice.warehouseStaff.employeeCode}"/>)</dd>
</dl>
<table border="1">
  <thead><tr><th>Phụ tùng</th><th>Số lượng</th><th>Đơn giá</th><th>Thành tiền</th></tr></thead>
  <tbody>
  <c:forEach items="${outImportDetails}" var="detail">
    <tr><td><c:out value="${detail.sparePart.name}"/></td>
        <td><c:out value="${detail.importQuantity}"/></td><td><c:out value="${detail.importUnitPrice}"/></td>
        <td><c:out value="${detail.lineAmount}"/></td></tr>
  </c:forEach>
  </tbody>
</table>
<p>Tổng số lượng: <c:out value="${importInvoice.totalQuantity}"/></p>
<p>Tổng tiền: <c:out value="${importInvoice.totalAmount}"/></p>
<p><a href="<c:url value='/suppliers?action=back'/>">Quay lại thống kê</a></p>
</main></body></html>
