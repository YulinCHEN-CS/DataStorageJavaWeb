<%--
  Display a web page with a form to update item information.
  Created by IntelliJ IDEA.
  User: Stephen Chen
  Date: 13/03/2023
  Time: 00:12
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${item.name}</title>
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

    /* Style the display of text in the container */
    .l {
        display: inline-block;
        text-align: left;
        max-width: 55%;
        font-size: 15px;
    }

    /* Set styling for the table */
    table {
        /*border-collapse: collapse;*/
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

    <!-- Display the name of the item -->
    <h2>Item name: ${item.name}</h2>

    <!-- Table to display the object information -->
    <table border="1">
        <tr>
            <!-- Table header -->
            <th>Type:Content</th>
        </tr>

        <!-- Initialize the count variable -->
        <c:set var="count" value="0" scope="request" />

        <!-- Get the list of raw objects for the item -->
        <c:set var="objects" value="${item.get_raw_objects()}" scope="request"/>

        <!-- Get the list of raw objects for the item -->
        <c:forEach items="${objects}" var="object">

            <!-- Get the list of raw objects for the item in request filed-->
            <c:set var="current_object" value="${object}" scope="request"/>
            <tr>
                <c:set var="type" value="${object.getClass().getSimpleName()}"/>

                <!-- Choose what to display based on the object type -->
                <c:choose>
                    <c:when test="${type eq 'URL'}">
                        <td>
                            <input type="text" name = "${count}" value="${type}:${object.toExternalForm()}" style="width: 600px"/><br/>
                        </td>
                    </c:when>
                    <c:when test="${type eq 'Image'}">
                        <td>
                        <div class="image">
                            <input type="text" name="${count}" value="${type}:${object.source}" style="width: 600px"/><br/>
                            <img src="data:image/png;base64,${object.encodedImage}"><br/>
                        </div>
                        </td>
                    </c:when>
                    <c:when test="${type eq 'Double'}">
                        <td>
                            <input type="text" name = "${count}" value="${type}:${object}" style="width: 600px"/><br/>
                        </td>
                    </c:when>
                    <c:when test="${type eq 'DoublyLinkedHashTable'}">
                        <td>
                            <input type="text" name = "${count}" value="List:${object.name}" style="width: 600px"/><br/>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td>
                            <input type="text" name = "${count}" value="${type}:${object}" style="width: 600px"/><br/>
                        </td>
                    </c:otherwise>

                </c:choose>

                <!-- Increment the count variable at the end of each iteration-->
                <c:set var="count" value="${count + 1}" scope="request"/>
            </tr>
        </c:forEach>

        <!-- Add a row for new object input -->
        <tr>
            <td>
                <input type="text" name = "${count}" style="width: 600px" /><br/>
            </td>
        </tr>
        <input type="hidden" name="count" value="${count}" />
    </table>

    <!-- Additional notes for the user -->
    <div class="l">
        <p>> Please enter data you want to add as (Type:Content) to the last row<br/></p>
        <p>> Supported type: URL, Image, Double, String, DoublyLinkedHashTable(List)<br/></p>
        <p>> Just enter List:ListName if you are trying to link another list. If ListName can not be found, it will create a new one for you<br/></p>
        <p>> Total number of objects: <c:out value="${count}" default="0"/><br/></p>
    </div>
    <br/>


    <!-- Button to return to the list -->
    <form action="/list?method=traverse">
        <input type="submit" value="Return to list">
    </form>

    <!-- Button to submit the updated item information -->
    <form action = "/list?method=update&name=${item.name}" method = "post">
        <input type = "submit" name="update"/>
    </form>

</div>
</body>
</html>
