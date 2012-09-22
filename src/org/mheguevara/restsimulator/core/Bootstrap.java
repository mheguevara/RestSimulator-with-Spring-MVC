package org.mheguevara.restsimulator.core; /**
 * Created with IntelliJ IDEA.
 * User: muhammet
 * Date: 9/21/12
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Bootstrap implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(Bootstrap.class);

    // Public constructor is required by servlet spec
    public Bootstrap() {



    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is
           initialized(when the Web application is deployed). 
           You can initialize servlet context related data here.
        */
        PropertyConfigurator.configure(
                System.getProperty("CONFIG_PATH")
                        .concat(System.getProperty("file.separator"))
                        .concat("RestSimulator")
                        .concat(System.getProperty("file.separator"))
                        .concat("log4j.properties")

        );


        logger.info("Bootstrapped!");



    }

    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is invoked when the Servlet Context 
           (the Web application) is undeployed or 
           Application Server shuts down.
        */
        logger.info("Destroyed!");
    }
}
