package com.studentportal;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import java.io.File;

public class ServerRunner {
    public static void main(String[] args) {
        try {
            // Kill any existing process on port 8080
            try {
                Runtime.getRuntime().exec("netstat -ano | findstr :8080");
            } catch (Exception e) {}
            
            Server server = new Server(8080);
            
            WebAppContext context = new WebAppContext();
            
            // Use the exploded WAR from target directory
            String webappPath = "target/student-admission-portal-1.0.0";
            File webappDir = new File(webappPath);
            
            if (!webappDir.exists()) {
                // Fallback to source directory
                webappPath = "src/main/webapp";
                webappDir = new File(webappPath);
            }
            
            context.setResourceBase(webappDir.getAbsolutePath());
            context.setContextPath("/student-admission-portal");
            context.setDescriptor(webappDir.getAbsolutePath() + "/WEB-INF/web.xml");
            context.setParentLoaderPriority(true);
            context.setClassLoader(Thread.currentThread().getContextClassLoader());
            
            // Enable JSF
            context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/javax.faces-[^/]*\\.jar$|.*/jsf-[^/]*\\.jar$");
            
            server.setHandler(context);
            server.start();
            
            System.out.println("========================================");
            System.out.println("Student Admission Portal is running!");
            System.out.println("========================================");
            System.out.println("Server started on port 8080");
            System.out.println("Open your browser and go to:");
            System.out.println("http://localhost:8080/student-admission-portal/welcome.xhtml");
            System.out.println("========================================");
            
            // Open browser automatically
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("http://localhost:8080/student-admission-portal/welcome.xhtml"));
            } catch (Exception e) {
                System.out.println("Could not open browser automatically. Please open manually.");
            }
            
            server.join();
        } catch (Exception e) {
            System.err.println("Error starting server:");
            e.printStackTrace();
            System.err.println("\nTrying alternative approach...");
            // Fallback: try with source directory
            try {
                Server server = new Server(8080);
                WebAppContext context = new WebAppContext();
                context.setResourceBase("src/main/webapp");
                context.setContextPath("/student-admission-portal");
                context.setParentLoaderPriority(true);
                server.setHandler(context);
                server.start();
                System.out.println("Server started with source directory!");
                server.join();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}

