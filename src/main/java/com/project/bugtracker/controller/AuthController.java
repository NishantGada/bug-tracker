package com.project.bugtracker.controller;

import com.project.bugtracker.DAO.EmployeeDAO;
import com.project.bugtracker.pojo.Employee;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {
    @GetMapping("/sign-up")
    public ModelAndView showSignupPage(Model model) {
        System.out.println("inside showSignupPage");
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("error", false);
        modelAndView.setViewName("signup");
        return modelAndView;
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

    @GetMapping("/login")
    public ModelAndView showLoginPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        model.addAttribute("invalid", false);
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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    public void createNewEmployee(Employee employee, EmployeeDAO employeeDAO) {
        System.out.println("createNewEmployee employee: " + employee);
        employeeDAO.saveEmployee(employee);
    }
}
