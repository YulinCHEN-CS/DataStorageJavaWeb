<%--
  Provide a search page for user to search with item name
  Created by IntelliJ IDEA.
  User: stephchen
  Date: 13/03/2023
  Time: 15:45
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>result for ${item.name}</title>
</head>
<style>

  /* Zoom image */
  .image img{max-width: 600px}

  /* Set background color and font family for the whole page */
  body {
    background-color: rgba(211, 211, 207, 0.92);
    font-family: "Helvetica Neue", sans-serif;
  }

  /* Center align the content of the container */
  .container {
    text-align: center;
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
</style>
<body>
    <div class="container">

      <!-- Check the item if found or not-->
      <c:if test="${item == null}">
        <h2>Nothing Found</h2>
      </c:if>

      <c:if test="${item != null}">
        <!-- Displays the name of the item -->
        <h2>Item name: ${item.name}</h2>

        <table border="1">
          <tr>
            <th>Object type</th>
            <th>Content</th>
          </tr>

          <!-- Sets the variable 'objects' to the raw objects of the item -->
          <c:set var="objects" value="${item.get_raw_objects()}"/>

          <!-- Checks node is normal or head and tail -->
          <c:if test="${objects.getClass().getSimpleName() eq 'ArrayList'}">
            <c:forEach items="${objects}" var="object">
              <tr>
                <td>${object.getClass().getSimpleName()}</td>

                <!-- Choose what to display based on the object type -->
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
                  <c:when test="${object.getClass().getSimpleName() eq 'DoublyLinkedHashTable'}">
                    <td><a href="/list?method=traverse&key=${object.name}">${object.name}</a></td>
                  </c:when>
                  <c:otherwise>
                    <td>${object}</td>
                  </c:otherwise>

                </c:choose>
              </tr>
            </c:forEach>
          </c:if>
        </table>
      </c:if>
      <br/>

      <!-- Button to return to the list -->
      <form action="/list?method=traverse">
        <input type="submit" value="Return to list">
      </form>
    </div>
  </body>
</html>

