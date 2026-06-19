# Coaching App - Responsive Web Application

A full-featured responsive coaching application built with **SpringBoot** backend and **Angular** frontend.

## Features

✅ **Live Video Streaming** - Real-time video sessions
✅ **Video Storage** - Save and retrieve recorded sessions
✅ **Live Quizzes** - Real-time quiz functionality with instant results
✅ **Tutorial Content** - Structured course modules and learning paths
✅ **Fully Responsive** - Works seamlessly on mobile, tablet, and desktop
✅ **User Authentication** - JWT-based security
✅ **Progress Tracking** - Monitor student learning progress

## Tech Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Database**: MySQL 8.0 / PostgreSQL
- **Security**: JWT, Spring Security
- **API**: RESTful Architecture
- **Messaging**: WebSocket for real-time features
- **Video Processing**: FFmpeg (optional)
- **Storage**: AWS S3 / Local Storage

### Frontend
- **Framework**: Angular 18+
- **UI Library**: Bootstrap 5 / Angular Material
- **Styling**: SCSS/TailwindCSS
- **State Management**: NgRx/RxJS
- **Video Player**: Video.js / HLS.js
- **HTTP Client**: Angular HttpClient
- **Real-time**: WebSocket

## Project Structure

```
coaching-app/
├── backend/                    # SpringBoot Application
│   ├── src/
│   │   ├── main/java/
│   │   │   └── com/coaching/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── config/
│   │   │       ├── util/
│   │   │       └── CoachingAppApplication.java
│   │   ├── resources/
│   │   │   ├── application.yml
│   │   │   └── application-dev.yml
│   │   └── test/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/                   # Angular Application
│   ├── src/
│   │   ├── app/
│   │   │   ├── modules/
│   │   │   │   ├── auth/
│   │   │   │   ├── dashboard/
│   │   │   │   ├── courses/
│   │   │   │   ├── live-class/
│   │   │   │   ├── quiz/
│   │   │   │   └── video-library/
│   │   │   ├── shared/
│   │   │   ├── core/
│   │   │   ├── app.component.ts
│   │   │   └── app.config.ts
│   │   ├── assets/
│   │   ├── styles/
│   │   └── main.ts
│   ├── angular.json
│   ├── package.json
│   ├── tsconfig.json
│   └── Dockerfile
├── docker-compose.yml
├── .gitignore
└── SETUP.md
```

## Quick Start

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL 8.0 or PostgreSQL
- Docker & Docker Compose (optional)

### Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend Setup
```bash
cd frontend
npm install
ng serve
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh token

### Courses
- `GET /api/courses` - Get all courses
- `GET /api/courses/:id` - Get course details
- `POST /api/courses` - Create course (admin)

### Live Classes
- `GET /api/live-classes` - Get upcoming classes
- `POST /api/live-classes` - Start live class
- `GET /api/live-classes/:id/stream` - Get video stream

### Quizzes
- `GET /api/quizzes` - Get available quizzes
- `POST /api/quizzes/:id/submit` - Submit quiz answers
- `GET /api/quizzes/:id/results` - Get quiz results

### Videos
- `GET /api/videos` - Get saved videos
- `POST /api/videos/upload` - Upload video
- `GET /api/videos/:id/download` - Download video

## Environment Configuration

Create `.env` files:

**Backend (.env)**
```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/coaching_app
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=password
JWT_SECRET=your_secret_key
JWT_EXPIRATION=86400000
```

**Frontend (.env)**
```
NG_APP_API_URL=http://localhost:8080/api
NG_APP_WS_URL=ws://localhost:8080/ws
```

## Database Schema

Key entities:
- **User** - Student/Admin/Instructor
- **Course** - Tutorial courses
- **LiveClass** - Scheduled live sessions
- **Video** - Recorded videos
- **Quiz** - Quiz questions and responses
- **Progress** - User progress tracking

## WebSocket Events

Real-time communication for:
- Live class status updates
- Quiz question distribution
- Live results display
- Notification broadcasting

## Deployment

### Docker
```bash
docker-compose up --build
```

### Production Checklist
- [ ] Set up HTTPS/SSL
- [ ] Configure CDN for video delivery
- [ ] Set up database backups
- [ ] Configure email notifications
- [ ] Set up monitoring (ELK stack)
- [ ] Enable rate limiting
- [ ] Configure CORS properly

## Contributing

1. Create a feature branch
2. Make your changes
3. Submit a pull request

## License

MIT License
