package org.mheguevara.restsimulator.controllers;

import org.apache.log4j.Logger;
import org.mheguevara.restsimulator.core.Result;
import org.mheguevara.restsimulator.dao.CourseDao;
import org.mheguevara.restsimulator.models.Course;
import org.mheguevara.restsimulator.models.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/23/12
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/courses")
public class CourseController {

    private  static final Logger logger = Logger.getLogger(CourseController.class);

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private Result notFoundMessage;

    @RequestMapping(method = RequestMethod.GET,produces = "application/json")
    public ModelAndView getJson(HttpServletResponse response){

        logger.info("------------------------------------------");
        logger.info("GET /courses");
        ModelAndView modelAndView = new ModelAndView("courses");
        List<Course> courses = null;
        try {
            courses = courseDao.getAllCourses();
        } catch (SQLException e) {
            logger.warn(e,e);
        }

        if(courses.size() == 0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject(notFoundMessage);
        } else {
            modelAndView.addObject(courses);
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET,produces = "application/xml")
    public ModelAndView getXml(HttpServletResponse response){

        logger.info("------------------------------------------");
        logger.info("GET /courses");
        ModelAndView modelAndView = new ModelAndView("courses");
        List<Course> courses = null;
        try {
            courses = courseDao.getAllCourses();
        } catch (SQLException e) {
            logger.warn(e, e);
        }

        if(courses.size() == 0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject(notFoundMessage);
        } else {
            modelAndView.addObject(new Courses(courses));
        }
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json","application/xml","text/xml"})
    public void post(@RequestBody Course course, HttpServletResponse response){
        logger.info("--------------------------------------------");
        logger.info("POST /courses");
        logger.debug(course);
        long id = 0;
        try {
            id = courseDao.saveCourse(course);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            logger.warn(e, e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        logger.info("--------------------------------------------");
        response.setHeader("Location", "/courses/" + id);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long courseId, HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("GET /courses/"+ courseId);
        Course course = null;
        try {
            course = courseDao.getCourseById(courseId);
        } catch (SQLException e) {
            logger.warn(e,e);
        }
        ModelAndView modelAndView = new ModelAndView("course");
        if(course == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelAndView.addObject(notFoundMessage);
        } else{
            modelAndView.addObject("course",course);
        }
        logger.info("------------------------------------------");
        return modelAndView;

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json","application/xml","text/xml"})
    public void put(@PathVariable("id") long courseId, @RequestBody Course course,HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("PUT /courses/"+ courseId);
        logger.info(course);
        courseDao.updateCourse(courseId, course);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        logger.info("------------------------------------------");
    }
}
