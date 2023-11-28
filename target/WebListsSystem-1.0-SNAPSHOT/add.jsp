<%--
  Created by IntelliJ IDEA.
  User: stephchen
  Date: 12/03/2023
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>new node</title>
</head>
<style>

        label{

        width: 150px;

        display: inline-block;

        text-align: right;

        }
</style>
<body>

    <form action = "/list?method=add" method = "post">
        <div>
            <label> Item name: </label>
            <input type="text" name="name"/><br/>
        </div>
        <br/>
        <div>
            <label> String: </label>
            <input type="text" name="strings"/><br/>
        </div>
        <br/>
        <div>
            <label> Url: </label>
            <input type="text" name="urls"/><br/>
        </div>
        <br/>
        <div>
            <label> Image(as url or absolute path): </label>
            <input type="text" name="images"/><br/>
        </div>
        <br/>
        <div>
            <label> Double: </label>
            <input type="text" name="doubles"/><br/>
        </div>
        <br/>
        <div>
            <label></label>
            <input type = "submit" name="Create new item">
        </div>

    </form>
> if you want to add two element with same type, you can add 1 first then add another one using modify<br/>
> type: URL, Image, Double, String(default)
</body>
</html>
