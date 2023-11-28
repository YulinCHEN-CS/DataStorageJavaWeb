<%--
  Created by IntelliJ IDEA.
  User: stephchen
  Date: 13/03/2023
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>result for ${item.name}</title>
  </head>
  <style>
    .image img{max-width: 500px}
  </style>
  <body>
    <h2>Item name: ${item.name}</h2>
    <table border="1">
      <tr>
        <th>Object type</th>
        <th>Content</th>
      </tr>
      <c:set var="objects" value="${item.get_raw_objects()}"/>
      <c:forEach items="${objects}" var="object">
        <tr>
          <td>${object.getClass().getSimpleName()}</td>
          <c:choose>
            <c:when test="${object.getClass().getSimpleName() eq 'URL'}">
              <td>
                <a href="${object.toExternalForm()}">${object.toExternalForm()}</a>
              </td>
            </c:when>
            <c:when test="${object.getClass().getSimpleName() eq 'Image'}">
              <td>
                <div class="image">
                  <img src="data:image/png;base64,${object.encodedImage}">
                </div>
              </td>
            </c:when>
            <c:when test="${object.getClass().getSimpleName() eq 'Double'}">
              <td>${object}</td>
            </c:when>
            <c:otherwise>
              <td>${object}</td>
            </c:otherwise>

          </c:choose>
        </tr>
      </c:forEach>
    </table>
    <br/>
    <form action="/list?method=traverse">
      <input type="submit" value="Return to list overview">
    </form>
  </body>
</html>

