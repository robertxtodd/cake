



<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
<style>
table, th, td {
    border: 1px solid black;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Main Page</title>
</head>

    <h2>Cake Manager</h2>

    <h3>List of cakes available:</h3>
    <table style="width:400px">
        <col width="25%">
        <col width="25%">
        <col width="50%">

        <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Image</th>
        </tr>

        <c:forEach items="${cakeList}" var="cake">
            <tr>
                <td>${cake.title}</td>
                <td>${cake.description}</td>
                <td><img src="<c:url value="${cake.image}"/>"/></td>
            </tr>
        </c:forEach>
    </table>

    <h3>Add a cake:</h3>
    <form action="/add" method="post">
        <table>
                <tr>
                    Add a Cake:
                </tr>
                <tr>
                    <td>Cake Name :</td>
                    <td width="25%"><input type="text" name="title"></td>
                </tr>
                <tr>
                    <td>Description :</td>
                    <td width="25%"><input type="text" name="desc"></td>
                </tr>
                <tr>
                    <td>Image :</td>
                    <td width="50%"><input type="text" name="image"></td>
                </tr>
        </table>
        <input type="submit" value="Add Cake">
        <h3>e.g. for image try  https://i1.wp.com/blog.gregwilson.co.uk/wp-content/uploads/2014/10/Gizza-Job.jpeg?resize=295%2C209</h3>
    </form>
</body>
</html>
