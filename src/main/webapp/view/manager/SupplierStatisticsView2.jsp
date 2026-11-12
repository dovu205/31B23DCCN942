<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>Thống kê nhà cung cấp theo lượng hàng nhập</title></head>
<body><main>
<h1>Thống kê nhà cung cấp theo lượng hàng nhập</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<form action="<c:url value='/import-invoices'/>" method="post">
  <input type="hidden" name="action" value="statistics">
  <label>Ngày bắt đầu <input type="date" name="startDate" value="<c:out value='${sessionScope.startDate}'/>" required></label>
  <label>Ngày kết thúc <input type="date" name="endDate" value="<c:out value='${sessionScope.endDate}'/>" required></label>
  <button type="submit">Xem thống kê</button>
</form>
<c:if test="${not empty sessionScope.startDate}">
  <c:choose>
    <c:when test="${empty sessionScope.outStatistics}"><p>Không có dữ liệu trong khoảng ngày đã chọn.</p></c:when>
    <c:otherwise>
      <table border="1">
        <thead><tr><th>Nhà cung cấp</th><th>Số hóa đơn</th><th>Tổng lượng nhập</th><th>Tổng tiền</th></tr></thead>
        <tbody>
        <c:forEach items="${sessionScope.outStatistics}" var="supplier">
          <c:url value="/import-invoices" var="invoicesUrl">
            <c:param name="action" value="invoices"/><c:param name="supplierId" value="${supplier.id}"/>
          </c:url>
          <tr><td><a href="<c:out value='${invoicesUrl}'/>"><c:out value="${supplier.name}"/></a></td>
              <td><c:out value="${supplier.importCount}"/></td>
              <td><c:out value="${supplier.totalImportedQuantity}"/></td>
              <td><c:out value="${supplier.totalImportAmount}"/></td></tr>
        </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>
</c:if>
<p><a href="<c:url value='/suppliers?action=report'/>">Quay lại</a></p>
</main></body></html>
