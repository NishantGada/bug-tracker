//package com.project.bugtracker.controller;
//
//import com.project.bugtracker.pojo.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//
//@Controller
//public class ModifyTaskController {
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @PostMapping("/saveChanges")
//    public String saveChanges(@RequestParam("edit-task-title") String title,
//                              @RequestParam("edit-task-priority") String priority,
//                              @RequestParam("edit-task-description") String description,
//                              @RequestParam("edit-task-assignee") String assignee) {
//
//        try (Session session = sessionFactory.openSession()) {
//            Transaction tx = session.beginTransaction();
//
//            Task task = new Task();
//            task.setTitle(title);
//            task.setPriority(priority);
//            task.setDescription(description);
//            task.setAssignee(assignee);
//
//            session.update(task);
//
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "redirect:/confirmationPage";
//    }
//
//    @GetMapping("/confirmationPage")
//    public String showConfirmationPage() {
//        return "confirmationPage";
//    }
//}