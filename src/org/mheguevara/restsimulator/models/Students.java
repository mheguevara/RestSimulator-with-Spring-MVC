package org.mheguevara.restsimulator.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/22/12
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "students")
public class Students {

    private List<Student> student;

    public Students() {
    }

    public Students(List<Student> student) {
        this.student = student;
    }

    public List<Student> getStudent() {
        return student;
    }

    @XmlElement
    public void setStudent(List<Student> student) {
        this.student = student;
    }
}
