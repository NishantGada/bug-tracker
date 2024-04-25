<%@ page import="java.util.List" %>
<%@ page import="com.project.bugtracker.pojo.Bug" %>
<%@ page import="com.project.bugtracker.pojo.Employee" %>
<%@ page import="java.util.Objects" %>
<%@page contentType="text/html;" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            font-family: Arial, Helvetica, sans-serif
        }

        body {
            padding: 0 50px;
            display: flex;
            flex-direction: column;
            row-gap: 30px;
        }

        nav {
            border: 1px solid black;
            background: rgb(54, 54, 54);
            color: white;
            padding: 20px;
            margin-top: 20px;

            display: flex;
            justify-content: space-between;
            column-gap: 20px;
        }

        nav h2 {
            font-style: italic;
        }

        nav div {
            display: flex;
            column-gap: 20px;
        }

        nav a {
            text-decoration: none;
            color: white;
        }

        .action {
            display: flex;
            justify-content: space-between;
        }

        form input[type=text] {
            background: none;
            padding: 10px;
            outline: none;
            border: 1px solid lightgray;
            width: 200px;
            /* margin: 20px 0; */
        }

        form input[type=submit], .action button {
            background: none;
            border: 1px solid lightgray;
            padding: 10px;
            width: 100px;
            cursor: pointer;
        }

        .bug-tracker-container {
            /* border: 1px solid black; */
            /* margin: 20px 0; */

            display: flex;
            justify-content: space-between;
            column-gap: 20px;
        }

        .bug-tracker-sub-container {
            /* border: 1px solid purple; */
            background-color: rgb(241, 241, 241);
            width: 100%;
            padding: 10px;
        }

        .bug-tracker-sub-container h3 {
            font-size: 16px;
            color: rgb(159, 159, 159);
        }

        .bug-tracker-sub-container span {
            margin-left: 8px;
        }

        .list-item {
            background-color: white;
            box-shadow: 3px 3px 15px lightgray;
            max-width: 100%;
            height: 80px;
            margin: 20px 0;
            padding: 10px;

            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .list-item div:last-child {
            display: flex;
            justify-content: space-between;
        }

        .list-item-title {
            font-weight: bold;
        }

        .list-item-desc {
            color: rgb(128, 128, 128);
            font-size: 13px;
        }

        .list-item-assignee {
            font-size: 15px;
            font-weight: bold;
        }

        .list-item-due {
            font-style: italic;
            font-size: 13px;
        }

        .bug-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        .bug-table th, .bug-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        .bug-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        .bug-table tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }

    </style>
</head>

<body>
<nav>
    <h2>JIRAFFE</h2>
    <div>
        <%
            boolean loginSuccessful = (boolean) request.getAttribute("loginSuccessful");
            if (loginSuccessful) {
        %>
            <a href="/logout">Logout</a>
        <%
            } else {
        %>
            <a href="/sign-up">Sign Up</a>
            <a href="/login">Login</a>
        <%
            }
        %>
    </div>
</nav>

<header>
    <h3>Welcome, <%= request.getAttribute("firstName") %></h3>
    <p>Email: <%= request.getAttribute("email") %></p>
    <p>Role: <%= request.getAttribute("employeeRole") %></p>
    <p>Supervisor: <%= request.getAttribute("supervisorName") %></p>
