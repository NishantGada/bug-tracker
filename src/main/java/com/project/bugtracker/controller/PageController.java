package com.project.bugtracker.controller;

import com.project.bugtracker.DAO.BugDAO;
import com.project.bugtracker.DAO.EmployeeDAO;
import com.project.bugtracker.pojo.Employee;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PageController {
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
                modelAndView.addObject("bugsList", bugDAO.getBugsByEmployeeId(loggedInEmployeeId));
                modelAndView.addObject("reviewList", bugDAO.getBugsByStatus("In Review"));
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

    @GetMapping("/edit")
    public ModelAndView showEditPage(HttpSession session, EmployeeDAO employeeDAO,Model model) {
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

    @GetMapping("/edit-employee")
    public ModelAndView showEditEmployeePage(HttpSession session, EmployeeDAO employeeDAO, Model model) {
        Employee loggedInEmployee = (Employee) session.getAttribute("loggedInEmployee");
        ModelAndView modelAndView = new ModelAndView();

        if (loggedInEmployee != null) {
            modelAndView.setViewName("editEmployee");
            modelAndView.addObject("editEmpId", session.getAttribute("editEmpId"));
            model.addAttribute("error", false);
            // model.addAttribute("employees", employeeDAO.getAllEmployees());
            // model.addAttribute("role", loggedInEmployee.getEmployeeRole());
        } else {
            System.out.println("loggedInEmployee is NULL... ");
            model.addAttribute("invalid", true);
            model.addAttribute("message", "Please Login!");
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }
}