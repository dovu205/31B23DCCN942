<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>Đặt lịch hẹn trực tuyến</title></head>
<body><main>
<h1>Đặt lịch hẹn trực tuyến</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<form action="<c:url value='/appointment'/>" method="post">
  <input type="hidden" name="action" value="checkTime">
  <p><label>Ngày hẹn <input type="date" name="appointmentDate" required></label></p>
  <p><label>Giờ hẹn <input type="time" name="appointmentTime" required></label></p>
  <p><label>Ghi chú <textarea name="note" maxlength="1000"></textarea></label></p>
  <button type="submit">Xác nhận</button>
  <a href="<c:url value='/appointment?action=home'/>">Quay lại</a>
</form>
</main></body></html>
