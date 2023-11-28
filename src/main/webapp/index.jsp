<%--
  Provide a start page for whole program
  Created by IntelliJ IDEA.
  User: Stephen Chen
  Date: 15/03/2023
  Time: 15:38
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
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

    /* Set font size of h2 */
    h2 {
        font-size: 32px;
    }

    /* Style the input submit button */
    input[type="submit"] {
        background-color: #3e9b40;
        color: #FFFFFF;
        padding: 10px;
        border-radius: 5px;
        border: none;
        cursor: pointer;
    }
</style>

<head>
    <title>Data Storage System</title>
</head>
<body>
    <div class="container">
        <!-- Show messages to user -->
        <h2>Welcome to a Data Storage System</h2>
        > Your data would be store in the lists.<br/>
        <br/>

        > View developer document <a href="COMP0004_Report.pdf" target="_blank">here</a><br/>
        <br/>
        > Click "START" to begin.<br/>
        <br/>
        <form action="/init" method="get">
            <input type="submit" value="START">
        </form>
        <h5>Copyright © 2023 Stephen Chen</h5>
    </div>
</body>
</html>

