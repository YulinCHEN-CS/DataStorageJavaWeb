<%--
  Provide a overview page for available lists
  Created by IntelliJ IDEA.
  User: Stephen Chen
  Date: 21/03/2023
  Time: 22:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Available Lists</title>
</head>
<style>

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
  <div class="container">
    <h2>Available list: ${keys.size()}</h2>

    <!-- Search form to search by list name -->
    <form action="/list?method=traverse">
      <b>Search by List Name: </b><input type="text" name="key">
      <input type="submit" value="Search"><br/>
      > if not found, create new list with list name entered<br/>
    </form>

    <!-- Search form to search by list name -->
    <form action="/init?method=spotlight_search" method="post">
      <b>Search by Node Name: </b> <input type="text" name="item_name">
      <input type="submit" value="Search"><br/>
      > please note the capitalization of item name<br/>
    </form>

    <!-- Table to display available lists -->
    <table border="1">
      <tr>
        <th>List Name</th>
        <th>Operations</th>
      </tr>
      <c:forEach items="${keys}" var="key">
        <tr>
          <td>${key}</td>
          <td>
            <a href="/list?method=traverse&key=${key}">Modify</a>
            <a href="/list?method=deleteList&key=${key}">Delete</a>
          </td>
        </tr>
      </c:forEach>
    </table>
    <br/>

    <!-- Button to create a new list -->
      <form action="/addList.jsp">
        <input type="submit" value="Create new list">
      </form>
  </div>
</body>
</html>
