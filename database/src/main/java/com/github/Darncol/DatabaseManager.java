package com.github.Darncol;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class DatabaseManager {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void saveEntity(Object entity) throws DataBaseException {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            System.out.println("Entity saved");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            throw new DataBaseException("Error while saving entity");
        }
    }

    public static List<Match> getMatchesWithPlayer(String name, String page) {
        int pageNumber = (page == null || page.isEmpty()) ? 0 : Integer.parseInt(page) - 1;
        int pageSize = 5;

        try (var session = sessionFactory.openSession()) {
            String hql = "FROM Match m WHERE m.player1.name = :name OR m.player2.name = :name ORDER BY m.id ASC";
            return session.createQuery(hql, Match.class)
                    .setParameter("name", name)
                    .setFirstResult(pageNumber * pageSize)
                    .setMaxResults(pageSize)
                    .list();
        } catch (HibernateException e) {
            System.out.println("Error fetching matches: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<Match> getMathesInPage(String page) {
        int pageNumber = (page == null || page.isEmpty()) ? 0 : Integer.parseInt(page) - 1;
        int pageSize = 5;

        try (var session = sessionFactory.openSession()) {
            String hql = "FROM Match m ORDER BY m.id ASC";
            return session.createQuery(hql, Match.class)
                    .setFirstResult(pageNumber * pageSize)
                    .setMaxResults(pageSize)
                    .list();
        } catch (HibernateException e) {
            System.out.println("Error fetching matches: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static Player getPlayerByName(String name) throws DataBaseException{
        try (var session = sessionFactory.openSession()) {
            String hql = "FROM Player p WHERE p.name = :name";
            return session.createQuery(hql, Player.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            throw new DataBaseException("Error while getting player by name");
        }
    }

    private DatabaseManager() {
    }
}