package configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class EntityManagerSingleton {
    private static EntityManager entityManager = null;
    private static final String persistenceUnitName = "StudentManagement_JPA";

    private EntityManagerSingleton() {}

    public static EntityManager getInstance() {
        if (EntityManagerSingleton.entityManager == null) {

            EntityManagerSingleton.entityManager = Persistence.createEntityManagerFactory(EntityManagerSingleton.persistenceUnitName)
                    .createEntityManager();
        }
        return EntityManagerSingleton.entityManager;
    }
}
