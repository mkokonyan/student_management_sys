package src.app.controllers;

import configs.CoursesServiceSingleton;
import entities.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.interfaces.CoursesService;

import java.nio.channels.AlreadyBoundException;
import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseRestController {
    private final CoursesService coursesService = CoursesServiceSingleton.getInstance();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex() {
        return "Hello world";
    }

//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<Course>> getAllCourses() {
//        return ResponseEntity.ok(coursesService.getAllCourses());
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity createCourse(@RequestBody String courseData) {
//        try {
//            this.coursesService.addNewCourse(courseData);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        } catch (AlreadyBoundException ex) {
//            ex.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
