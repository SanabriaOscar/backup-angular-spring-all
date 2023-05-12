<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
</head>
<body>
    <h1>User List</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% List<User> users = (List<User>) request.getAttribute("users"); %>
            <% if (users != null) { %>
            <% for (User user :users) { %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getName() %></td>
                    <td>
                        <a href="user-form.jsp?id=<%= user.getId() %>">Edit</a>
                        <a href="users?id=<%= user.getId() %>&_method=DELETE" onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
                    </td>
                </tr>
            <% } %>
            <% } %>
        </tbody>
    </table>
    <a href="user-form.jsp">Create User</a>
</body>
</html>
