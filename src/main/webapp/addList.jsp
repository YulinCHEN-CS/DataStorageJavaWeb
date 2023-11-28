<%--
  This page is used to create a new list.
  This JSP file is created using IntelliJ IDEA.
  User: stephchen
  Date: 21/03/2023
  Time: 23:45
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new list</title>
</head>
<style>

    /* Set the background color and font family for the page */
    body {
        background-color: rgba(211, 211, 207, 0.92);
        font-family: "Helvetica Neue", sans-serif;
    }

    /* Center align the content of the container */
    .container {
        text-align: center;
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
  <form action="/list?method=traverse" method="get">

    <!-- Prompt the user to enter a list name -->
    Enter list name: <input type="text" name="key">

    <!-- Submit button to create the new list -->
    <input type="submit" value="Create new list">
  </form><br/>
  <form action="/init" method="get">

      <!-- Button to go back to the lists overview page -->
      <input type="submit" value="Back to lists overview">
  </form>
</div>
</body>
</html>
