MediTrack

A secure, role-based web application built using **Spring Boot**, **Thymeleaf**, **Spring Security**, and **MySQL** to manage clinical data including patient information, visits, and lab results.

## 🚀 Featuress

- **User Authentication & Role Management**
  - Admin, Doctor, and Data Entry roles.
  - Spring Security integration with role-based access control.
  
- **Patient Management**
  - Add, edit, delete, and view patient records.
  - Doctors can only manage their assigned patients.

- **Visit Management**
  - Data Entry users can add clinical visits for assigned patients.
  - Doctors and Admins can view all visits.

- **Lab Results**
  - Add, view, and list lab results for each visit.
  - Accessible by Data Entry users for assigned patients.

- **Search & Dashboard**
  - Search patients by name.
  - Role-based dashboards and navigation.

## 🛠️ Tech Stack

| Layer          | Technology               |
|----------------|--------------------------|
| Backend        | Spring Boot              |
| Frontend       | Thymeleaf + HTML/CSS     |
| Security       | Spring Security          |
| Database       | MySQL                    |
| ORM            | Spring Data JPA (Hibernate) |
| Build Tool     | Maven                    |

## 🗂️ Project Structure

cdms/
├── src/
│ ├── main/
│ │ ├── java/com/clinicdata/cdms/
│ │ │ ├── controller/
│ │ │ ├── model/
│ │ │ ├── repository/
│ │ │ ├── service/
│ │ │ └── security/
│ │ └── resources/
│ │ ├── templates/
│ │ ├── static/css/
│ │ └── application.properties
├── pom.xml
└── README.md

bash
Copy
Edit

## 🧪 How to Run

1. **Clone the repo**

```bash
git clone https://github.com/your-username/cdms.git
cd cdms
Configure application.properties

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/cdms
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.thymeleaf.cache=false
Create MySQL Database

sql
Copy
Edit
CREATE DATABASE cdms;
Run the App

bash
Copy
Edit
./mvnw spring-boot:run
Access in Browser

arduino
Copy
Edit
http://localhost:8080/
👤 Default Roles and Behavior
Role	Permissions
Admin	Full access to user & patient data
Doctor	View & manage own patients
Data Entry	Add visits and lab results for assigned patients

📷 Screenshots
You can add sample screenshots of login page, dashboards, lab result form, etc.

📌 Future Enhancements (Phase 7+)
Add audit logging.

Export reports as PDF.

Email notifications for doctors on new lab results.

Add profile management for users.

🤝 Contributions
Contributions are welcome! Please open issues or pull requests.
