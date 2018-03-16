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
        <th>服务名</th>
        <th>方法名</th>
        <th>操作</th>

    </tr>
    <c:forEach items="${list}" var="arr">
    <tr>
        <td>${arr.service}</td>
        <td>${arr.name}</td>
        <td>
            <button value="调用"/>
        </td>
    <tr>
        </c:forEach>
</table>


</body>
</html>
