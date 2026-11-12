<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi"><head><meta charset="utf-8"><title>Xác nhận thông tin cá nhân</title></head>
<body><main>
<h1>Xác nhận thông tin cá nhân</h1>
<c:if test="${not empty requestScope.message}">
  <p role="alert"><c:out value="${requestScope.message}"/></p>
</c:if>

<p>Khung giờ: <c:out value="${sessionScope.appointmentDateTime}"/></p>
<form action="<c:url value='/customer'/>" method="post">
  <input type="hidden" name="action" value="update">
  <p><label>Họ tên <input name="fullName" maxlength="255" required value="<c:out value='${customer.fullName}'/>"></label></p>
  <p><label>Ngày sinh <input type="date" name="dateOfBirth" required value="<c:out value='${customer.dateOfBirth}'/>"></label></p>
  <p><label>Địa chỉ <input name="address" maxlength="255" required value="<c:out value='${customer.address}'/>"></label></p>
  <p><label>Email <input type="email" name="email" maxlength="255" value="<c:out value='${customer.email}'/>"></label></p>
  <p><label>Số điện thoại <input type="tel" name="phoneNumber" maxlength="30" value="<c:out value='${customer.phoneNumber}'/>"></label></p>
  <button type="submit">Xác nhận</button>
  <a href="<c:url value='/appointment'/>">Quay lại</a>
</form>
</main></body></html>
