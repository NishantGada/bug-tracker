package com.project.bugtracker.DAO;

import com.project.bugtracker.pojo.Bug;
import com.project.bugtracker.pojo.Employee;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManagerFactory;

import java.util.Collections;
import java.util.List;

@Repository
public class EmployeeDAO {
    public void saveEmployee(Employee employee) {
        System.out.println("Employee DAO Reached");
        Session session = DAO.getSessionFactory().openSession();
        System.out.println("saveEmployee session: " + session);
        Transaction transaction = null;
        try {
            System.out.println("saveEmployee inside try block");
            transaction = session.beginTransaction();
            session.persist(employee);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("saveEmployee inside catch block");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            System.out.println("saveEmployee inside finally block");
            session.close();
        }
    }

    public List<Employee> getAllEmployees() {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "FROM Employee";
            return session.createQuery(hql, Employee.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getEmployeeCount() {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(e) FROM Employee e";
            Query query = session.createQuery(hql);
            return ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean alreadyExists(String email) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "FROM Employee e WHERE e.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            List<Employee> result = query.getResultList();
            return !result.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValidEmployee(String email, String password) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "FROM Employee e WHERE e.email = :email AND e.password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            query.setParameter("password", password);
            List<Employee> result = query.getResultList();
            return !result.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee getEmployeeByEmail(String email) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "FROM Employee e WHERE e.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            List<Employee> result = query.getResultList();
            if (!result.isEmpty()) {
                return result.get(0); // Assuming email is unique, so there should be only one result
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeById(int id) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllEmployeeNames() {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "SELECT e.firstName FROM Employee e";
            return session.createQuery(hql, String.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void updateEmployee(Employee employee) {
        System.out.println("BugDAO updateBug called...");
        Session session = null;
        Transaction transaction = null;
        try {
            session = DAO.getSessionFactory().openSession();
            transaction = session.beginTransaction();
             session.update(employee);
            System.out.println("updateEmployee employee: " + employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Bug> getEmployeeBugs(int employeeId) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, employeeId);
            if (employee != null) {
                return employee.getBugsList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void deleteEmployee(int empId) {
        System.out.println("deleteEmployee empId: " + empId);
        Session session = DAO.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, empId);
            System.out.println("deleteEmployee delete emp: " + employee);
            if (employee != null) {
                session.delete(employee);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}