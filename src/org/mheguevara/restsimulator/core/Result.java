package org.mheguevara.restsimulator.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/22/12
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "result")
public class Result {

    private String message;

    public String getMessage() {
        return message;
    }

    @XmlElement
    public void setMessage(String message) {
        this.message = message;
    }
}
