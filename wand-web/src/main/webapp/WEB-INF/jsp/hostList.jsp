<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<body>
<h2>Wand App Hosts</h2>
<table>
    <tr>
        <th>主机名</th>
        <th>ip</th>
    </tr>
    <c:forEach items="${list}" var="arr">
    <tr>
        <td><a href="methodList?ip=${arr.ip}&port=${arr.port}">${arr.name}</a></td>
        <td>${arr.ip}</td>
    <tr>
        </c:forEach>
</table>


</body>
</html>