</header>
<%
    List<Bug> bugs = (List<Bug>) request.getAttribute("bugsList");
    Boolean deleteAccess = (Boolean) request.getAttribute("deleteAccess");
    if (bugs.isEmpty()) {
%>
    <p>No Pending Tasks, Yay!</p>
<% } else { %>
<% if (request.getAttribute("employeeRole").equals("Developer")) { %>
    <%= request.getAttribute("firstName") %>'s Tasks:
    <section>
        <table class="bug-table">
                <thead>
                <tr>
                    <th>Bug ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Priority</th>
                    <th>Due Date</th>
                    <th>Assignee</th>
                    <th>Status</th>
                    <th>Edit</th>
                    <% if (deleteAccess) { %>
                    <th>Remove</th>
                    <% } %>
                </tr>
                </thead>
                <tbody>
                <% for (Bug bug : bugs) {
                    // if (!Objects.equals(bug.getBugStatus(), "DONE")) {
                %>
                <tr>
                    <td><%= bug.getBugID() %></td>
                    <td><%= bug.getBugTitle() %></td>
                    <td><%= bug.getBugDescription() %></td>
                    <td><%= bug.getBugPriority() %></td>
                    <td><%= bug.getBugDueDate() %></td>
                    <td><%= bug.getAssignedTo().getFirstName() %></td>
                    <td><%= bug.getBugStatus() %></td>
                    <td>
                        <form action="/edit-bug" method="post">
                            <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                            <input type="submit" value="Edit" />
                        </form>
                    </td>
                    <% if (deleteAccess) { %>
                    <td>
                        <form
                                action="/delete-bug"
                                method="post"
                                onsubmit="return confirm('Are you sure you want to delete this bug?');"
                        >
                            <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                            <input type="submit" value="Delete" />
                        </form>
                    </td>
                    <% } %>
                </tr>
                <% } %>
                </tbody>
            </table>
    </section>
<% }} %>

