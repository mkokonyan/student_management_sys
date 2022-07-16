package configs;

import services.CoursesServiceImpl;
import services.interfaces.CoursesService;

public class CoursesServiceSingleton {
    private static CoursesService coursesService = null;

    private CoursesServiceSingleton() {
    }

    public static CoursesService getInstance() {
        if (CoursesServiceSingleton.coursesService == null) {

            CoursesServiceSingleton.coursesService = new CoursesServiceImpl();
        }
        return CoursesServiceSingleton.coursesService;
    }
}
