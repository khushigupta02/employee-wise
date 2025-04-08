#  Employee Management Application

Employee Management App (Spring Boot + CouchDB)

🧰 Technology Stack:
- Backend: Spring Boot (Maven Project)
- Database: CouchDB (NoSQL)
- Java Version: 17+
- Build Tool: Maven
- REST APIs: JSON format request/response

📌 Description:
This application provides REST APIs to manage employees using CouchDB. It supports adding, updating, deleting, and retrieving employee records, including hierarchical manager-level tracking and pagination support.

----------------------------------------------------------
🔧 SETUP INSTRUCTIONS
----------------------------------------------------------

1. ✅ PREREQUISITES:
   - Java 17 or later
   - Apache Maven
   - CouchDB installed and running (http://127.0.0.1:5984/)
   - Gmail account (For sending emails – Advanced Level)

2. 📥 PROJECT SETUP:
   - Unzip the project archive (`EmployeeApp.zip` or `.rar`)
   - Open the project in your favorite IDE (IntelliJ, VS Code, Eclipse)
   - Configure the CouchDB properties in `application.properties`:
    ```json
     spring.couchdb.url=http://localhost:5984/
     spring.couchdb.username=admin
     spring.couchdb.password=12345
     spring.couchdb.database=employees
    ```

3. 📦 BUILD PROJECT:
   - Open terminal and run:
     ```bash
     mvn clean install
     ```

4. 🚀 RUN PROJECT:
   - Run the application from the IDE or using:
     ```bash
     mvn spring-boot:run
     ```

----------------------------------------------------------
📬 EMAIL SETUP (Advanced Level):
----------------------------------------------------------

1. Create a new Gmail account.
2. Enable "Less Secure Apps" OR create an App Password.
3. Add the following to `application.properties`:
```json
spring.mail.host=smtp.gmail.com spring.mail.port=587
spring.mail.username=yournewemail@gmail.com
spring.mail.password=yourAppPassword
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

----------------------------------------------------------
📌 API DOCUMENTATION
----------------------------------------------------------

📍 BASE URL: `http://localhost:8080/api/employees/`

----------------------------------
1. Add Employee [Entry Level]
----------------------------------
- Method: POST
- Endpoint: `/add`
- Request Body:
```json
{
  "employeeName": "John Doe",
  "phoneNumber": "9876543210",
  "email": "john.doe@example.com",
  "reportsTo": "manager-uuid",
  "profileImage": "https://example.com/image.jpg"
}''

```
----------------------------------
2. Get All Employees [Entry Level]
----------------------------------
- Method: GET
- Endpoint: `/get`
- Request Body:
```json
[
  {
    "id": "uuid-1",
    "employeeName": "John Doe",
    "phoneNumber": "9876543210",
    "email": "john.doe@example.com",
    "reportsTo": "manager-uuid",
    "profileImage": "https://example.com/image.jpg"
  }
]

```
----------------------------------
3. Delete Employee by ID [Entry Level]
----------------------------------
- Method: DELETE
- Endpoint: `/delete/{id}`
- Request Body:{}
- Reponse : "Deleted Successfully!"
  
4. Update Employee by ID [Entry Level]
----------------------------------
- Method: PUT
- Endpoint: `/update/{id}`
- Request Body:  (same as Add Employee) 
```json
[
  {
    "employeeName": "John Doe",
    "phoneNumber": "9876543210",
    "email": "john.doe@example.com",
    "reportsTo": "manager-uuid",
    "profileImage": "https://example.com/image.jpg"
  }
]
```
- Reponse : "Deleted Successfully!"

5. Get nth Level Manager [Intermediate Level]
----------------------------------
- Method: GET
- Endpoint: `/{id}/manager/{level}`
- Example: /123e4567-e89b-12d3-a456-426614174000/manager/2
- Response: Employee object of nth level manager

6. Get Employees with Pagination and Sorting [Intermediate Level]
----------------------------------
- Method: POST
- Endpoint: `/getPagination`
- Request Body:  
```json
{
  "page": 0,
  "size": 5,
  "sortBy": "employeeName",
  "sortOrder": "asc"
}
```
- Reponse :
```json
{
  "employees": [...],
  "totalElements": 10,
  "totalPages": 2,
  "currentPage": 0
}
```
7. Send Email to Level 1 Manager on New Employee Addition [Advanced]
----------------------------------
- When a new employee is added, an email is sent to their Level 1 Manager:
```json
- Subject: New Employee Added
Body:
"John Doe will now work under you. Mobile number is 9876543210 and email is john.doe@example.com."
```

📂 Files Included in ZIP:
- Spring Boot Project (Maven-based)
- README.txt (this file)
- EmployeeWise.postman_collection.json (import it in Postman to test APIs)

----------------------------------

🧪 Postman Collection
- File Name: EmployeeWise.postman_collection.json

- How to Use:
- Open Postman
- Click on Import → Upload Files
- Select the EmployeeWise.postman_collection.json from the ZIP
- Use the environment variables or directly run APIs
