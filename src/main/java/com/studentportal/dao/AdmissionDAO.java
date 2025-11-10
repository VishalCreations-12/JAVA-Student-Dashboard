package com.studentportal.dao;

import com.studentportal.entity.Admission;
import com.studentportal.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdmissionDAO {
    
    public Long save(Admission admission) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Long admissionId = null;
        
        try {
            transaction = session.beginTransaction();
            admissionId = (Long) session.save(admission);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return admissionId;
    }
    
    public void update(Admission admission) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            session.update(admission);
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
    
    public void delete(Long admissionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            Admission admission = session.get(Admission.class, admissionId);
            if (admission != null) {
                session.delete(admission);
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
    
    public Admission findById(Long admissionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Admission admission = null;
        
        try {
            admission = session.get(Admission.class, admissionId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return admission;
    }
    
    @SuppressWarnings("unchecked")
    public List<Admission> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Admission> admissions = null;
        
        try {
            admissions = session.createQuery("FROM Admission ORDER BY admissionDate DESC").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return admissions;
    }
    
    @SuppressWarnings("unchecked")
    public List<Admission> findByStudentId(Long studentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Admission> admissions = null;
        
        try {
            Query<Admission> query = session.createQuery("FROM Admission WHERE student.studentId = :studentId", Admission.class);
            query.setParameter("studentId", studentId);
            admissions = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return admissions;
    }
}

