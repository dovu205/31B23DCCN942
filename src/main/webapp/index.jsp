<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>GaraMan</title></head>
<body><main>
<h1>GaraMan</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<ul>
  <li><a href="<c:url value='/appointment?action=home'/>">Khách hàng</a></li>
  <li><a href="<c:url value='/suppliers?action=home'/>">Quản lý</a></li>
</ul>
</main></body></html>
