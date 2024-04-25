/*
package com.project.bugtracker.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DAO {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
 */

package com.project.bugtracker.DAO;

import com.project.bugtracker.pojo.Bug;
import com.project.bugtracker.pojo.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

public class DAO {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .applySetting(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
                        .applySetting(Environment.URL, "jdbc:mysql://localhost:3306/semester2")
                        .applySetting(Environment.USER, "root")
                        .applySetting(Environment.PASS, "Alphagamma@9999")
                        .applySetting(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect")
                        .applySetting(Environment.SHOW_SQL, "true")
                        .applySetting(Environment.HBM2DDL_AUTO, "update")
                        .build();
                MetadataSources metadataSources = new MetadataSources(registry);
                metadataSources.addAnnotatedClass(Employee.class);
                metadataSources.addAnnotatedClass(Bug.class);

                Metadata metadata = metadataSources.getMetadataBuilder().build();
                System.out.println("DAO metadata: " + metadata);

                sessionFactory = metadata.getSessionFactoryBuilder().build();
                System.out.println("DAO sessionFactory: " + sessionFactory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}