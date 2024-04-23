<%@ page import="java.util.List" %>
<%@ page import="com.project.bugtracker.pojo.Employee" %>
<%@ page import="com.project.bugtracker.DAO.BugDAO" %>
<%@ page import="com.project.bugtracker.pojo.Bug" %>
<%@ page import="java.time.LocalDate" %>
<%@page contentType="text/html;" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit</title>
</head>
<body>
    <div>
        <h4>EDIT BUG</h4>
    </div>
    <%
        int id = (Integer) request.getAttribute("editBugId");
        String role = (String) request.getAttribute("role");
        boolean disabled;
        if (role.equals("Team Lead")) {
            disabled = false;
        } else {
            disabled = true;
        }
        System.out.println("Edit Page: role & disabled = " + role + disabled);
    %>
    <p>Edit ID: <%= id %></p>
    <form action="/save-changes" method="post">
        <%
            BugDAO bugDAO = new BugDAO();
            Bug bug = bugDAO.getBugById(id);
            if (bug != null) {
        %>
        <input type="hidden" name="edit-bug-id" value="<%= bug.getBugID() %>" />
        <div>
            <label for="edit-task-title">Enter task title</label>
            <input
                    type="text"
                    name="edit-task-title"
                    id="edit-task-title"
                    value="<%= bug.getBugTitle() %>"
                    required
            >
        </div>

        <div>
            <label for="edit-task-priority">Choose Task Priority</label>
            <select name="edit-task-priority" id="edit-task-priority" required>
                <option value="high" <%= bug.getBugPriority().equals("high") ? "selected" : "" %>>High</option>
                <option value="moderate" <%= bug.getBugPriority().equals("moderate") ? "selected" : "" %>>Moderate</option>
                <option value="low" <%= bug.getBugPriority().equals("low") ? "selected" : "" %>>Low</option>
            </select>
        </div>

        <div>
            <label for="edit-task-description">Enter Task Description</label>
            <textarea
                    name="edit-task-description"
                    id="edit-task-description"
                    cols="30" rows="10"
                    required
            >
                <%= bug.getBugDescription() %>
            </textarea>
        </div>

        <div>
            <label for="edit-task-due-date">Enter Task Due Date</label>
            <input
                    type="date"
                    name="edit-task-due-date"
                    id="edit-task-due-date"
                    value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(bug.getBugDueDate()) %>"
                    min="<%= java.time.LocalDate.now().toString() %>"
                    disabled="<%= !editAccess %>"
                    required
            >
        </div>


        <div>
            <label for="task-status">Select Status for bug:</label>
            <select id="task-status" name="edit-task-status" required>
                <option value="TODO">TODO</option>
                <option value="In Progress">In Progress</option>
                <option value="In Review">In Review</option>
                <option value="DONE">DONE</option>
            </select>
        </div>

        <div>
            <label for="edit-task-assignee">Select Assignee</label>
            <select name="edit-task-assignee" id="edit-task-assignee" required>
                <%
                    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
                    for (Employee employee : employees) {
                %>
                <option
                        value="<%= employee.getEmpId() %>"
                        <%= bug.getAssignedTo().getEmpId() == employee.getEmpId()
                                ? "selected"
                                : "" %>
                >
                    <%= employee.getFirstName() %>
                </option>
                <% } %>
            </select>
        </div>

        <input type="submit" value="Update">
        <%
            }
        %>
    </form>
</body>
</html>