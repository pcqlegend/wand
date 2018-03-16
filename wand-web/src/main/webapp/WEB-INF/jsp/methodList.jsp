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
        <td>${arr.class}</td>
        <td>${arr.methodDesc}</td>
        <td>${arr.method}</td>
        <td>
            <a href="/method?ip=${ip}&port=${port}&class=${arr.class}&method=${arr.method}">调用</a>
        </td>
    <tr>
        </c:forEach>
</table>


</body>
</html>
