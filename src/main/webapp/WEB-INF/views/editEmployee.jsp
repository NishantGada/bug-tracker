<%@ page import="com.project.bugtracker.DAO.EmployeeDAO" %>
<%@ page import="com.project.bugtracker.pojo.Employee" %>
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
    <h2>Edit Employee Details</h2>
    <form action="/save-employee" method="post">
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
        <%
            int id = (Integer) request.getAttribute("editEmpId");
            System.out.println("Employee Edit ID: " + id);
            EmployeeDAO employeeDAO = new EmployeeDAO();
            Employee employee = employeeDAO.getEmployeeById(id);
            if (employee != null) {
        %>
        <div>
            <input
                    type="text"
                    name="edit-first-name"
                    id="first-name"
                    value="<%= employee.getFirstName() %>"
                    placeholder="Enter First Name"
                    required
            >
            <input
                    type="text"
                    name="edit-last-name"
                    id="last-name"
                    value="<%= employee.getLastName() %>"
                    placeholder="Enter Last Name"
                    required
            >
        </div>
        <div>
            <label for="employee-role">Select Employee Role</label>
            <select name="edit-employee-role" id="employee-role" required>
                <option value="Developer">Developer</option>
                <option value="Tester">Tester</option>
                <option value="Team Lead">Team Lead</option>
            </select>
            <input
                    type="text"
                    name="edit-supervisor-name"
                    id="supervisor-name"
                    value="<%= employee.getSupervisorName() %>"
                    placeholder="Enter Supervisor's Name"
                    required
            >
        </div>
        <div>
            <input
                    type="email"
                    name="edit-email"
                    id="email"
                    value="<%= employee.getEmail() %>"
                    placeholder="Enter Email ID"
                    required
            >
            <input
                    type="password"
                    name="edit-password"
                    id="password"
                    value="<%= employee.getPassword() %>"
                    placeholder="Enter new Password"
                    required
            >
            <input
                    type="password"
                    name="edit-confirm-password"
                    id="confirm-password"
                    placeholder="Confirm Password"
                    required
            >
        </div>
        <input type="submit" value="Save Changes">
        <% } %>
    </form>
</section>
</body>

</html>