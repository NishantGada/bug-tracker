package com.project.bugtracker.controller;

import com.project.bugtracker.DAO.BugDAO;
import com.project.bugtracker.DAO.EmployeeDAO;
import com.project.bugtracker.pojo.Bug;
import com.project.bugtracker.pojo.Employee;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class BugController {

    @PostMapping("/create-new-task")
    public String createNewTask(
            @RequestParam("task-title") String bugTitle,
            @RequestParam("task-priority") String bugPriority,
            @RequestParam("task-description") String bugDescription,
            @RequestParam("task-due-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date bugDueDate,
            @RequestParam("task-assignee") String assigneeId,
            BugDAO bugDAO,
            EmployeeDAO employeeDAO
    ){
        System.out.println("createNewTask!");

        System.out.println("Title: " + bugTitle);
        System.out.println("Priority: " + bugPriority);
        System.out.println("Description: " + bugDescription);
        System.out.println("Due Date: " + bugDueDate);
        System.out.println("Assignee: " + assigneeId);

        Employee bugAssignee = employeeDAO.getEmployeeById(Integer.parseInt(assigneeId));
        System.out.println("bugAssignee: " + bugAssignee);

        // int bugCount = bugDAO.getBugCount();
        Bug bug = new Bug(bugTitle, bugDescription, bugPriority, bugDueDate, bugAssignee);
        System.out.println("bug: " + bug);
        createNewBug(bug, bugDAO);

        return "redirect:/home";
    }

    @GetMapping("/new-task")
    public String newTask(EmployeeDAO employeeDAO, BugDAO bugDAO, Model model, Employee employee) {
        System.out.println("newTask");
        System.out.println("Employee Names: " + employeeDAO.getAllEmployeeNames());
        System.out.println("All Bugs: " + bugDAO.getAllBugs());
        model.addAttribute("employeeNames", employeeDAO.getAllEmployeeNames());
        model.addAttribute("employees", employeeDAO.getAllEmployees());
        return "newTask";
    }

    public void createNewBug(Bug bug, BugDAO bugDAO) {
        System.out.println("createNewBug bug: " + bug);
        bugDAO.saveBug(bug);
        System.out.println("New Bug Created Successfully!");
    }

    @PostMapping("/save-changes")
    public String saveEditedBug(
            @RequestParam("edit-task-title") String editBugTitle,
            @RequestParam("edit-bug-id") String editBugId,
            @RequestParam("edit-task-priority") String editBugPriority,
            @RequestParam("edit-task-description") String editBugDescription,
            @RequestParam("edit-task-due-date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date editBugDueDate,
            @RequestParam("edit-task-status") String editBugStatus,
            @RequestParam("edit-task-assignee") String editAssigneeId,
            BugDAO bugDAO,
            EmployeeDAO employeeDAO
    ) {
        try {
            System.out.println("Saving Changes.. ");

            System.out.println("editBugTitle: " + editBugTitle);
            System.out.println("editBugId: " + editBugId);
            System.out.println("editBugPriority: " + editBugPriority);
            System.out.println("editBugDescription: " + editBugDescription);
            // bugDAO.getBugById(Integer.parseInt(editBugId)).getBugDueDate()
            System.out.println("editBugDueDate: " + editBugDueDate);
            System.out.println("editBugStatus: " + editBugStatus);
            System.out.println("editAssigneeId: " + editAssigneeId);

            Employee bugAssignee = employeeDAO.getEmployeeById(Integer.parseInt(editAssigneeId));
            System.out.println("bugAssignee: " + bugAssignee);

            Bug bug = new Bug(editBugTitle, editBugDescription, editBugPriority, editBugDueDate, bugAssignee, editBugStatus);
            bug.setBugID(Integer.parseInt(editBugId));
            System.out.println("Edit Bug: " + bug);

            bugDAO.updateBug(bug);
            return "redirect:/home";
        } catch (Exception e) {
            System.out.println("Exception e: " + e);
            return "redirect:/edit";
        }
    }

    @PostMapping("delete-bug")
    public String deleteBug(
            @RequestParam("bugId") int bugId,
            BugDAO bugDAO
    ) {
        System.out.println("bugId to be deleted: " + bugId);
        bugDAO.deleteBug(bugId);
        return "redirect:/home";
    }

    @PostMapping("/edit-bug")
    public String editBug(@RequestParam("bugId") int bugId, BugDAO bugDAO, HttpSession session) {
        System.out.println("bugId to be edited: " + bugId);
        session.setAttribute("editBugId", bugId);
        return "redirect:/edit";
    }
}