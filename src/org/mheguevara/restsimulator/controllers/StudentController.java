package org.mheguevara.restsimulator.controllers;

import org.apache.log4j.Logger;
import org.mheguevara.restsimulator.core.Result;
import org.mheguevara.restsimulator.dao.StudentDao;
import org.mheguevara.restsimulator.models.Course;
import org.mheguevara.restsimulator.models.Courses;
import org.mheguevara.restsimulator.models.Student;
import org.mheguevara.restsimulator.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/21/12
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/students")
public class StudentController {

    private static final Logger logger = Logger.getLogger(StudentController.class);


    @Autowired
    private StudentDao studentDao;

    @Autowired
    private Result notFoundMessage;

    @Autowired
    private Result noSuchCourseMessage;

    @Autowired
    private Result okMessage;

    public StudentDao getStudentDao() {
        return studentDao;
    }


    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Result getNoSuchCourseMessage() {
        return noSuchCourseMessage;
    }

    public void setNoSuchCourseMessage(Result noSuchCourseMessage) {
        this.noSuchCourseMessage = noSuchCourseMessage;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getJson(HttpServletResponse response){

        logger.info("------------------------------------------");
        logger.info("GET /students");
        ModelAndView modelAndView = new ModelAndView("students");
        List<Student> students = studentDao.getAllStudents();
        if(students.size() == 0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject(notFoundMessage);
        } else{
            modelAndView.addObject("students",students);
        }
        logger.info("------------------------------------------");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/xml")
    public ModelAndView getXml(HttpServletResponse response){

        logger.info("------------------------------------------");
        logger.info("GET /students");
        ModelAndView modelAndView = new ModelAndView("students");
        List<Student> students = studentDao.getAllStudents();
        if(students.size() == 0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject(notFoundMessage);
        }else{
            modelAndView.addObject("students",new Students(students));
        }
        logger.info("------------------------------------------");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json","application/xml","text/xml"})
    public void postJsonXml(@RequestBody Student student, HttpServletResponse response){
        logger.info("--------------------------------------------");
        logger.info("POST /students");
        logger.debug(student);
        long id = studentDao.saveStudent(student);
        logger.info("--------------------------------------------");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", "/students/" + id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long studentId, HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("GET /students/"+ studentId);
        Student student = studentDao.getStudentById(studentId);
        ModelAndView modelAndView = new ModelAndView("student");
        if(student == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject(notFoundMessage);
        } else{
            modelAndView.addObject("student",student);
        }
        logger.info("------------------------------------------");
        return modelAndView;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json","application/xml","text/xml"})
    public void put(@PathVariable("id") long studentId, @RequestBody Student student,HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("PUT /students/"+ studentId);
        logger.info(student);
        studentDao.updateStudent(studentId, student);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        logger.info("------------------------------------------");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long studentId,HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("DELETE /students/" + studentId);
        studentDao.deleteStudent(studentId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        logger.info("------------------------------------------");
    }

    @RequestMapping(value = "/{id}/courses", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getCoursesOfStudentJson(@PathVariable("id") long studentId, HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("GET /students/"+ studentId +"/courses");
        ModelAndView modelAndView = new ModelAndView("courses");
        List<Course> courses = studentDao.getCoursesOfStudent(studentId);
        if(courses.size() == 0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject(notFoundMessage);
        } else{
            modelAndView.addObject("courses", courses);
        }
        logger.info("------------------------------------------");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/courses", method = RequestMethod.GET, produces = "application/xml")
    public ModelAndView getCoursesOfStudentXml(@PathVariable("id") long studentId, HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("GET /students/"+ studentId +"/courses");
        ModelAndView modelAndView = new ModelAndView("courses");
        List<Course> courses = studentDao.getCoursesOfStudent(studentId);
        if(courses.size() == 0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject(notFoundMessage);
        } else{
            modelAndView.addObject("courses",new Courses(courses));
        }
        logger.info("------------------------------------------");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/courses", method = RequestMethod.POST, consumes = {"application/xml","application/json"})
    public ModelAndView post(@PathVariable("id") long studentId, @RequestBody Course course, HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("POST /students/" + studentId + "/courses");
        logger.debug(course);
        ModelAndView modelAndView = new ModelAndView();
        boolean result = studentDao.saveCourseForStudent(studentId,course);
        if(result){
            response.setStatus(HttpServletResponse.SC_OK);
            modelAndView.addObject(okMessage);
        } else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            modelAndView.addObject(noSuchCourseMessage);
        }
        logger.info("------------------------------------------");
        return modelAndView;
    }
}
