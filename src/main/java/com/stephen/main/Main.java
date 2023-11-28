package com.stephen.main;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * @author Stephen Chen
 * the Main class represents the entry point of the application.
 */
public class Main {
    /**
     * the main method initializes a Tomcat server and deploys a web application in it.
     * @param args command line arguments
     * @throws Exception if an error occurs while starting the Tomcat server
     */
    public static void main(String[] args) throws Exception{
        // define location of webapp
        String webappDirLocation = "src/main/webapp/";

        // create Tomcat object and set port
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.getConnector();
        // add webapp into tomcat object
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/",
                new File(webappDirLocation).getAbsolutePath());

        // add extra resources dictionary to store compiled java file
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        // start tomcat server and wait for https request
        tomcat.start();
        tomcat.getServer().await();
    }
}
