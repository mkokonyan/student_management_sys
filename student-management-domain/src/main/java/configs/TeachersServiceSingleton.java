package configs;

import services.TeachersServiceImpl;
import services.interfaces.TeachersService;

public class TeachersServiceSingleton {
    private static TeachersService teachersService = null;

    private TeachersServiceSingleton() {
    }

    public static TeachersService getInstance() {
        if (TeachersServiceSingleton.teachersService == null) {

            TeachersServiceSingleton.teachersService = new TeachersServiceImpl();
        }
        return TeachersServiceSingleton.teachersService;
    }
}
