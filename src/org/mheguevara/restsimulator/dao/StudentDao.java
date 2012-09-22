package org.mheguevara.restsimulator.dao;

import org.apache.log4j.Logger;
import org.mheguevara.restsimulator.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/22/12
 * Time: 12:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudentDao {

    private static final Logger logger = Logger.getLogger(StudentDao.class);

    public List<Student> getAllStudents(){

        logger.info("All students are being retrieved!");

        Student student = new Student();
        student.setId(1);
        student.setName("Muhammet");
        student.setSurname("Alay");
        student.setEmail("muhammetalay61@gmail.com");
        student.setSchoolNumber(1);

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("Serhat");
        student2.setSurname("Alay");
        student2.setEmail("serhatalay@gmail.com");
        student2.setSchoolNumber(2);

        List<Student> list = new ArrayList<Student>();
        list.add(student);
        list.add(student2);

        logger.info("All students are being returned!");

        return list;
    }

    public Student getStudentById(long id){

        Student student = new Student();
        student.setId(1);
        student.setName("Muhammet");
        student.setSurname("Alay");
        student.setEmail("muhammetalay61@gmail.com");
        student.setSchoolNumber(1);

        return id == 1 ? student : null;
    }

}
