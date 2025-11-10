# 🎓 Student Admission Portal

A complete **Student Admission Management System** built with **JSF (JavaServer Faces)** and **Hibernate ORM**, demonstrating advanced Java programming concepts including navigation rules, managed beans, validators, converters, form events, and database persistence.

## ✨ Features

- **Multiple JSF Pages** with navigation rules (welcome → registration → confirmation → dashboard)
- **Managed Beans** for data storage and passing between pages
- **Input Validation** using custom validators (email, phone, age range)
- **Form Submission Events** (ActionEvents and ValueChangeEvents)
- **Hibernate ORM** for database persistence with relational mappings
- **Complete CRUD Operations** (Create, Read, Update, Delete)
- **Professional UI** with modern design

## 🛠️ Technologies Used

- **Java 8**
- **JSF 2.3** (JavaServer Faces)
- **Hibernate 5.6** (ORM)
- **H2 Database** (Embedded - No setup required)
- **Maven** (Build tool)
- **Jetty** (Embedded server)

## 📋 Requirements

- Java JDK 8 or higher
- Maven 3.6 or higher

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/student-admission-portal.git
cd student-admission-portal
```

### 2. Build the Project
```bash
mvn clean package
```

### 3. Run the Server

**Option 1: Using Batch File (Windows)**
```bash
RUN_COMPLETE_SERVER.bat
```

**Option 2: Using Maven**
```bash
mvn exec:java -Dexec.mainClass="com.studentportal.ServerRunner" -Dexec.classpathScope=runtime
```

**Option 3: Using Jetty Plugin**
```bash
mvn jetty:run
```

### 4. Access the Application

Open your browser and navigate to:
```
http://localhost:8080/student-admission-portal/welcome.xhtml
```

## 📁 Project Structure

```
student-admission-portal/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/studentportal/
│   │   │       ├── bean/          # Managed Beans
│   │   │       ├── dao/           # Data Access Objects
│   │   │       ├── entity/       # JPA Entities
│   │   │       ├── validator/    # Custom Validators
│   │   │       ├── converter/    # Custom Converters
│   │   │       ├── util/         # Utilities
│   │   │       └── ServerRunner.java
│   │   ├── resources/
│   │   │   └── hibernate.cfg.xml # Hibernate Configuration
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml
│   │       │   └── faces-config.xml
│   │       ├── resources/
│   │       │   └── css/
│   │       │       └── style.css
│   │       ├── welcome.xhtml
│   │       ├── registration.xhtml
│   │       ├── confirmation.xhtml
│   │       └── dashboard.xhtml
│   └── pom.xml
├── README.md
└── .gitignore
```

## 🎯 Key Implementations

### 1. JSF Navigation Rules
Navigation configured in `faces-config.xml`:
- Welcome → Registration
- Registration → Confirmation (on success)
- Confirmation → Dashboard

### 2. Managed Beans
- **RegistrationBean** (`@SessionScoped`) - Manages registration flow
- **DashboardBean** (`@ViewScoped`) - Manages dashboard operations

### 3. Custom Validators
- **EmailValidator** - Validates email format
- **PhoneValidator** - Validates phone number (10-15 digits)
- **Age Validation** - Validates age between 16-100 years

### 4. Form Events
- **ValueChangeEvent** - Course selection in registration form
- **ActionEvent** - Button clicks with `setPropertyActionListener`

### 5. Hibernate ORM
- Entity mappings with JPA annotations
- Relational mappings (One-to-Many, Many-to-One)
- Lazy loading and cascade operations
- Automatic table creation

### 6. CRUD Operations
- **Create**: Save students, courses, admissions
- **Read**: Find all, find by ID, find by email
- **Update**: Update student and admission records
- **Delete**: Delete with proper cascade handling

## 🗄️ Database

The application uses **H2 Embedded Database** by default:
- No setup required
- Tables created automatically
- Sample data initialized on first run

**To use MySQL:**
1. Update `src/main/resources/hibernate.cfg.xml`
2. Change connection properties to MySQL
3. Create database: `CREATE DATABASE student_portal;`

## 📖 Application Flow

1. **Welcome Page** → Start registration
2. **Registration Page** → Fill form with validation
3. **Confirmation Page** → View registration details
4. **Dashboard** → Admin operations (view, edit, delete, approve/reject)

## 🧪 Testing

1. Register a new student
2. Verify validation (try invalid email/phone)
3. Check confirmation page
4. View dashboard
5. Test CRUD operations (edit, delete, approve/reject)

## 📝 Configuration

### Hibernate Configuration
Edit `src/main/resources/hibernate.cfg.xml` to change database settings.

### JSF Configuration
Navigation rules and validators are configured in `src/main/webapp/WEB-INF/faces-config.xml`.

## 🐛 Troubleshooting

### Port 8080 Already in Use
```bash
# Windows
netstat -ano | findstr :8080
taskkill /F /PID <process_id>
```

### Build Errors
```bash
mvn clean compile
```

### Database Issues
- Ensure H2 dependency is in `pom.xml`
- Check `hibernate.cfg.xml` configuration
- Verify `DatabaseInitializer` is registered

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

Your Name

## 🙏 Acknowledgments

- JSF Framework
- Hibernate ORM
- H2 Database
- Maven Community

---

**Made with ❤️ for Advanced Java Programming**
