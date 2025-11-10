package com.studentportal;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleServer {
    public static void main(String[] args) throws Exception {
        // Initialize mock data
        com.studentportal.dao.MockDataStore.getInstance();
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Serve static files
        server.createContext("/student-admission-portal/", new FileHandler());
        server.createContext("/", new RootHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("========================================");
        System.out.println("Student Admission Portal is RUNNING!");
        System.out.println("========================================");
        System.out.println("Server started on port 8080");
        System.out.println("Open your browser:");
        System.out.println("http://localhost:8080/student-admission-portal/welcome.xhtml");
        System.out.println("========================================");
        
        // Open browser
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("http://localhost:8080/student-admission-portal/welcome.xhtml"));
        } catch (Exception e) {
            System.out.println("Please open browser manually");
        }
    }
    
    static class RootHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "<html><head><meta http-equiv='refresh' content='0;url=/student-admission-portal/welcome.xhtml'></head><body>Redirecting...</body></html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class FileHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String path = t.getRequestURI().getPath();
            path = path.replace("/student-admission-portal", "");
            if (path.equals("/") || path.isEmpty()) {
                path = "/welcome.xhtml";
            }
            
            // Try multiple locations
            String[] locations = {
                "target/student-admission-portal-1.0.0" + path,
                "src/main/webapp" + path,
                "target/student-admission-portal-1.0.0/welcome.xhtml",
                "src/main/webapp/welcome.xhtml"
            };
            
            File file = null;
            for (String loc : locations) {
                File f = new File(loc);
                if (f.exists() && f.isFile()) {
                    file = f;
                    break;
                }
            }
            
            if (file == null || !file.exists()) {
                // Return a simple HTML page
                String html = getSimpleHTML(path);
                t.sendResponseHeaders(200, html.length());
                OutputStream os = t.getResponseBody();
                os.write(html.getBytes());
                os.close();
                return;
            }
            
            byte[] bytes = Files.readAllBytes(file.toPath());
            String contentType = getContentType(file.getName());
            
            t.getResponseHeaders().set("Content-Type", contentType);
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
        
        private String getContentType(String filename) {
            if (filename.endsWith(".html") || filename.endsWith(".xhtml")) return "text/html; charset=UTF-8";
            if (filename.endsWith(".css")) return "text/css";
            if (filename.endsWith(".js")) return "application/javascript";
            if (filename.endsWith(".png")) return "image/png";
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
            return "text/plain";
        }
        
        private String getSimpleHTML(String path) {
            if (path.contains("welcome")) {
                return getWelcomePage();
            } else if (path.contains("registration")) {
                return getRegistrationPage();
            } else if (path.contains("confirmation")) {
                return getConfirmationPage();
            } else if (path.contains("dashboard")) {
                return getDashboardPage();
            }
            return getWelcomePage();
        }
        
        private String getWelcomePage() {
            return "<!DOCTYPE html><html><head><title>Welcome - Student Admission Portal</title>" +
                   "<link rel='stylesheet' href='/student-admission-portal/resources/css/style.css'>" +
                   "</head><body><div class='container'><div class='header'><h1>🎓 Student Admission Portal</h1>" +
                   "<p>Your Gateway to Quality Education</p></div><div class='content'><div class='welcome-section'>" +
                   "<h2>Welcome to Our Admission System</h2><p>We are delighted that you are considering joining our institution.</p>" +
                   "<a href='/student-admission-portal/registration.xhtml' class='btn-primary'>Start Registration</a>" +
                   "</div></div></div></body></html>";
        }
        
        private String getRegistrationPage() {
            return "<!DOCTYPE html><html><head><title>Registration</title>" +
                   "<link rel='stylesheet' href='/student-admission-portal/resources/css/style.css'>" +
                   "</head><body><div class='container'><div class='header'><h1>📝 Student Registration</h1></div>" +
                   "<div class='content'><form method='post' action='/student-admission-portal/confirmation.xhtml'>" +
                   "<div class='form-container'><h3>Personal Information</h3>" +
                   "<div class='form-group'><label>First Name *</label><input type='text' name='firstName' required></div>" +
                   "<div class='form-group'><label>Last Name *</label><input type='text' name='lastName' required></div>" +
                   "<div class='form-group'><label>Email *</label><input type='email' name='email' required></div>" +
                   "<div class='form-group'><label>Phone *</label><input type='tel' name='phone' required></div>" +
                   "<div class='form-group'><label>Date of Birth *</label><input type='date' name='dob' required></div>" +
                   "<div class='form-group'><label>Course *</label><select name='course' required>" +
                   "<option>Computer Science (CS101)</option><option>Electrical Engineering (EE201)</option>" +
                   "<option>Mechanical Engineering (ME301)</option></select></div>" +
                   "<button type='submit' class='btn-primary'>Submit Registration</button></div></form></div></div></body></html>";
        }
        
        private String getConfirmationPage() {
            return "<!DOCTYPE html><html><head><title>Confirmation</title>" +
                   "<link rel='stylesheet' href='/student-admission-portal/resources/css/style.css'>" +
                   "</head><body><div class='container'><div class='header'><h1>✅ Registration Successful</h1></div>" +
                   "<div class='content'><div class='success-message'>🎉 Congratulations! Your registration has been completed successfully.</div>" +
                   "<div class='info-card'><h3>Registration Details</h3><div class='info-row'><span class='info-label'>Application Number:</span>" +
                   "<span class='info-value'>APP20250101001</span></div></div>" +
                   "<a href='/student-admission-portal/dashboard.xhtml' class='btn-primary'>Go to Dashboard</a>" +
                   "</div></div></body></html>";
        }
        
        private String getDashboardPage() {
            return "<!DOCTYPE html><html><head><title>Dashboard</title>" +
                   "<link rel='stylesheet' href='/student-admission-portal/resources/css/style.css'>" +
                   "</head><body><div class='container'><div class='header'><h1>📊 Admin Dashboard</h1></div>" +
                   "<div class='content'><div class='stats-container'><div class='stat-card'><h3>2</h3><p>Total Students</p></div>" +
                   "<div class='stat-card'><h3>2</h3><p>Total Admissions</p></div><div class='stat-card'><h3>8</h3><p>Available Courses</p></div></div>" +
                   "<div class='form-container'><h3>📋 Registered Students</h3><table class='data-table'><thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th></tr></thead>" +
                   "<tbody><tr><td>1</td><td>John Doe</td><td>john.doe@example.com</td><td>1234567890</td></tr>" +
                   "<tr><td>2</td><td>Jane Smith</td><td>jane.smith@example.com</td><td>9876543210</td></tr></tbody></table></div>" +
                   "<a href='/student-admission-portal/welcome.xhtml' class='btn-primary'>Back to Welcome</a></div></div></body></html>";
        }
    }
}

