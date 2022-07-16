package configs;

import entities.Student;
import repositories.StudentsRepository;

public class StudentsRepositorySingleton {
    private static StudentsRepository studentsRepository = null;


    private StudentsRepositorySingleton() {
    }

    public static StudentsRepository getInstance() {
        if (StudentsRepositorySingleton.studentsRepository == null) {

            StudentsRepositorySingleton.studentsRepository = new StudentsRepository(EntityManagerSingleton.getInstance(), Student.class);
        }
        return StudentsRepositorySingleton.studentsRepository;
    }
}
