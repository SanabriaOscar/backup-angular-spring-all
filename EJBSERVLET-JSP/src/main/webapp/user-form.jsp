<%@page import="model.User"%>
<%@page import="ejb.UserBeanImpl"%>
<%@page import="ejb.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Form</title>
</head>
<body>
    <h1>User Form</h1>
    <form method="POST" action="/users/*">
        <% if (request.getParameter("id") != null) {
            int userId = Integer.parseInt(request.getParameter("id"));
            UserBeanImpl userBean = new UserBeanImpl();
            User user = userBean.getUser(userId);
            if (user != null) {
        %>
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="id" value="<%= userId %>">
            <label>Name:</label>
            <input type="text" name="name" value="<%= user.getName() %>">
        <% }
           } else { %>
            <label>Name:</label>
            <input type="text" name="name">
        <% } %>
        <input type="submit" value="Save">
    </form>
</body>
</html>
