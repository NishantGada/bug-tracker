<%@page contentType="text/html;" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/static/css/login.css">
</head>
<body>
<nav>
    <h2><a href="/">JIRAFFE</a></h2>
    <div>
        <a href="/sign-up">Sign Up</a>
    </div>
</nav>

<section class="login-form">
    <h2>Login!</h2>
    <form action="/validate-login" method="post">
<%--        <c:if test="${invalid}">--%>
<%--            <p>Invalid Credentials!</p>--%>
<%--        </c:if>--%>
        <%
            boolean invalid = (boolean) request.getAttribute("invalid");
            String message = (String) request.getAttribute("message");
        %>
        <%
            if (invalid) {
        %>
            <p><%= message %></p>
        <%
            }
        %>
        <div>
            <input type="email" name="email" id="email" placeholder="Enter Email ID" required>
            <input type="password" name="password" id="password" placeholder="Enter Password" required>
        </div>
        <input type="submit" value="Login">
    </form>
</section>
</body>
</html>