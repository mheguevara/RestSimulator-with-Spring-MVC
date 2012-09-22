package org.mheguevara.restsimulator.controllers;

import org.apache.log4j.Logger;
import org.mheguevara.restsimulator.dao.StudentDao;
import org.mheguevara.restsimulator.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

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

    public StudentDao getStudentDao() {
        return studentDao;
    }


    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get(){

        logger.info("------------------------------------------");
        logger.info("GET /students");

        ModelAndView modelAndView = new ModelAndView("students");

        modelAndView.addObject("students",studentDao.getAllStudents());
        logger.info("------------------------------------------");

        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long id, HttpServletResponse response){
        logger.info("------------------------------------------");
        logger.info("GET /students/"+id);

        Student student = studentDao.getStudentById(id);
        ModelAndView modelAndView = new ModelAndView("student");
        if(student == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else{
            modelAndView.addObject("student",student);
        }


        logger.info("------------------------------------------");


        return modelAndView;

    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody Student student, HttpServletResponse response){
        logger.info("--------------------------------------------");
        logger.info("POST /students");
        logger.debug(student);
        logger.info("--------------------------------------------");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", "/students/" + 3);
    }
}
