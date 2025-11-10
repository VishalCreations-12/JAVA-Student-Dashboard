package com.studentportal.dao;

import com.studentportal.entity.Course;
import com.studentportal.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CourseDAO {
    
    public Long save(Course course) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Long courseId = null;
        
        try {
            transaction = session.beginTransaction();
            courseId = (Long) session.save(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return courseId;
    }
    
    public Course findById(Long courseId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course course = null;
        
        try {
            course = session.get(Course.class, courseId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return course;
    }
    
    @SuppressWarnings("unchecked")
    public List<Course> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Course> courses = null;
        
        try {
            courses = session.createQuery("FROM Course ORDER BY courseName").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return courses;
    }
}

