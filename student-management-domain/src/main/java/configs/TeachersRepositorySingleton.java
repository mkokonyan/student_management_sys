package configs;

import entities.Teacher;
import repositories.TeachersRepository;

public class TeachersRepositorySingleton {
    private static TeachersRepository teachersRepository = null;


    private TeachersRepositorySingleton() {
    }

    public static TeachersRepository getInstance() {
        if (TeachersRepositorySingleton.teachersRepository == null) {

            TeachersRepositorySingleton.teachersRepository = new TeachersRepository(EntityManagerSingleton.getInstance(), Teacher.class);
        }
        return TeachersRepositorySingleton.teachersRepository;
    }
}
