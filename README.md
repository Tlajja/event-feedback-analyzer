# Event Feedback Analyzer

<img width="2347" height="1486" alt="Screenshot 2025-11-16 at 01-38-08 EventSync" src="https://github.com/user-attachments/assets/f5a32869-21f2-48fb-becd-7796267880b8" />

## Overview

Streamline your event management with automated feedback analysis. This platform collects participant feedback and uses AI sentiment analysis to automatically classify responses as positive, neutral, or negative.

---

## Features

- **Event Management** - Create and manage events with titles and descriptions
- **Feedback Collection** - Collect written feedback from event participants
- **AI Sentiment Analysis** - Automatic classification using Hugging Face API

---

## Tech Stack

- **Backend:** Java 21 with Spring Boot
- **Database:** H2 In-Memory Database
- **AI Service:** Hugging Face Inference API (cardiffnlp/twitter-roberta-base-sentiment-latest)
- **Frontend:** HTML, CSS, JavaScript
- **Build Tool:** Gradle

---

## Prerequisites

1. **Java 17 or 21**
   - Check with: `java --version`
   - Download from: [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

---

## Installation

### Step 1: Clone the repository

```bash
git clone https://github.com/Tlajja/event-feedback-analyzer.git
cd event-feedback-analyzer
```

### Step 2: Get Hugging Face API Token

1. Go to [Hugging Face](https://huggingface.co/) and create a free account or log in
2. Click your profile picture in the top-right corner and select **Settings**
3. Navigate to **Access Tokens** in the left sidebar
4. Click **Create new token**
5. Select **Read** as the Token type
6. Enter a token name like `event-feedback-analyzer`
7. Click **Create token**
8. **Copy your token** - it will look like: `hf_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`

### Step 3: Configure API Token

1. Navigate to and open `src/main/resources/application.properties`
2. Replace `YOUR_HUGGINGFACE_API_TOKEN` with your Hugging Face token:

```
huggingface.api.token=YOUR_HUGGINGFACE_API_TOKEN
```

### Step 4: Start the application

Navigate to the repository and in the terminal run:

```bash
./gradlew bootRun
```

---

## Usage

### Accessing the Application

1. **Web Interface:** Open <http://localhost:8080> in your browser
2. **API Endpoints:** Base URL is <http://localhost:8080>
3. **H2 Console:** <http://localhost:8080/h2-console> (for database inspection)
   - JDBC URL: `jdbc:h2:mem:eventdb`
   - Username: `sa`
   - Password: (leave empty)

**Create Events**

- Click "Create Event" in the main interface
- Enter an event name and description
- Click "Create Event" to submit

<img width="1636" height="1485" alt="Screenshot 2025-11-16 at 01-39-57 EventSync" src="https://github.com/user-attachments/assets/9ff20362-b840-4cac-a6ce-1a9278b85260" />

<br>
<br>

**Submit Feedback**

- Click on any event card to view its details
- Use the feedback form to submit participant comments
- The AI will automatically analyze sentiment and display results

**View Analytics**

- Check the sentiment summary for each event
- See sentiment scores for each feedback entry
- Use the filter to view feedback by sentiment type (Positive, Neutral, Negative)

<br>

<img width="1255" height="1455" alt="Screenshot 2025-11-16 at 01-41-02 EventSync" src="https://github.com/user-attachments/assets/3ad59405-bd87-432d-aa0f-372f590f44df" />

---

## API Endpoints

### Event Management

| Method | Endpoint       | Description                          |
| ------ | -------------- | ------------------------------------ |
| POST   | `/events`      | Create new event                     |
| GET    | `/events`      | List all events with feedback counts |
| GET    | `/events/{id}` | Get specific event details           |

### Feedback & Analytics

| Method | Endpoint                | Description                    |
| ------ | ----------------------- | ------------------------------ |
| POST   | `/events/{id}/feedback` | Submit feedback for an event   |
| GET    | `/events/{id}/feedback` | Get all feedback for an event  |
| GET    | `/events/{id}/summary`  | Get sentiment analysis summary |

---

## Project Structure

```
event-feedback-analyzer
├── gradle
│   └── wrapper
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── eventsync
    │   │           └── event_feedback_analyzer
    │   │               ├── controllers
    │   │               ├── dtos
    │   │               ├── enums
    │   │               ├── exceptions
    │   │               ├── models
    │   │               ├── repositories
    │   │               └── services
    │   └── resources
    │       ├── static
    │       │   ├── css
    │       │   └── js
    │       └── templates
    └── test
        └── java
            └── com
                └── eventsync
                    └── event_feedback_analyzer
```

---

### Running Tests

From the project root:

```
./gradlew test
```

This executes all tests under

```
src/test/java/...
```

### Generating Coverage Report (JaCoCo)

```
./gradlew jacocoTestReport
```

The report will be created at:

```
build/reports/jacoco/test/html/index.html
```

---

## Troubleshooting

**Issue:** "Port 8080 already in use" <br>
**Solution:**

- Kill the process that is using port 8080
- Or change the port in application.properties: server.port=8081 (or any other free port)

**Issue:** Sentiment Analysis fails <br>
**Solution:**

- Check if your Hugging Face API token is correctly configured
- Verify the token has "Read" permissions
- Check application logs for API errors

**Issue:** Application gives error while building <br>
**Solution:**

- This project requires **Java 17 or Java 21** (Java 25 is not supported)
- Check your Java version: `java --version`
- Download Java 17: [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=17)
- Set JAVA_HOME if needed:

  ```bash
  # Mac/Linux
  export JAVA_HOME=$(/usr/lib/jvm -v 17)

  # Windows (PowerShell)
  $env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.x.x"
  ```

- Clean and rebuild: `./gradlew clean build`

**Issue:** Application crashes on startup <br>
**Solution:**

- Verify your Hugging Face API token is set in `application.properties`
- Check console logs for specific error messages
- Ensure H2 database can be created (check file permissions)

**Issue:** "Hugging Face API token is not configured" <br>
**Solution:**

- Verify your token is correctly set in `src/main/resources/application.properties`:

  ```properties
  huggingface.api.token=hf_xxxxxxxxxxxxxxxxxxxxx
  ```

- Make sure there are no extra spaces or quotes around the token

---

# Have Fun
