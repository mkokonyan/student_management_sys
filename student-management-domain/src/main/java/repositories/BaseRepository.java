package repositories;

import entities.interfaces.Identifiable;
import entities.interfaces.Nameable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public abstract class BaseRepository<T extends Nameable & Identifiable> {
    private final EntityManager entityManager;
    private final Class<T> clazzRepo;

    public BaseRepository(EntityManager entityManager, Class<T> domainClass) {
        this.entityManager = entityManager;
        this.clazzRepo = domainClass;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public Class<T> getClazzRepo() {
        return this.clazzRepo;
    }

    public T add(T entity) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        this.entityManager.persist(entity);

        transaction.commit();

        return entity;
    }

    public T findByID(String id) {
        TypedQuery<T> typedQuery = queryByID(id);
        return typedQuery.getSingleResult();
    }

    public T findByName(String name) {
        return this.entityManager.createQuery("SELECT t FROM " + clazzRepo.getName() + " t WHERE t.name = :name", this.clazzRepo)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<T> findAll() {
        return this.entityManager.createQuery("SELECT t FROM " + clazzRepo.getName() + " t", this.clazzRepo)
                .getResultList();
    }

    public String getEntityID(T entity) {
        return this.getEntityManager().createQuery("SELECT t.id FROM " + clazzRepo.getName() + " t WHERE t = :entity", String.class)
                .setParameter("entity", entity).getSingleResult();
    }

    public String getEntityName(T entity) {
        return this.getEntityManager().createQuery("SELECT t.name FROM " + clazzRepo.getName() + " t WHERE t = :entity", String.class)
                .setParameter("entity", entity).getSingleResult();
    }

    public T deleteByID(String id) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();

        T entityToDelete = findByID(id);
        this.entityManager.remove(entityToDelete);

        flushAndClear();

        transaction.commit();

        return entityToDelete;

    }

    public boolean exists(String id) {
        return queryByID(id)
                .getResultList()
                .size() == 1;
    };

    private TypedQuery<T> queryByID(String id) {
        return this.entityManager.createQuery("SELECT t FROM " + clazzRepo.getName() + " t WHERE t.id = :id", this.clazzRepo)
                .setParameter("id", id);
    }

    private void flushAndClear() {
        this.entityManager.flush();
        this.entityManager.clear();
    }
}
