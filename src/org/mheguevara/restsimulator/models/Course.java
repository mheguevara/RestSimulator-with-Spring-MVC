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
 * Date: 9/22/12
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "course")
public class Course implements Serializable{

    private static final Logger logger = Logger.getLogger(Course.class);

    private long id;
    private String name;
    private String code;

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

    public String getCode() {
        return code;
    }

    @XmlElement
    public void setCode(String code) {
        this.code = code;
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
