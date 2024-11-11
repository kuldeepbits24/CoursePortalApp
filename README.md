Here is a README for your Java Swing Course Portal program:

---

# Course Portal Application

A simple Java Swing application that allows students and teachers to register, log in, and manage course requests. Students can apply for courses with teachers, and teachers can approve or deny the requests. The application provides functionalities for logging in, registering, managing course requests, and logging out.

## Features

- **User Registration**: Students and teachers can register with a username and password.
- **Login**: Users can log in as either a student or a teacher.
- **Student Dashboard**: Students can:
  - View available teachers.
  - Apply for a course by sending a request to the selected teacher.
  - View the status of their course requests.
- **Teacher Dashboard**: Teachers can:
  - View student course requests.
  - Approve or deny course requests from students.
- **Logout**: Both students and teachers can log out to return to the login screen.

## Requirements

- **Java 8 or higher**
- **IDE for Java development** (e.g., IntelliJ IDEA, Eclipse)

## Running the Application

### Steps to Run in IntelliJ IDEA

1. **Clone/Download the Project**: Download or clone this project to your local machine.
2. **Open IntelliJ IDEA**:
   - If the project is downloaded as a ZIP file, extract it to your preferred directory.
   - Open IntelliJ IDEA and choose `Open` from the welcome screen.
   - Navigate to the folder where the project is located and select it.
3. **Run the Program**:
   - Inside IntelliJ, right-click on the `CoursePortal.java` file.
   - Select `Run 'CoursePortal.main()'`.
4. **Interact with the Program**:
   - A login screen will appear where you can register and log in as either a student or a teacher.
   - Follow the prompts to use the functionalities available to both roles.

## Code Overview

### `CoursePortal.java`

The main class that drives the application, which includes:
- A `HashMap` to store users' information (`users`), course requests for each teacher (`teacherRequests`), and course requests for each student (`studentRequests`).
- Registration, login, and dashboard creation functionality for students and teachers.

### `StudentDashboard.java`

The dashboard displayed when a student logs in. Features:
- Apply for a course by selecting a teacher.
- View the status of course requests (Pending/Approved).
- Logout functionality to return to the login page.

### `TeacherDashboard.java`

The dashboard displayed when a teacher logs in. Features:
- View course requests from students.
- Approve or deny requests.
- Logout functionality to return to the login page.

## Example Usage

1. **Registering a User**:
   - Open the application, go to the register page.
   - Enter a unique username and password and select the user type (Student or Teacher).
   - Click `Register` to create the account.

2. **Logging In**:
   - Enter your username and password on the login screen.
   - Choose the correct user type (Student or Teacher) and click `Login`.
   - If valid, you will be taken to the corresponding dashboard.

3. **Student Functionality**:
   - On the student dashboard, select a teacher and click `Apply for Course` to send a request.
   - You can view the status of your request by clicking `Refresh Status`.

4. **Teacher Functionality**:
   - On the teacher dashboard, view the course requests from students.
   - Approve or deny each request by clicking the `Approve` button.

5. **Logging Out**:
   - Click the `Logout` button on either the student or teacher dashboard to return to the login screen.

## Troubleshooting

- **If the application doesn't launch**: Ensure that Java is properly installed and the correct SDK is selected in your IDE.
- **Issues with registration or login**: Double-check the username and password for typos.

## License

This project is open-source and available under the MIT License.

---

This README provides a concise overview of the program, its features, and instructions for running the application. Feel free to add or modify any additional information specific to your project.
