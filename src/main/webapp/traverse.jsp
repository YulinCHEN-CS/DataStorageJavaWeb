<%--
  To display all content in selected list
  Created by IntelliJ IDEA.
  User: Stephen Chen
  Date: 14/03/2023
  Time: 15:18
--%>
<%@ page import="com.stephen.entities.DoublyLinkedHashTable" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%
  DoublyLinkedHashTable list = (DoublyLinkedHashTable) request.getAttribute("list");
%>
<head>
  <title>${list.name} traverse</title>
</head>
<style>

  /* Zoom image */
  .image img{max-width: 250px}

  /* Set background color and font family for the whole page */
  body {
    background-color: rgba(211, 211, 207, 0.92);
    font-family: "Helvetica Neue", sans-serif;
  }

  /* Center align the content of the container */
  .container {
    text-align: center;
  }

  /* Style the display of text in the container */
  .l {
    display: inline-block;
    text-align: left;
    max-width: 70%;
  }

  /* Set styling for the table */
  table {
    width: 100%;
    max-width: 800px;
    margin: auto;
    font-size: 16px;
    font-family: Arial, sans-serif;
  }

  /* Set padding and alignment for table headers and data cells */
  th, td {
    padding: 8px;
    text-align: left;
  }

  /* Set background color, font weight, and color for table headers */
  th {
    background-color: #bbbbb6;
    font-weight: bold;
    color: #333;
  }

  /* Set background color for table rows when hovered over */
  tr:hover {
    background-color: #dad0b6;
  }

  /* Style the input submit button */
  input[type="submit"] {
    background-color: #5b5b59;
    color: #FFFFFF;
    padding: 10px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
  }

  /* Style the input text fields */
  input[type="text"] {
    background-color: #ffffff;
    color: #000000;
    padding: 10px;
    border-radius: 2px;
    border: none;
    cursor: pointer;
    font-size: 15px;
  }
</style>
<body>

<!-- Centered and left-aligned display -->
  <div class="container">
    <div class="l">
      <h2>

        <!-- To display and modify list name -->
        <form action="/list?method=rename" method="post">
          > Current list name:<input type="text" name = "new_name" value=${list.name}>
          <input type="submit" value="Change List Name">
        </form>
      </h2>

      <!-- To display list size -->
      <h2>> Current list size: ${list.size}</h2>
      <form action="/list?method=search" method="post">
        <input type="text" name="search_name">
        <input type="submit" value="Search by Node Name">
      </form>
    </div>

    <!-- Table displaying list content -->
    <table border="1">
      <tr>
        <th>Node Name</th>
        <th>Items in Node</th>
        <th>Operations</th>
      </tr>

      <!-- Loop through each node in the list -->
      <c:set var="node" value="${list.head.next}"/>
      <c:forEach begin="1" end="${list.size}" var="count">
        <c:if test="${node!=list.tail}">
          <tr>
            <td>${node.name}</td>
            <td>

              <!-- Loop through each object in the current node -->
              <c:set var="objects" value="${node.get_raw_objects()}"/>
              <c:set var="count" value="0"/>
              <c:forEach items="${objects}" var="object">

                <!-- Display different object types in different ways -->
                <c:choose>
                  <c:when test="${object.getClass().getSimpleName() eq 'URL'}">
                    <a href="${object.toExternalForm()}">${object.toExternalForm()}</a><br/>
                  </c:when>
                  <c:when test="${object.getClass().getSimpleName() eq 'Image'}">
                    <div class="image">
                      <img src="data:image/png;base64,${object.encodedImage}"><br/>
                    </div>
                  </c:when>
                  <c:when test="${object.getClass().getSimpleName() eq 'Double'}">
                    ${object}<br/>
                  </c:when>
                  <c:when test="${object.getClass().getSimpleName() eq 'DoublyLinkedHashTable'}">
                      <a href="/list?method=traverse&key=${object.name}">${object.name}</a><br/>
                  </c:when>
                  <c:otherwise>
                    ${object}<br/>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </td>
            <td>

              <!-- Link to delete and modify item -->
              <a href="/list?method=delete&name=${node.name}">Delete</a>
              <a href="/list?method=update&name=${node.name}">Modify</a>
            </td>
          </tr>
          <c:set var="node" value="${node.next}"/>
          <c:set var="count" value="count+1"/>
        </c:if>
      </c:forEach>
    </table>

    <!-- Add new node button -->
    <br/>
    <form action="/add.jsp">
      <input type="submit", value="Add new node">
    </form>

    <!-- Back to Lists button -->
    <form action="/init">
      <input type="submit" value="Back to Lists">
    </form>
  </div>
</body>
</html>