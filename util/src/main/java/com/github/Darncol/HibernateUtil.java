package com.github.Darncol;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Player.class);
            configuration.addAnnotatedClass(Match.class);

            sessionFactory = configuration.buildSessionFactory(
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build()
            );
        } catch (HibernateException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

