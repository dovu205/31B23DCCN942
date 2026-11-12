<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>Giao diện khách hàng</title></head>
<body><main>
<h1>Giao diện khách hàng</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<p><a href="<c:url value='/appointment'/>">Đặt lịch hẹn</a></p>
</main></body></html>
