package ma.projet.util;

import ma.projet.classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.InputStream;
import java.util.Properties;

public final class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties props = new Properties();
                try (InputStream in = HibernateUtil.class.getClassLoader()
                        .getResourceAsStream("application.properties")) {
                    if (in == null) throw new IllegalStateException("application.properties introuvable");
                    props.load(in);
                }

                Properties settings = new Properties();
                settings.put("hibernate.connection.url", props.getProperty("db.url"));
                settings.put("hibernate.connection.username", props.getProperty("db.user"));
                settings.put("hibernate.connection.password", props.getProperty("db.password"));
                settings.put("hibernate.hbm2ddl.auto", props.getProperty("hibernate.hbm2ddl.auto"));
                settings.put("hibernate.show_sql", props.getProperty("hibernate.show_sql"));
                settings.put("hibernate.format_sql", props.getProperty("hibernate.format_sql"));
                settings.put("hibernate.dialect", props.getProperty("hibernate.dialect"));
                settings.put("hibernate.current_session_context_class", props.getProperty("hibernate.current_session_context_class"));

                StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build();

                MetadataSources sources = new MetadataSources(registry)
                        .addAnnotatedClass(Categorie.class)
                        .addAnnotatedClass(Produit.class)
                        .addAnnotatedClass(Commande.class)
                        .addAnnotatedClass(LigneCommandeProduit.class)
                        .addAnnotatedClass(LigneCommandeProduitId.class);

                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                throw new RuntimeException("Erreur init SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) sessionFactory.close();
        sessionFactory = null;
    }
}
