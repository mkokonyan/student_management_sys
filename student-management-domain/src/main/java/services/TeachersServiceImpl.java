package services;

import entities.Course;
import entities.Teacher;
import enums.TeacherDegree;
import repositories.CoursesRepository;
import repositories.TeachersRepository;
import services.interfaces.Closeable;
import services.interfaces.TeachersService;
import services.validators.Validators;
import configs.CoursesRepositorySingleton;
import configs.TeachersRepositorySingleton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TeachersServiceImpl implements TeachersService, Closeable {
    private final CoursesRepository coursesRepository;
    private final TeachersRepository teachersRepository;


    public TeachersServiceImpl() {
        this.coursesRepository = CoursesRepositorySingleton.getInstance();
        this.teachersRepository = TeachersRepositorySingleton.getInstance();
    }

    public TeachersServiceImpl(CoursesRepository coursesRepository, TeachersRepository teachersRepository) {
        this.coursesRepository = coursesRepository;
        this.teachersRepository = teachersRepository;
    }

    @Override
    public boolean addNewTeacher(String teacherData) {
        String teacherName = teacherData.split(System.lineSeparator())[0].strip();
        String teacherDegreeOptionAsStr = teacherData.split(System.lineSeparator())[1].strip();

        try {
            Validators.validateName(teacherName);

            Teacher newTeacher = this.teachersRepository.createNewTeacher(teacherName, teacherDegreeOptionAsStr);
            this.teachersRepository.add(newTeacher);

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean setTeacherToCourse(String teacherID, String courseID)  {
        Teacher teacherToUpdate = this.teachersRepository.findByID(teacherID);
        Course courseToUpdate = this.coursesRepository.findByID(courseID);

        String teacherName = this.teachersRepository.getEntityName(teacherToUpdate);

        try {

            Validators.validateCourseHasNoTeacher(this.coursesRepository, courseToUpdate);

        } catch (IllegalArgumentException ex) {

            System.out.println(ex.getMessage());
            return false;

        }

        this.coursesRepository.setCourseTeacher(teacherName, courseToUpdate);

        return true;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return this.teachersRepository.findAll();
    }

    @Override
    public String getTeacherID(Teacher teacher) {
        return this.teachersRepository.getEntityID(teacher);
    }

    @Override
    public List<String> getTeacherDegreesTypes() {
        return Arrays.stream(TeacherDegree.values())
                .map(TeacherDegree::getTeacherDegree)
                .collect(Collectors.toList());
    }
}
