# Hospital Management System (HMS) - APU Medical Centre

## Project Overview
A comprehensive Object-Oriented Java application for managing healthcare operations at APU Medical Centre. The system supports four types of users with role-based access: Admin Staff, Medical Managers, Doctors, and Patients.

## Project Structure

```
OODJ202606/
├── main.java                 (VS Code entry point)
├── src/
│   └── com/apu/hms/
│       ├── HospitalManagementSystem.java
│       ├── accounts/
│       ├── controllers/
│       ├── models/
│       ├── utils/
│       └── views/
├── README.md
└── bin/                     (generated after compilation)

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

### 5. **Data Storage**
   - Account data is stored in `data/accounts.txt`
   - Appointment data is stored in `data/appointments.txt`
   - The text files are loaded when the application starts and updated after changes

## User Roles & Functionalities

### **Implemented Core Features**
- Login access and patient registration
- Medical grading and billing with text-file persistence
- Ward/clinic and department/specialty creation
- Medical assessment type design
- Assessment and lab-result entry
- Clinical feedback and prescription records
- Analytical reports for stored operational data

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

## How to Run in VS Code

### Prerequisites
- Java Development Kit (JDK) installed
- VS Code with the Java extension pack

This project is designed to run directly from the source files in VS Code. No additional launcher script is required.

### Run the application
1. Open the project in VS Code.
2. Open [main.java](main.java).
3. Click the Run Java / Start Debugging button in the editor.
4. The application will launch from the `main` method and open the login screen.

### Manual compilation (optional)
```bash
mkdir -p bin
find src -name '*.java' -print0 | xargs -0 javac -d bin -sourcepath src
java -cp bin com.apu.hms.HospitalManagementSystem
```

## File Management

- **Data Storage**: Account and appointment records are persisted in pipe-delimited `.txt` files under `data/`.
- **No extra launcher scripts**: The project is intended to run directly from VS Code using [main.java](main.java).
- **File Manager**: Utility classes are kept within the Java source structure when needed.

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
