<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>Báo cáo</title></head>
<body><main>
<h1>Báo cáo</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<ul><li><a href="<c:url value='/suppliers'/>">Thống kê nhà cung cấp theo lượng hàng nhập</a></li></ul>
<p><a href="<c:url value='/suppliers?action=home'/>">Quay lại</a></p>
</main></body></html>
