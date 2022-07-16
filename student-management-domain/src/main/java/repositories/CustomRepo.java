package repositories;

import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
//import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class CustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Course> getAllCourses() {
        return this.entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }
}
