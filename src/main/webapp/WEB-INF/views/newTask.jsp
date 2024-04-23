<%@ page import="com.project.bugtracker.pojo.Employee" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html;" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Task</title>
</head>
<body>
    <div>
        <h4>ADD NEW TICKET</h4>
    </div>
    <form action="/create-new-task" method="post">
        <div>
            <label for="task-title">Enter task title</label>
            <input type="text" name="task-title" id="task-title" required>
        </div>

        <div>
            <label for="task-priority">Choose Task Priority</label>
            <select name="task-priority" id="task-priority" required>
                <option value="high">High</option>
                <option value="moderate">Moderate</option>
                <option value="low">Low</option>
            </select>
        </div>

        <div>
            <label for="task-description">Enter Task Description</label>
            <textarea name="task-description" id="task-description" cols="30" rows="10" required></textarea>
        </div>

        <div>
            <label for="task-due-date">Enter Task Due Date</label>
            <input
                    type="date"
                    name="task-due-date"
                    id="task-due-date"
                    min="<%= java.time.LocalDate.now().toString() %>"
                    required
            >
        </div>

        <div>
            <label for="task-assignee">Select Assignee</label>
            <%
                Object attribute = request.getAttribute("employees");
                if (attribute instanceof List<?>) {
                    List<Employee> employees = (List<Employee>) attribute;
            %>
            <select name="task-assignee" id="task-assignee" required>
                <% for (Employee employee : employees) { %>
                <option value="<%= employee.getEmpId() %>"><%= employee.getFirstName() %></option>
                <% } %>
            </select>
            <%
                }
            %>
        </div>

        <input type="submit" value="Create">
    </form>
</body>
</html>