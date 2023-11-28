<%@ page import="java.net.URL" %>
<%@ page import="java.io.File" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.util.Base64" %><%--
  Created by IntelliJ IDEA.
  User: stephchen
  Date: 13/03/2023
  Time: 00:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${item.name}</title>
</head>
<style>
    .image img{max-width: 500px}
</style>
<body>
    <h2>Item name: ${item.name}</h2>
    <form action = "/list?method=update&name=${item.name}" method = "post">
    <table border="1">
        <tr>
            <th>Type-Content</th>
        </tr>
        <c:set var="count" value="0" scope="request" />
        <c:set var="objects" value="${item.get_raw_objects()}" scope="request"/>
        <c:forEach items="${objects}" var="object">
            <c:set var="current_object" value="${object}" scope="request"/>
            <tr>
                <c:set var="type" value="${object.getClass().getSimpleName()}"/>
<%--                <td>${type}</td>--%>
                <c:choose>
                    <c:when test="${type eq 'URL'}">
                        <td>
                            <input type="text" name = "${count}" value="${type}:${object.toExternalForm()}" style="width: 600px"/><br/>
                        </td>
                    </c:when>
                    <c:when test="${type eq 'Image'}">
                        <td>
                        <div class="image">
                            <input type="text" name="${count}" value="${type}:${object.source}" style="width: 600px"/><br/>
                            <img src="data:image/png;base64,${object.encodedImage}"><br/>
                        </div>
                        </td>
                    </c:when>
                    <c:when test="${type eq 'Double'}">
                        <td>
                            <input type="text" name = "${count}" value="${type}:${object}" style="width: 600px"/><br/>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td>
                            <input type="text" name = "${count}" value="${type}:${object}" style="width: 600px"/><br/>
                        </td>
                    </c:otherwise>

                </c:choose>
                <c:set var="count" value="${count + 1}" scope="request"/>
            </tr>
        </c:forEach>
        <tr>
            <td>
                <input type="text" name = "${count}" style="width: 600px" /><br/>
            </td>
        </tr>
        <input type="hidden" name="count" value="${count}" />
    </table>
        > Please enter data you want to add as (Type-Content) to the last row<br/>
        > Supported type: URL, Image, Double, String<br/>
        > Total number of objects: <c:out value="${count}" default="0"/><br/>
        <input type = "submit" name="update"/>
    </form>
</body>
</html>
