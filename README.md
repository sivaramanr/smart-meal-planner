# Capstone Project: Smart Meal Planner

This project is a capstone demonstration of an AI-powered agent application built with Spring Boot and Google's Agent Development Kit (ADK). It leverages the Gemini large language model (LLM) in Vertex AI for intelligent interaction and retrieval augmented generation.

## Features

- **LLM powered Agent:** Uses Google's Gemini model deployed in Vertex AI for natural language understanding and generation.
- **Custom Tools:** Implements application-specific tools such as get_sessions, get_ingredient_categories, get_ingredient_nutritions, get_ingredients, get_nutritions, get_recipe_groups, get_recipe_ingredients, get_recipes, and search_recipe for diverse domain-specific queries.
- **Secure User Authentication:** Users authenticate before accessing the agent. Authentication tokens are used securely by some custom tools for API calls.
- **Agent Engine & Session Management:** Agent Engine deployment in Vertex AI with session and memory handling via VertexAiSessionService for robust agent evaluation and context management.
- **Observability & Logging:** Detailed logs are added in Controllers, Tools, and Callbacks, including User ID and Session ID for enhanced traceability.
- **API Development:** Reuses ADK's AdkWebServer framework to develop RESTful APIs for the agent.

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- Google Cloud Project with Vertex AI enabled
- Google Cloud credentials configured

### Setup (Backend)

1. Clone the repository

2. Navigate to the backend folder:
```bash
cd backend
```

3. Configure environment variables or use `local.env`.
- GOOGLE_CLOUD_PROJECT
- GOOGLE_CLOUD_LOCATION
- GOOGLE_API_KEY
- GOOGLE_APPLICATION_CREDENTIALS
- GOOGLE_CLOUD_AGENT_ENGINE_ENABLE_TELEMETRY

4. Configure application database and agent settings in application.yml (or corresponding profile):
- Set datasource properties:

    - spring.datasource.url=<MYSQL_DB_URL>
    - spring.datasource.username=<USERNAME>
    - spring.datasource.password=<PASSWORD>

- If you use VertexAiSessionService, also set: (Skip this if using InMemorySessionService.)
    - google.adk.reasoning-engine-name=<VERTEX_AI_REASONING_ENGINE_NAME>

5. Build the project using Maven:

```bash
mvn clean install
```

6. Run the Spring Boot application:

```bash
mvn spring-boot:run
```

## Custom Tools Overview

The following custom tools extend the core agent capabilities:

- `get_sessions`: Manages user sessions
- `get_ingredient_categories`: Fetches categories of ingredients
- `get_ingredient_nutritions`: Retrieves nutritional data for ingredients
- `get_ingredients`: Lists available ingredients
- `get_nutritions`: General nutrition data retrieval
- `get_recipe_groups`: Organizes recipes into groups
- `get_recipe_ingredients`: Lists ingredients of recipes (Calls API)
- `get_recipes`: Retrieves recipes (Calls API)
- `search_recipe`: Enables recipe search functionality

These tools are integrated into the agent to enrich conversational context and responses.

## Architecture Highlights

The following diagram shows the high-level architecture of the Smart Meal Planner:
![Smart Meal Planner Architecture](/docs/Architecture-Diagram.png)

Screenshots:
![Login](/docs/login.png)
![Start Conversation](/docs/Start-Conversation.png)
![Agent Response](/docs/Agent-Response.png)

- **Agent LLM** acts as the orchestrator for AI interactions.
- **VertexAiSessionService** handles sessions and persistent memory for better agent context evaluation.
- Secure token-based user authentication gates access, propagating tokens to necessary tool APIs.
- Logging is pervasive across the controller, tools, and callbacks to link user and session events with logs.
- The API server layer is built on top of the ADK-provided `AdkWebServer`.

## Observability

Logs include key identifiers like User ID and Session ID throughout the request lifecycle ensuring efficient debugging and monitoring.

---

This project showcases the integration of Google ADK with Spring Boot for building intelligent, stateful AI agents leveraged by cutting-edge language models like Gemini in Google Vertex AI.

## Frontend (Web App)

For details about the Smart Meal Planner web application, see the [Web App README](webapp/README.md).
