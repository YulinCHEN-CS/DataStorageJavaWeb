<%--
  Provide a page to support spotlight_search
  Created by IntelliJ IDEA.
  User: stephchen
  Date: 22/03/2023
  Time: 22:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Spotlight Search</title>
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
  <h2>Results Table</h2>

    <!-- If no results were found, display a message indicating so -->
    <c:if test="${result_lists.size() == 0}">
      <h3><c:out value="Nothing found"/><br/></h3>
    </c:if>

    <!-- If there are results, display them in a table -->
    <c:if test="${result_lists.size() != 0}">
      <table border="1">
        <tr>
          <th>Link to Origin</th>
          <th>Link to Item</th>
        </tr>
        <c:forEach var="i" begin="0" end="${result_lists.size() - 1}">
          <tr>

            <!-- Link to the origin list of the result -->
            <td><a href="/list?method=traverse&key=${result_lists.get(i)}">${result_lists.get(i)}</a></td>

            <!-- Link to the specific item in the origin list -->
            <td><a href="/list?method=search&key=${result_lists.get(i)}&search_name=${result_nodes.get(i)}">${result_nodes.get(i)}</a></td>

          </tr>
        </c:forEach>
      </table>
    </c:if>
    <br/>

    <!-- Back to Lists button -->
    <form action="/init">
      <input type="submit" value="Back to Lists">
    </form>
  </div>
</body>
</html>
