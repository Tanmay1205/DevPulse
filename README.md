# 🚀 DevPulse — AI Powered Task Management Platform

A modern full-stack task management platform built with **React, Spring Boot, PostgreSQL, JWT Authentication, and Google Gemini AI** — designed to help individuals organize tasks, manage productivity, visualize progress, and receive AI-powered assistance.

![React](https://img.shields.io/badge/React-19-blue?logo=react)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Authentication-orange)
![Material UI](https://img.shields.io/badge/MaterialUI-v7-purple)

---

# 📋 Table of Contents

- Overview
- Features
- Tech Stack
- Project Structure
- Getting Started
- Prerequisites
- Backend Setup
- Frontend Setup
- Environment Variables
- API Reference
- Screenshots
- Roadmap
- Author
- License

---

# 🔍 Overview

DevPulse is a modern productivity and task management platform inspired by GitHub Projects and Trello. It enables users to efficiently organize daily tasks, monitor productivity, manage personal profiles, visualize analytics, and receive AI-powered productivity suggestions through Google Gemini.

The application is built with a scalable Spring Boot REST API backend and a responsive React frontend using Material UI.

---

# ✨ Features

## 🔐 Authentication & Security

- JWT Authentication
- Refresh Token Support
- Secure Login & Registration
- Forgot Password via Email
- Password Reset
- Role-Based Access Control
- Secure Logout

---

## 📋 Task Management

- Create Tasks
- Edit Tasks
- Delete Tasks
- Task Search
- Status Filtering
- Priority Management
- Due Date Support
- Pagination
- Recent Tasks

---

## 📊 Dashboard

- Total Tasks
- Todo Tasks
- In Progress Tasks
- Completed Tasks
- High Priority Tasks
- Today's Tasks
- Task Distribution Chart
- Recent Activity Overview

---

## 📌 Kanban Board

- TODO Column
- IN PROGRESS Column
- COMPLETED Column
- GitHub-style Task Workflow

---

## 👤 Profile Management

- Update User Profile
- Upload Profile Picture
- Delete Account
- Dynamic Navbar Avatar

---

## 🤖 AI Productivity Assistant

- Google Gemini AI Integration
- AI Productivity Suggestions
- Smart Task Assistance
- AI Chat Endpoint

---

## 🎨 Modern User Interface

- Material UI
- Responsive Design
- Gradient Theme
- Interactive Dashboard
- Beautiful Cards
- Modern Sidebar & Navbar

---

# 🛠 Tech Stack

| Layer | Technology |
|--------|------------|
| Frontend | React 19, Vite, JavaScript |
| UI | Material UI, Recharts |
| Backend | Spring Boot 3, Spring Security |
| Authentication | JWT + Refresh Token |
| Database | PostgreSQL |
| AI | Google Gemini API |
| Build Tool | Maven |

---

# 📂 Project Structure

```text
DevPulse/
│
├── frontend/
│   ├── src/
│   │   ├── api/
│   │   ├── assets/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── routes/
│   │   ├── services/
│   │   └── theme/
│   │
│   ├── package.json
│   └── vite.config.js
│
├── src/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   ├── config/
│   ├── security/
│   └── exception/
│
├── uploads/
│
└── README.md
```

---

# 🚀 Getting Started

## Prerequisites

Ensure the following are installed:

- Java 21+
- Maven
- Node.js 18+
- PostgreSQL 17+
- npm

---

# 1. Backend Setup

Clone Repository

```bash
git clone https://github.com/Tanmay1205/DevPulse.git

cd DevPulse
```

Configure PostgreSQL database in:

```text
src/main/resources/application.properties
```

Run application

```bash
mvn spring-boot:run
```

Backend available at:

```
http://localhost:8080
```

---

# 2. Frontend Setup

```bash
cd frontend

npm install

npm run dev
```

Frontend available at

```
http://localhost:5173
```

---

# 🔑 Environment Variables

Configure the following in **application.properties**

| Variable | Description |
|----------|-------------|
| spring.datasource.url | PostgreSQL Database URL |
| spring.datasource.username | Database Username |
| spring.datasource.password | Database Password |
| jwt.secret | JWT Secret Key |
| gemini.api.key | Google Gemini API Key |

---

# 📡 API Reference

## Authentication

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /api/auth/register | Register User |
| POST | /api/auth/login | Login |
| POST | /api/auth/refresh | Refresh JWT |
| POST | /api/auth/logout | Logout |
| POST | /api/auth/forgot-password | Forgot Password |
| POST | /api/auth/reset-password | Reset Password |

---

## Dashboard

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /api/dashboard | Dashboard Statistics |

---

## Tasks

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /api/tasks | Get Tasks |
| POST | /api/tasks | Create Task |
| PUT | /api/tasks/{id} | Update Task |
| DELETE | /api/tasks/{id} | Delete Task |

---

## Profile

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /api/profile | Get Profile |
| PUT | /api/profile | Update Profile |
| POST | /api/profile/upload | Upload Profile Image |
| DELETE | /api/profile | Delete Account |

---

## AI Assistant

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /api/ai | Generate AI Response |

---

# 📸 Screenshots

Coming Soon

- Login Page
- Dashboard
- Task Management
- Kanban Board
- User Profile
- AI Assistant

---

# 🗺 Roadmap

- Drag & Drop Kanban
- Calendar View
- Dark Mode
- Email Notifications
- Team Collaboration
- Activity Timeline
- Mobile Responsive Improvements
- Export Tasks to PDF & Excel
- Advanced Productivity Analytics

---

# 👨‍💻 Author

**Tanmay Sanjay Sarode**

Electronics & Computer Science Engineering

Fr. Conceicao Rodrigues College of Engineering

GitHub: https://github.com/Tanmay1205

---

# 📄 License

This project is intended for educational, learning, and portfolio purposes.