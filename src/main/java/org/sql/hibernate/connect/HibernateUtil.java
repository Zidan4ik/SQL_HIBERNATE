package org.sql.hibernate.connect;



import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import org.apache.logging.log4j.LogManager;

public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeConnection() {
        getSessionFactory().close();
    }
    public static void showList(List list){
        for (Object object:list){
            System.out.println(object);
        }
    };
    public void logMessage(String message){
        Logger logger = LogManager.getLogger(this.getClass());
        logger.info(message);
    }
}
