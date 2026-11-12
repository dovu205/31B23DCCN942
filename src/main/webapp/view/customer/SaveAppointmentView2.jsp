<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>Đặt lịch thành công</title></head>
<body><main>
<h1>Đặt lịch thành công</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<p>Mã lịch hẹn: <strong><c:out value="${appointment.appointmentCode}"/></strong></p>
<p>Ngày giờ hẹn: <c:out value="${appointment.appointmentDateTime}"/></p>
<a href="<c:url value='/appointment?action=home'/>">OK</a>
</main></body></html>
