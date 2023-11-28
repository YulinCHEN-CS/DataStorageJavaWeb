<%@ page import="com.stephen.entities.Node" %>
<%@ page import="com.stephen.entities.DoublyLinkedHashTable" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.util.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%
    DoublyLinkedHashTable list = (DoublyLinkedHashTable) request.getAttribute("list");
%>
    <head>
        <h2>> Current list size: ${list.size}</h2>
    </head>
    <style>
        .image img{max-width: 150px}
    </style>
    <body>
    <form action="/list?method=search" method="post">
        <input type="text" name="search_name">
        <input type="submit" value="search">
    </form>
    <table border="1">
        <tr>
            <th>Node Name</th>
            <th>Items in Node</th>
            <th>Operations</th>
        </tr>
        <c:set var="node" value="${list.head.next}"/>
        <c:forEach begin="1" end="${list.size}" var="count">
            <c:if test="${node!=list.tail}">
                <tr>
                    <td>${node.name}</td>
                    <td>
                        <c:set var="objects" value="${node.get_raw_objects()}"/>
                        <c:set var="count" value="0"/>
                        <c:forEach items="${objects}" var="object">

                                ${object.getClass().getSimpleName()}:
                                <c:choose>
                                    <c:when test="${object.getClass().getSimpleName() eq 'URL'}">
                                        <a href="${object.toExternalForm()}">${object.toExternalForm()}</a><br/>
                                    </c:when>
                                    <c:when test="${object.getClass().getSimpleName() eq 'Image'}">
                                        <br/>
                                        <div class="image">
                                            <img src="data:image/png;base64,${object.encodedImage}"><br/>
                                        </div>
                                    </c:when>
                                    <c:when test="${object.getClass().getSimpleName() eq 'Double'}">
                                        ${object}<br/>
                                    </c:when>
                                    <c:otherwise>
                                        ${object}<br/>
                                    </c:otherwise>
                                </c:choose>

                        </c:forEach>
                    </td>
                    <td>
                        <a href="/list?method=delete&name=${node.name}">Delete</a>
                        <a href="/list?method=update&name=${node.name}">Modify</a>
                    </td>
                </tr>
                <c:set var="node" value="${node.next}"/>
                <c:set var="count" value="count+1"/>
            </c:if>
        </c:forEach>

    </table>
    <br/>
    <form action="/add.jsp">
        <input type="submit", value="Add new node">
    </form>


    </body>
</html>
