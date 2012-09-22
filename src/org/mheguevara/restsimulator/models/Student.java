package org.mheguevara.restsimulator.models;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/21/12
 * Time: 11:29 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "student")
public class Student implements Serializable {

    private static final Logger logger = Logger.getLogger(Student.class);

    private long id;
    private String name;
    private String surname;
    private int schoolNumber;
    private String email;

    public long getId() {
        return id;
    }

    @XmlElement
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    @XmlElement
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSchoolNumber() {
        return schoolNumber;
    }

    @XmlElement
    public void setSchoolNumber(int schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String result = null;
        try {
            result = new ObjectMapper().defaultPrettyPrintingWriter().writeValueAsString(this);
        } catch (IOException e) {
            logger.warn(e,e);
        }
        return result;
    }
}
