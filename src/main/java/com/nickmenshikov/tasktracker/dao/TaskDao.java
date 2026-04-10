package com.nickmenshikov.tasktracker.dao;

import com.nickmenshikov.tasktracker.model.Priority;
import com.nickmenshikov.tasktracker.model.Status;
import com.nickmenshikov.tasktracker.model.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskDao extends BaseDao<Task>{

    private final SessionFactory sessionFactory;

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public List<Task> findAll(Long creatorId, int page, int size, Status status, Priority priority) {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Task> cq = cb.createQuery(Task.class);
            Root<Task> root = cq.from(Task.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("creatorId"), creatorId));

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }

            cq.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(cq)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .list();
        }
    }

    public Optional<Task> getById(Long id, Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Task where id = :id and creatorId = :userId", Task.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .uniqueResultOptional();
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Task task = session.find(Task.class, id);
            session.remove(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
