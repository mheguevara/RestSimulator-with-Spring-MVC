package org.mheguevara.restsimulator.dao;

import org.apache.log4j.Logger;
import org.mheguevara.restsimulator.models.Course;
import org.mheguevara.restsimulator.models.Student;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Autowired
    private DbUtil dbUtil;

    public List<Student> getAllStudents() throws SQLException {

        logger.info("All students are being retrieved!");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Student> list = new ArrayList<Student>();

        try {
            connection = dbUtil.getConnection();
            preparedStatement = connection.prepareStatement("select id,name,surname,email,schoolNumber from student order by surname,name");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surname"));
                student.setEmail(resultSet.getString("email"));
                student.setSchoolNumber(resultSet.getInt("schoolNumber"));
                list.add(student);
            }

        } catch (SQLException e) {
            logger.warn(e,e);
            throw e;
        }
        finally {
            dbUtil.close(resultSet);
            dbUtil.close(preparedStatement);
            dbUtil.close(connection);
        }


        return list;
    }

    public Student getStudentById(long id) throws SQLException {

        logger.info("Student with id "+ id + " is being retrieved!");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Student student = null;

        try {
            connection = dbUtil.getConnection();
            preparedStatement = connection.prepareStatement("select id,name,surname,email,schoolNumber from student where id = ?");
            preparedStatement.setLong(1,id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surname"));
                student.setEmail(resultSet.getString("email"));
                student.setSchoolNumber(resultSet.getInt("schoolNumber"));
            }

        } catch (SQLException e) {
            logger.warn(e,e);
            throw e;
        }
        finally {
            dbUtil.close(resultSet);
            dbUtil.close(preparedStatement);
            dbUtil.close(connection);
        }


        return student;
    }

    public long saveStudent(Student student) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        long result = 0;

        try {
            connection = dbUtil.getConnection(false);
            preparedStatement = connection.prepareStatement("insert into student(name,surname,email,schoolNumber) values (?,?,?,?)");
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getSurname());
            preparedStatement.setString(3,student.getEmail());
            preparedStatement.setInt(4,student.getSchoolNumber());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("select last_insert_id() as id");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result = resultSet.getLong(1);
            }

            connection.commit();

        } catch (SQLException e) {
            logger.warn(e,e);
            dbUtil.rollback(connection);
            throw e;
        }
        finally {
            dbUtil.close(resultSet);
            dbUtil.close(preparedStatement);
            dbUtil.close(connection);
        }
        return result;

    }

    public void updateStudent(long id, Student student){

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbUtil.getConnection(false);
            preparedStatement = connection.prepareStatement("update student set name = ?, surname = ?, email = ?, schoolNumber = ? where id = ?");
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getSurname());
            preparedStatement.setString(3,student.getEmail());
            preparedStatement.setInt(4,student.getSchoolNumber());
            preparedStatement.setLong(5,id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.warn(e,e);
            dbUtil.rollback(connection);
        } finally {
            dbUtil.close(preparedStatement);
            dbUtil.close(connection);
        }


    }

    public void deleteStudent(long id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbUtil.getConnection(false);
            preparedStatement = connection.prepareStatement("delete from student where id = ?");
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.warn(e,e);
            dbUtil.rollback(connection);
        } finally {
            dbUtil.close(preparedStatement);
            dbUtil.close(connection);
        }

    }

    public List<Course> getCoursesOfStudent(long id){

        List<Course> courses = new ArrayList<Course>();
        Course course = new Course();
        course.setId(1);
        course.setName("Introduction to Algorithms-I");
        course.setCode("CS-101");

        Course course2 = new Course();
        course2.setId(1);
        course2.setName("Introduction to Algorithms-II");
        course2.setCode("CS-102");

        courses.add(course);
        courses.add(course2);

        return id == 1 ? courses : new ArrayList<Course>();

    }

    public boolean saveCourseForStudent(long id, Course course){
        return id == 1 ? true:false;
    }

}
