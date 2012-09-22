package org.mheguevara.restsimulator.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/22/12
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "courses")
public class Courses {

    private List<Course> course;

    public Courses() {
    }

    public Courses(List<Course> course){
        this.course = course;
    }

    public List<Course> getCourse() {
        return course;
    }

    @XmlElement
    public void setCourse(List<Course> course) {
        this.course = course;
    }
}
