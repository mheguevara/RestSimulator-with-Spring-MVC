package org.mheguevara.restsimulator.dao;

import org.apache.log4j.Logger;
import org.mheguevara.restsimulator.models.Course;
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
 * Date: 9/23/12
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseDao {

    private static final Logger logger = Logger.getLogger(CourseDao.class);

    @Autowired
    private DbUtil dbUtil;

    public List<Course> getAllCourses() throws SQLException {
        logger.info("All courses are being retrieved!");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Course> list = new ArrayList<Course>();

        try {
            connection = dbUtil.getConnection();
            preparedStatement = connection.prepareStatement("select id,name,code from course order by name");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Course course = new Course();
                course.setId(resultSet.getLong("id"));
                course.setName(resultSet.getString("name"));
                course.setCode(resultSet.getString("code"));
                list.add(course);
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

    public long saveCourse(Course course) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        long result = 0;

        try {
            connection = dbUtil.getConnection(false);
            preparedStatement = connection.prepareStatement("insert into course(name,code) values (?,?)");
            preparedStatement.setString(1,course.getName());
            preparedStatement.setString(2, course.getCode());
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

    public Course getCourseById(long courseId) throws SQLException {

        logger.info("Course with id "+ courseId + " is being retrieved!");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Course course = null;

        try {
            connection = dbUtil.getConnection();
            preparedStatement = connection.prepareStatement("select id,name,code from course where id = ?");
            preparedStatement.setLong(1,courseId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                course = new Course();
                course.setId(resultSet.getLong("id"));
                course.setName(resultSet.getString("name"));
                course.setCode(resultSet.getString("code"));
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


        return course;
    }

    public void updateCourse(long courseId, Course course) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbUtil.getConnection(false);
            preparedStatement = connection.prepareStatement("update course set name = ?, code = ? where id = ?");
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2,course.getCode());
            preparedStatement.setLong(3,courseId);
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
}
