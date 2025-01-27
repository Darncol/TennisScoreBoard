package com.github.Darncol;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.util.List;

public class DatabaseManager {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void saveEntity(Object entity) throws IllegalArgumentException {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            System.out.println("Entity saved");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Error while saving entity");
        }
    }

    public static <T> T getEntity(Class<T> entityClass, long id) throws IllegalArgumentException {
        try (var session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Error while getting entity");
        }
    }

    public static Player getPlayerByName(String name) throws IllegalArgumentException {
        try (var session = sessionFactory.openSession()) {
            String hql = "FROM Player p WHERE p.name = :name";
            return session.createQuery(hql, Player.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("No player with name " + name);
        }
    }

    public static <T> List<T> getEntities(Class<T> entityClass) throws IllegalArgumentException {
        try (var session = sessionFactory.openSession()) {
            String hql = "FROM " + entityClass.getSimpleName();
            return session.createQuery(hql, entityClass).list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Error while getting entities");
        }
    }

    private DatabaseManager() {
    }
}