<%
    if (request.getAttribute("employeeRole").equals("Tester")) {
        List< Bug > reviewList = (List<Bug>) request.getAttribute("reviewList");
%>
    <%= request.getAttribute("firstName") %>'s Tasks:
    <section>
        <table class="bug-table">
            <thead>
            <tr>
                <th>Bug ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Priority</th>
                <th>Due Date</th>
                <th>Assignee</th>
                <th>Status</th>
                <th>Edit</th>
                <% if (deleteAccess) { %>
                <th>Remove</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% for (Bug bug : bugs) {
                if (!Objects.equals(bug.getBugStatus(), "DONE") && !Objects.equals(bug.getBugStatus(), "In Review")) {
            %>
            <tr>
                <td><%= bug.getBugID() %></td>
                <td><%= bug.getBugTitle() %></td>
                <td><%= bug.getBugDescription() %></td>
                <td><%= bug.getBugPriority() %></td>
                <td><%= bug.getBugDueDate() %></td>
                <td><%= bug.getAssignedTo().getFirstName() %></td>
                <td><%= bug.getBugStatus() %></td>
                <td>
                    <form action="/edit-bug" method="post">
                        <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                        <input type="submit" value="Edit" />
                    </form>
                </td>
                <% if (deleteAccess) { %>
                <td>
                    <form
                            action="/delete-bug"
                            method="post"
                            onsubmit="return confirm('Are you sure you want to delete this bug?');"
                    >
                        <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
                <% } %>
            </tr>
            <% }} %>
            </tbody>
        </table>
    </section>
    <section>
        <p>To be reviewed: </p>
        <table class="bug-table">
            <thead>
            <tr>
                <th>Bug ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Priority</th>
                <th>Due Date</th>
                <th>Assignee</th>
                <th>Status</th>
                <th>Edit</th>
                <% if (deleteAccess) { %>
                <th>Remove</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% for (Bug bug : reviewList) {
                if (Objects.equals(bug.getBugStatus(), "In Review")) {
            %>
            <tr>
                <td><%= bug.getBugID() %></td>
                <td><%= bug.getBugTitle() %></td>
                <td><%= bug.getBugDescription() %></td>
                <td><%= bug.getBugPriority() %></td>
                <td><%= bug.getBugDueDate() %></td>
                <td><%= bug.getAssignedTo().getFirstName() %></td>
                <td><%= bug.getBugStatus() %></td>
                <td>
                    <form action="/edit-bug" method="post">
                        <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                        <input type="submit" value="Edit" />
                    </form>
                </td>
                <% if (deleteAccess) { %>
                <td>
                    <form
                            action="/delete-bug"
                            method="post"
                            onsubmit="return confirm('Are you sure you want to delete this bug?');"
                    >
                        <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
                <% } %>
            </tr>
            <% }} %>
            </tbody>
        </table>
    </section>
<% } %>


<%
    if (request.getAttribute("employeeRole").equals("Team Lead")) {
%>
    <a href="/new-task">Create new task</a>
    <br>
    All Pending Tasks:
    <section>
        <table class="bug-table">
            <thead>
            <tr>
                <th>Bug ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Priority</th>
                <th>Due Date</th>
                <th>Assignee</th>
                <th>Status</th>
                <th>Edit</th>
                <% if (deleteAccess) { %>
                <th>Remove</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% for (Bug bug : bugs) {
                if (!Objects.equals(bug.getBugStatus(), "DONE")) {
            %>
            <tr>
                <td><%= bug.getBugID() %></td>
                <td><%= bug.getBugTitle() %></td>
                <td><%= bug.getBugDescription() %></td>
                <td><%= bug.getBugPriority() %></td>
                <td><%= bug.getBugDueDate() %></td>
                <td><%= bug.getAssignedTo().getFirstName() %></td>
                <td><%= bug.getBugStatus() %></td>
                <td>
                    <form action="/edit-bug" method="post">
                        <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                        <input type="submit" value="Edit" />
                    </form>
                </td>
                <% if (deleteAccess) { %>
                <td>
                    <form
                            action="/delete-bug"
                            method="post"
                            onsubmit="return confirm('Are you sure you want to delete this bug?');"
                    >
                        <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
                <% } %>
            </tr>
            <% }} %>
            </tbody>
        </table>
    </section>
    <section>
        <br>
        Employee List:
        <% List<Employee> employees = (List<Employee>) request.getAttribute("employeeList");%>
        <table class="bug-table">
            <thead>
            <tr>
                <th>Employee ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Password</th>
                <th>Role</th>
                <th>Supervisor</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
                <% for (Employee employee : employees) { %>
                <tr>
                    <td><%= employee.getEmpId() %></td>
                    <td><%= employee.getFirstName() %></td>
                    <td><%= employee.getLastName() %></td>
                    <td><%= employee.getEmail() %></td>
                    <td><%= employee.getPassword() %></td>
                    <td><%= employee.getEmployeeRole() %></td>
                    <td><%= employee.getSupervisorName() %></td>
                    <td>
                        <form action="/edit-employee-details" method="post">
                            <input type="hidden" name="empId" value="<%= employee.getEmpId() %>" />
                            <input type="submit" value="Edit" />
                        </form>
                    </td>
                    <% if (deleteAccess) { %>
                    <td>
                        <form
                                action="/delete-employee"
                                method="post"
                                onsubmit="return confirm('Are you sure you want to delete this employee?');"
                        >
                            <input type="hidden" name="empId" value="<%= employee.getEmpId() %>" />
                            <input type="submit" value="Delete" />
                        </form>
                    </td>
                    <% } %>
                </tr>
                <%}%>
            </tbody>
        </table>
    </section>
    <section>
        <p>Completed Tasks:</p>
        <% List<Bug> bugsDone = (List<Bug>) request.getAttribute("doneList"); %>
        <table class="bug-table">
            <thead>
            <tr>
                <th>Bug ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Priority</th>
                <th>Due Date</th>
                <th>Assignee</th>
                <th>Status</th>
                <th>Edit</th>
                <% if (deleteAccess) { %>
                <th>Remove</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% for (Bug bug : bugsDone) { %>
            <tr>
                <td><%= bug.getBugID() %></td>
                <td><%= bug.getBugTitle() %></td>
                <td><%= bug.getBugDescription() %></td>
                <td><%= bug.getBugPriority() %></td>
                <td><%= bug.getBugDueDate() %></td>
                <td><%= bug.getAssignedTo().getFirstName() %></td>
                <td><%= bug.getBugStatus() %></td>
                <td>
                    <form action="/edit-bug" method="post">
                        <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                        <input type="submit" value="Edit" />
                    </form>
                </td>
                <% if (deleteAccess) { %>
                <td>
                    <form
                            action="/delete-bug"
                            method="post"
                            onsubmit="return confirm('Are you sure you want to delete this bug?');"
                    >
                        <input type="hidden" name="bugId" value="<%= bug.getBugID() %>" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
                <% } %>
            </tr>
            <% } %>
            </tbody>
        </table>
    </section>
<% } %>

</body>
</html>