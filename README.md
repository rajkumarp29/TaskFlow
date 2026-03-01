# TaskFlow – A Full Stack Task Management Web Application
TaskFlow is a robust, full-stack task management solution designed to bridge the gap between complex enterprise project tools and simple to-do lists. Built using a decoupled architecture with a Spring Boot backend and an Angular frontend, the platform leverages JWT (JSON Web Token) authentication to ensure secure, stateless data handling.

The application features a high-performance, responsive dashboard that allows users to manage the full lifecycle of a task—from creation to completion—with real-time status updates. By utilizing Angular Standalone Components and a Service-DAO pattern on the backend, TaskFlow demonstrates a scalable, modern approach to building CRUD-based web applications with a focus on clean UI/UX and industry-standard security protocols.


## 🎯 Objective of the Project
The objective of TaskFlow is to build a modern task management system that allows users to:
* **Secure Auth:** Register & Login securely using modern standards.
* **CRUD Operations:** Create, update, and delete tasks seamlessly.
* **Status Tracking:** Monitor progress via To-Do, In Progress, and Done states.
* **Responsive Design:** View tasks on a professional, mobile-friendly dashboard.

---

## ✨ Features

### 🔐 Authentication
* User Registration & Login with **JWT (JSON Web Tokens)**.
* Route Protection using **Angular Auth Guards**.
* Secure password handling on the backend.

### 📋 Task Management
* **Full CRUD:** Create, Edit, and Delete tasks.
* **Filtering:** Filter tasks by status for better organization.
* **Search:** Quick search functionality to find specific tasks.
* **Dashboard Stats:** Visual counters for Total, To-Do, In Progress, and Done tasks.

### 📱 Responsive UI
* Mobile-first responsive layout using **Bootstrap**.
* Horizontal stat cards optimized for small screens.
* Touch-friendly buttons (44px min height) for mobile accessibility.
* Hamburger menu navigation.

---

## 🛠 Tech Stack

### Frontend
* **Angular** (Standalone Components)
* **TypeScript**
* **Bootstrap** (UI Framework)
* **HTML5 / CSS3**

### Backend
* **Java / Spring Boot**
* **Spring Security** (JWT Authentication)
* **RESTful APIs**

### Database
* **MySQL** (Production) / **H2** (Development)

---

## 📂 Project Structure

### Backend Structure
`com.taskflow`
* `controller` — API Endpoints
* `service` — Business Logic
* `repository` — Data Access Layer
* `entity` — Database Models
* `dto` — Data Transfer Objects
* `config` — Security & App Configuration

### Frontend Structure
`src/app`
* `auth` — Login/Register components
* `tasks` — Task listing & Management
* `services` — API Communication
* `guards` — Route Protection
* `models` — Interfaces & Classes
* `shared` — Common UI components


## 🚀 How to Run the Project

### 1. Prerequisites
* **JDK 17** or higher
* **Node.js** (v18+) & **npm**
* **MySQL** Server
* **Angular CLI** (`npm install -g @angular/cli`)
* Run the application:(cd frontend/npm install/ng serve)
* Open your browser and navigate to: http://localhost:4200


### 2. Backend Setup (Spring Boot)
1. Navigate to the `backend` folder.
2. Open `src/main/resources/application.properties`.
3. Update the MySQL credentials (`username` and `password`).
4. Run the application:
   ```bash
   ./mvnw spring-boot:run

---

## 🔄 Data Flow
1. **Authentication:** User logs in → Backend generates JWT token.
2. **Storage:** Token is stored securely in `localStorage`.
3. **Interception:** Angular sends the token in the `Authorization` header for every request.
4. **Validation:** Spring Boot validates the JWT before allowing access.
5. **Sync:** Frontend fetches tasks and the Dashboard updates reactively.

---

## ⚠️ Challenges & Solutions

| Challenge | Solution |
| :--- | :--- |
| **403 Forbidden Errors** | Refined Spring Security Filter Chain & CORS config. |
| **Injection Token Errors** | Corrected Service provisioning in Standalone components. |
| **Mobile Layout Issues** | Implemented custom CSS Media Queries for stat cards. |
| **Standalone Routing** | Successfully implemented `provideRouter()` in `app.config.ts`. |

---

## 🚀 Future Scope
* **Kanban Board:** Drag & Drop interface for task movement.
* **RBAC:** Role-based access control (Admin vs. User).
* **Notifications:** Email reminders for task deadlines.
* **Theme Support:** Dark mode toggle.
* **Cloud Deployment:** Deploying the full stack on AWS or Render.

---

## 📱 Mobile Responsive Support

| Breakpoint | Layout |
| :--- | :--- |
| **xs (<576px)** | Single column / Stacked |
| **sm (576–767px)** | Compact layout |
| **md (768–991px)** | 2 Column grid |
| **lg (992px+)** | 3 Column grid |

---

## 🏁 Conclusion
TaskFlow is a robust full-stack application that demonstrates:
* Secure authentication and authorization.
* Efficient REST API integration.
* Modern, responsive UI/UX principles.
* Clean, scalable project architecture using Angular Standalone components.

---

## 👨‍💻 Developed By
**Raj Kumar**
