package services.interfaces;

import entities.Teacher;

import java.util.List;

public interface TeachersService {

    boolean addNewTeacher(String teacherData);

    boolean setTeacherToCourse(String teacherID, String courseID);

    List<Teacher> getAllTeachers();

    String getTeacherID(Teacher teacher);

    List<String> getTeacherDegreesTypes();

}
