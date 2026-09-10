# Hospital Management System (HMS) - APU Medical Centre

## Project Overview
A comprehensive Object-Oriented Java application for managing healthcare operations at APU Medical Centre. The system supports four types of users with role-based access: Admin Staff, Medical Managers, Doctors, and Patients.

## Project Structure

```
OODJ202606/
├── src/
│   └── com/apu/hms/
│       ├── HospitalManagementSystem.java (Main entry point)
│       ├── models/           (Data models)
│       │   ├── User.java (Abstract base class)
│       │   ├── AdminStaff.java
│       │   ├── MedicalManager.java
│       │   ├── Doctor.java
│       │   ├── Patient.java
│       │   ├── Department.java
│       │   ├── Ward.java
│       │   ├── MedicalAssessment.java
│       │   ├── Appointment.java
│       │   ├── Prescription.java
│       │   └── Billing.java
│       ├── views/            (GUI components using Swing)
│       │   └── LoginView.java
│       ├── controllers/      (Business logic - to be implemented)
│       ├── services/         (Service layer - to be implemented)
│       └── utils/            (Utility classes)
│           ├── FileManager.java (File I/O operations)
│           └── ValidationUtil.java (Input validation)
├── data/                     (Data storage - text files)
├── lib/                      (External libraries - if needed)
└── README.md

```

## Key Features & OOP Concepts

### 1. **Inheritance & Polymorphism**
   - Abstract `User` class with concrete implementations for each role
   - `displayMenu()` abstract method overridden in each user type

### 2. **Encapsulation**
   - Private attributes with public getters/setters
   - Controlled access to sensitive data

### 3. **Abstraction**
   - Abstract `User` class hides implementation details
   - Users interact through well-defined interfaces

### 4. **Data Models**
   - `Department` - Represents medical departments
   - `Ward` - Hospital wards/clinics with capacity management
   - `MedicalAssessment` - Patient vital signs and findings
   - `Appointment` - Consultation bookings
   - `Prescription` - Digital medication records
   - `Billing` - Payment tracking with medical grading

### 5. **Persistence**
   - File-based storage using Java serialization
   - Text files for data storage (no databases allowed)

## User Roles & Functionalities

### **Admin Staff**
- Create/Read/Update/Delete users
- Assign doctors to medical managers
- Manage hospital assets (rooms, wards, labs)
- Configure consultation rates
- Manage insurance networks

### **Medical Managers**
- Edit personal profile
- Create/update clinical departments
- Design doctor shift rosters
- View hospital metrics reports
- View revenue summaries

### **Doctors**
- Edit personal profile
- Log patient vital signs
- Write consultation notes
- Issue digital prescriptions
- Request lab tests/imaging

### **Patients**
- Edit personal profile
- Browse and book doctor appointments
- Reschedule/cancel bookings
- View medical history
- View prescriptions
- Submit ratings and feedback

## How to Compile & Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line terminal

The application is platform-independent and works on Windows, macOS, and Linux. Java Swing is included with the JDK, so no additional GUI library is required.

### macOS Application
Install a JDK such as Eclipse Temurin from https://adoptium.net, then double-click `APU Medical Centre HMS.app` in Finder. The app builds the project automatically when the JDK is installed.

If macOS blocks the first launch, Control-click the app, choose **Open**, and confirm **Open**.

You can also compile and run the application manually:
```bash
mkdir -p bin
find src -name '*.java' -print0 | xargs -0 javac -d bin -sourcepath src
java -cp bin com.apu.hms.HospitalManagementSystem
```

## File Management

- **Data Storage**: All data is stored in text files in the `data/` directory
- **File Formats**: Java object serialization (.ser files)
- **File Manager**: Utility class handles all file operations

## Validation

Input validation is performed using `ValidationUtil` class:
- Email format validation
- Phone number format validation
- Password strength checking
- Numeric input validation
- Date format validation

## GUI Components

The application uses Java Swing for the graphical user interface:
- `LoginView` - User authentication interface
- Grid-based layouts for responsive design
- Color-coded buttons for different actions
- Dialog boxes for user feedback

## Next Steps to Implement

1. **User Authentication Service** - Login/Registration logic
2. **Database Manager** - Implement user and data storage
3. **Appointment Scheduler** - Booking and scheduling logic
4. **Billing Calculator** - Medical grading and cost calculation
5. **Report Generator** - Hospital metrics and analytics
6. **Additional Views** - For each user role functionality
7. **Error Handling** - Comprehensive exception handling

## OOP Principles Demonstrated

✓ Inheritance - User class hierarchy  
✓ Polymorphism - Abstract methods and overriding  
✓ Encapsulation - Private attributes, public interface  
✓ Abstraction - Abstract classes and interfaces  
✓ Composition - Objects containing other objects  
✓ Separation of Concerns - Models, Views, Controllers, Services  

## Notes

- The system is designed to run continuously with persistent data storage
- All user inputs are validated to prevent logical errors
- The application demonstrates professional Java programming practices
- Scalable architecture allows for easy expansion of features

---

**Last Updated**: 14 August 2026  
**Course**: Object-Oriented Design with Java  
**Institution**: APU Medical Centre
