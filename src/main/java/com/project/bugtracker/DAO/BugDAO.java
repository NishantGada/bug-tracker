package com.project.bugtracker.DAO;

import com.project.bugtracker.pojo.Bug;
import com.project.bugtracker.pojo.Employee;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BugDAO {
    public void saveBug(Bug bug) {
        System.out.println("Bug DAO Reached");
        System.out.println("saveBug bug: " + bug);

        Session session = DAO.getSessionFactory().openSession();
        System.out.println("saveBug session: " + session);
        Transaction transaction = null;
        try {
            System.out.println("saveBug inside try block");
            transaction = session.beginTransaction();
            session.persist(bug);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("saveBug inside catch block");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            System.out.println("saveBug inside finally block");
            session.close();
        }
    }

    public List<Bug> getAllBugs() {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "FROM Bug";
            return session.createQuery(hql, Bug.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getBugCount() {
        try (Session session = DAO.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(b) FROM Bug b";
            Query query = session.createQuery(hql);
            return ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Bug> getBugsByEmployeeId(int employeeId) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, employeeId);
            if (employee != null) {
                return employee.getBugs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBug(int bugId) {
        Session session = DAO.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Bug bug = session.get(Bug.class, bugId);
            if (bug != null) {
                session.delete(bug);
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
