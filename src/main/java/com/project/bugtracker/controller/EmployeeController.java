package com.project.bugtracker.controller;

import com.project.bugtracker.DAO.BugDAO;
import com.project.bugtracker.DAO.EmployeeDAO;
import com.project.bugtracker.pojo.Bug;
import com.project.bugtracker.pojo.Employee;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EmployeeController {
    @PostMapping("/edit-employee-details")
    public String editEmployeeDetails(@RequestParam("empId") int empId, HttpSession session) {
        System.out.println("empId to be edited: " + empId);
        session.setAttribute("editEmpId", empId);
        return "redirect:/edit-employee";
    }

    @PostMapping("/save-employee")
    public ModelAndView saveEmployeeChanges(
            @RequestParam("edit-first-name") String firstName,
            @RequestParam("edit-last-name") String lastName,
            @RequestParam("edit-email") String email,
            @RequestParam("edit-password") String password,
            @RequestParam("edit-confirm-password") String confirmPassword,
            @RequestParam("edit-supervisor-name") String supervisorName,
            @RequestParam("edit-employee-role") String employeeRole,
            EmployeeDAO employeeDAO,
            Model model,
            HttpSession session
    ) {
        ModelAndView modelAndView = new ModelAndView();
        String message;

        System.out.println("edited firstName: "+  firstName);
        System.out.println("edited lastName: "+  lastName);
        System.out.println("edited email: "+  email);
        System.out.println("edited password: "+  password);
        System.out.println("edited confirmPassword: "+  confirmPassword);
        System.out.println("edited supervisorName: "+  supervisorName);
        System.out.println("edited employeeRole: "+  employeeRole);

        if (password.equals(confirmPassword)) {
            List <Bug> bugsList = employeeDAO.getEmployeeBugs( (Integer) session.getAttribute("editEmpId"));
            Employee employee = new Employee((Integer) session.getAttribute("editEmpId"), firstName, lastName, email, password, employeeRole, supervisorName, bugsList);
            System.out.println("Updated employee: " + employee);

            employeeDAO.updateEmployee(employee);

            modelAndView.setViewName("redirect:/home");
        } else {
            message = "Passwords do not match!";
            model.addAttribute("message", message);
            model.addAttribute("error", true);
            modelAndView.setViewName("redirect:/edit-employee");
        }

        return modelAndView;
    }
}