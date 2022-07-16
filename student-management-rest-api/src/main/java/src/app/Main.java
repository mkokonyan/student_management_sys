package src.app;

import configs.CoursesRepositorySingleton;
import configs.EntityManagerSingleton;
//import jakarta.persistence.EntityManager;
import repositories.CoursesRepository;
import services.CoursesServiceImpl;
import services.interfaces.CoursesService;


public class Main {
    public static void main(String[] args) {
//        EntityManager entityManager = EntityManagerSingleton.getInstance();
//        CoursesRepository coursesRepository =  CoursesRepositorySingleton.getInstance();
//        CoursesService coursesService = new CoursesServiceImpl(coursesRepository);

        new CoursesServiceImpl().getAllCourses().forEach(System.out::println);
    }
}
