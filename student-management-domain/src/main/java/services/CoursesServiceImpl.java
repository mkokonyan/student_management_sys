package services;

import entities.Course;
import repositories.CoursesRepository;
import services.interfaces.Closeable;
import services.interfaces.CoursesService;
import services.validators.Validators;
import configs.CoursesRepositorySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoursesServiceImpl implements CoursesService, Closeable{
    private final CoursesRepository coursesRepository;

    public CoursesServiceImpl() {
        this.coursesRepository = CoursesRepositorySingleton.getInstance();
    }

    public CoursesServiceImpl(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @Override
    public boolean addNewCourse(String courseData) {
        String courseName = courseData.split(System.lineSeparator())[0].strip();
        String courseHoursAsStr = courseData.split(System.lineSeparator())[1].strip();

        try {
            int courseHours = Validators.validateNumber(courseHoursAsStr);

            Validators.validateCourseExistence(this.coursesRepository, courseName);

            Course newCourse = this.coursesRepository.createNewCourse(courseName, courseHours);
            this.coursesRepository.add(newCourse);

        } catch (IllegalArgumentException ex) {

            System.out.println(ex.getMessage());
            return false;

        }

        return true;
    }

    @Override
    public Map<Course, List<String>> showAllEntities() {

        Map<Course, List<String>> allEntityData = new HashMap<>();

        for (Course course : getAllCourses()) {
            allEntityData.putIfAbsent(course, new ArrayList<>());

            String courseName = this.coursesRepository.getEntityName(course);
            String teacherName = this.coursesRepository.getCourseTeacher(course);

            String courseStudents = String.join(", ", this.coursesRepository.getCourseStudents(course));

            allEntityData.get(course).add(courseName);
            allEntityData.get(course).add(teacherName);
            allEntityData.get(course).add(courseStudents);

        }

        return allEntityData;
    }

    @Override
    public List<Course> getAllCourses() {
        return this.coursesRepository.findAll();
    }

    @Override
    public String getCourseID(Course course) {
        return this.coursesRepository.getEntityID(course);
    }

    @Override
    public Course findCourseByName(String courseName) {
        return this.coursesRepository.findByName(courseName);
    }

}
