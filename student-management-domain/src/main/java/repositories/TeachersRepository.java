package repositories;

import entities.Teacher;
import jakarta.persistence.EntityManager;

public class TeachersRepository extends BaseRepository<Teacher> {
    public TeachersRepository(EntityManager entityManager, Class<Teacher> domainClass) {
        super(entityManager, domainClass);
    }

    public Teacher createNewTeacher(String name, String degree) {
        return new Teacher(name, degree);
    }
}
