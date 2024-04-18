<%@page contentType="text/html;" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        .login-form {
            /* border: 1px solid black; */
            width: 40%;
            margin: 30px auto;

            display: flex;
            flex-direction: column;
            row-gap: 30px;
        }

        .login-form h2 {
            text-align: center;
        }

        .login-form form {
            /* border: 1px solid red; */
            width: 100%;

            display: flex;
            flex-direction: column;
            row-gap: 20px;
        }

        .login-form form input {
            width: 100%;
        }

        .login-form form div {
            display: flex;
            column-gap: 20px;
            display: flex;
            flex-direction: column;
            row-gap: 20px;
        }

        .login-form form input[type=email],
        .login-form form input[type=password] {
            padding: 10px;
            outline: none;
            border: 1px solid lightgray;
        }

        .login-form form input[type=submit] {
            width: 30%;
            margin: auto;
            background-color: rgb(54, 54, 54);
            color: white;
            font-weight: bold;
        }
    </style>
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
            <input type="email" name="email" id="email" placeholder="Enter Email ID">
            <input type="password" name="password" id="password" placeholder="Enter Password">
        </div>
        <input type="submit" value="Login">
    </form>
</section>
</body>
</html>