<%--
  This JSP file is used to display the item creation page.
  It contains a form to add a new node.
  Created by IntelliJ IDEA.
  User: Stephen Chen
  Date: 12/03/2023
  Time: 21:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>new node</title>
</head>
<style>
    /* set width, font size and display method for label */
    label{
        width: 150px;
        display: inline-block;
        text-align: right;
    }

    /* Set the background color and font for the page */
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
        max-width: 350%;
        font-size: 15px;
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
<body>
<div class="container">
    <h2>Add a new Node</h2>
    <form action = "/list?method=addNode" method = "post">

        <!-- Input fields for item creation -->
        <div>
            <label> Item name: </label>
            <input type="text" name="name"/><br/>
        </div>
        <br/>
        <div>
            <label> Text: </label>
            <input type="text" name="strings"/><br/>
        </div>
        <br/>
        <div>
            <label> URL: </label>
            <input type="text" name="urls"/><br/>
        </div>
        <br/>
        <div>
            <label> Image(url or absolute path): </label>
            <input type="text" name="images"/><br/>
        </div>
        <br/>
        <div>
            <label> Number: </label>
            <input type="text" name="doubles"/><br/>
        </div>
        <br/>
        <div>
            <label> List Name: </label>
            <input type="text" name="lists"/><br/>
        </div>
        <br/>
        <div>
            <label></label>
            <input type = "submit" name="Create new item">
        </div>
    </form>

    <!-- Additional notes for the user -->
    <div class="l">
        <p>> if you want to add two element with same type, you can add 1 first then add another one using modify<p>
        <p>> type: URL, Image, Double, List link(name), String(default)<p>
    </div>
</div>
</body>
</html>
