<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>Hóa đơn nhập của nhà cung cấp</title></head>
<body><main>
<h1>Hóa đơn nhập của nhà cung cấp</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<p>Nhà cung cấp: <c:out value="${supplier.name}"/></p>
<p>Từ <c:out value="${sessionScope.startDate}"/> đến <c:out value="${sessionScope.endDate}"/></p>
<c:if test="${empty sessionScope.outImportInvoices}"><p>Không có hóa đơn.</p></c:if>
<table border="1">
  <thead><tr><th>Mã hóa đơn</th><th>Ngày nhập</th><th>Tổng số lượng</th><th>Tổng tiền</th></tr></thead>
  <tbody>
  <c:forEach items="${sessionScope.outImportInvoices}" var="invoice">
    <c:url value="/import-invoice-details" var="detailUrl"><c:param name="invoiceId" value="${invoice.id}"/></c:url>
    <tr><td><a href="<c:out value='${detailUrl}'/>"><c:out value="${invoice.importInvoiceCode}"/></a></td>
        <td><c:out value="${invoice.importDate}"/></td><td><c:out value="${invoice.totalQuantity}"/></td>
        <td><c:out value="${invoice.totalAmount}"/></td></tr>
  </c:forEach>
  </tbody>
</table>
<p><a href="<c:url value='/suppliers?action=back'/>">Quay lại thống kê</a></p>
</main></body></html>
