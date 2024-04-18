<%@page contentType="text/html;" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign up</title>
</head>

<body>
<nav>
    <h2><a href="/">JIRAFFE</a></h2>
    <div>
        <a href="/login">Login</a>
    </div>
</nav>

<section class="signup-form">
    <h2>Sign up!</h2>
    <form action="/validate-signup" method="post">
        <%
            boolean error = (boolean) request.getAttribute("error");
            String message = (String) request.getAttribute("message");
        %>
        <%
            if (error) {
        %>
            <p><%= message %></p>
        <%
            }
        %>
        <div>
            <input type="text" name="first-name" id="first-name" placeholder="Enter First Name">
            <input type="text" name="last-name" id="last-name" placeholder="Enter Last Name">
        </div>
        <div>
<%--            <input type="text" name="role" id="role" placeholder="Enter Employee Role">--%>
            <label for="employee-role">Select Employee Role</label>
            <select name="employee-role" id="employee-role">
                <option value="Developer">Developer</option>
                <option value="Tester">Tester</option>
                <option value="Team Lead">Team Lead</option>
            </select>
            <input type="text" name="supervisor-name" id="supervisor-name" placeholder="Enter Supervisor's Name">
        </div>
        <div>
            <input type="email" name="email" id="email" placeholder="Enter Email ID">
            <input type="password" name="password" id="password" placeholder="Create a Password">
            <input type="password" name="confirm-password" id="confirm-password" placeholder="Confirm Password">
        </div>
        <input type="submit" value="Sign up">
    </form>
</section>
</body>

</html>