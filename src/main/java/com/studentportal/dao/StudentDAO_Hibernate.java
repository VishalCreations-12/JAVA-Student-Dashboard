package com.studentportal.dao;

import com.studentportal.entity.Student;
import com.studentportal.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Hibernate-based StudentDAO
 * To use this instead of MockDataStore, rename this file to StudentDAO.java
 * and replace the MockDataStore version
 */
public class StudentDAO_Hibernate {
    
    public Long save(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Long studentId = null;
        
        try {
            transaction = session.beginTransaction();
            studentId = (Long) session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return studentId;
    }
    
    public void update(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public void delete(Long studentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.delete(student);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public Student findById(Long studentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student student = null;
        
        try {
            student = session.get(Student.class, studentId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return student;
    }
    
    public Student findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student student = null;
        
        try {
            Query<Student> query = session.createQuery("FROM Student WHERE email = :email", Student.class);
            query.setParameter("email", email);
            student = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return student;
    }
    
    @SuppressWarnings("unchecked")
    public List<Student> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = null;
        
        try {
            students = session.createQuery("FROM Student ORDER BY registrationDate DESC").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return students;
    }
}

