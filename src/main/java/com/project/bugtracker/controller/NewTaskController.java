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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class NewTaskController {

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

    public void createNewEmployee(Employee employee, EmployeeDAO employeeDAO) {
        System.out.println("createNewEmployee employee: " + employee);
        employeeDAO.saveEmployee(employee);
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

    @GetMapping("/edit")
    public ModelAndView showEditPage(HttpSession session, BugDAO bugDAO, EmployeeDAO employeeDAO,Model model) {
        Employee loggedInEmployee = (Employee) session.getAttribute("loggedInEmployee");
        ModelAndView modelAndView = new ModelAndView();

        if (loggedInEmployee != null) {
            modelAndView.setViewName("edit");
            modelAndView.addObject("editBugId", session.getAttribute("editBugId"));
            model.addAttribute("employees", employeeDAO.getAllEmployees());
            model.addAttribute("role", loggedInEmployee.getEmployeeRole());
        } else {
            System.out.println("loggedInEmployee is NULL... ");
            model.addAttribute("invalid", true);
            model.addAttribute("message", "Please Login!");
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView showIndexPage(EmployeeDAO employeeDAO) {
        System.out.println("inside showLoginPage");

        List<Employee> employees = employeeDAO.getAllEmployees();
        System.out.println("List of Employees: " + employees);
        System.out.println("Count of Employees: " + employeeDAO.getEmployeeCount());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/validate-signup")
    public ModelAndView validatePostSignup(
            EmployeeDAO employeeDAO,
            @RequestParam("first-name") String firstName,
            @RequestParam("last-name") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm-password") String confirmPassword,
            @RequestParam("supervisor-name") String supervisorName,
            @RequestParam("employee-role") String employeeRole,
            Model model
    ) {
        String message = "";
        ModelAndView modelAndView = new ModelAndView();

        if (password.equals(confirmPassword)) {
            System.out.println("Passwords Match: " + password + " & " + confirmPassword);
            System.out.println("validatePostSignup email: " + email);
            boolean alreadyExists = employeeDAO.alreadyExists(email);
            System.out.println("validatePostSignup alreadyExists: " + alreadyExists);

            if (!alreadyExists) {
                int employeeCount = employeeDAO.getEmployeeCount();
                Employee employee = new Employee(
                        employeeCount, firstName, lastName, email, password, employeeRole, supervisorName
                );
                createNewEmployee(employee, employeeDAO);
                message = "Successful Sign up!";
                model.addAttribute("invalid", false);
                // model.addAttribute("message", message);
                modelAndView.setViewName("login");
            } else {
                System.out.println("Employee already in DB!");
                message = "Employee already in DB!";
                modelAndView.setViewName("redirect:/sign-up");
                model.addAttribute("message", message);
                model.addAttribute("error", true);
            }
        } else {
            message = "Passwords do not match!";
            model.addAttribute("message", message);
            model.addAttribute("error", true);
            modelAndView.setViewName("redirect:/sign-up");
        }

        return modelAndView;
    }

    @PostMapping("/validate-login")
    public ModelAndView validatePostLogin(
            EmployeeDAO employeeDAO,
            Model model,
            HttpSession session,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        String message = "";
        ModelAndView modelAndView = new ModelAndView();
        if (employeeDAO.isValidEmployee(email, password)) {
            Employee loggedInEmployee = employeeDAO.getEmployeeByEmail(email);
            session.setAttribute("loggedInEmployee", loggedInEmployee);
            // session.setAttribute("email", email);
            modelAndView.setViewName("redirect:/home");
        } else {
            message = "Invalid Credentials! Please re-check your information";
            model.addAttribute("invalid", true);
            model.addAttribute("message", message);
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage(Model model) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            model.addAttribute("invalid", false);

            /*
            model.addAllAttributes(
                    Map.of(
                            "firstName", firstName,
                            "lastName", lastName,
                            "email", email,
                            "password", password,
                            "confirmPassword", confirmPassword,
                            "supervisorName", supervisorName,
                            "employeeRole", employeeRole
                    )
            );
             */

            return modelAndView;
    }

    @GetMapping("/sign-up")
    public ModelAndView showSignupPage(Model model) {
        System.out.println("inside showSignupPage");
        ModelAndView modelAndView = new ModelAndView();
        // String message = "";
        // model.addAttribute("message", message);
        model.addAttribute("error", false);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView showHomePage(
            Model model,
            HttpSession session,
            BugDAO bugDAO,
            EmployeeDAO employeeDAO
    ) {
        System.out.println("showHomePage!");
        Employee loggedInEmployee = (Employee) session.getAttribute("loggedInEmployee");

        ModelAndView modelAndView = new ModelAndView();

        if (loggedInEmployee != null) {
            System.out.println("logged in employee: " + loggedInEmployee.getFirstName());
            int loggedInEmployeeId = loggedInEmployee.getEmpId();
            System.out.println("logged in employee id: " + loggedInEmployeeId);

            modelAndView.setViewName("home");
            model.addAttribute("invalid", false);
            model.addAttribute("loginSuccessful", true);

            modelAndView.addObject("firstName", loggedInEmployee.getFirstName());
            modelAndView.addObject("lastName", loggedInEmployee.getLastName());
            modelAndView.addObject("email", loggedInEmployee.getEmail());
            modelAndView.addObject("password", loggedInEmployee.getPassword());
            modelAndView.addObject("supervisorName", loggedInEmployee.getSupervisorName());
            modelAndView.addObject("employeeRole", loggedInEmployee.getEmployeeRole());
            // modelAndView.addObject("bugsList", loggedInEmployee.getBugsList());
            if (loggedInEmployee.getEmployeeRole().equals("Team Lead")) {
                modelAndView.addObject("bugsList", bugDAO.getAllBugs());
                modelAndView.addObject("employeeList", employeeDAO.getAllEmployees());
                modelAndView.addObject("deleteAccess", true);
                modelAndView.addObject("doneList", bugDAO.getBugsByStatus("Done"));
            } else if ((loggedInEmployee.getEmployeeRole().equals("Tester"))) {
                // System.out.println("bugs in review: " + bugDAO.getBugsInReview());
                modelAndView.addObject("bugsList", bugDAO.getBugsByStatus("In Review"));
                modelAndView.addObject("deleteAccess", true);
            } else {
                modelAndView.addObject("bugsList", bugDAO.getBugsByEmployeeId(loggedInEmployeeId));
                modelAndView.addObject("deleteAccess", false);
            }
        } else {
            System.out.println("loggedInEmployee is NULL... ");
            model.addAttribute("invalid", true);
            model.addAttribute("message", "Please Login!");
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    /*
    @PostMapping("/confirmation")
    public ModelAndView showConfirmationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("newTask");
        return modelAndView;
    }
     */
}