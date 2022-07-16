package configs;

import entities.Course;
import repositories.CoursesRepository;

public class CoursesRepositorySingleton {
    private static CoursesRepository coursesRepository = null;

    private CoursesRepositorySingleton() {
    }

    public static CoursesRepository getInstance() {
        if (CoursesRepositorySingleton.coursesRepository == null) {

            CoursesRepositorySingleton.coursesRepository = new CoursesRepository(EntityManagerSingleton.getInstance(), Course.class);
        }
        return CoursesRepositorySingleton.coursesRepository;
    }
}
