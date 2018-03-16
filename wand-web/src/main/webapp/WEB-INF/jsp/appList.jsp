<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<body>
<h2>Wand App List</h2>
<table>
    <tr>
        <th>应用名</th>
        <th>负责人</th>
    </tr>
    <c:forEach items="${list}" var="arr">
    <tr>
        <td>
            <a href="hostList?app=${arr.name}">${arr.name}</a>
        </td>
        <td>${arr.owner}</td>
    <tr>
        </c:forEach>
</table>


</body>
</html>
