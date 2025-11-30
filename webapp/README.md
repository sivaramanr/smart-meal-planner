
# Smart Meal Planner (Capstone Project)

A web chat application built with Next.js and assistant-ui, created as part of the Agents Intensive Capstone Project. The app provides a single thread conversational interface with Smart Meal Planner Agent. It delegates authentication to Keycloak using OpenID Connect.

**Features**
- 🧠 AI-powered chat thread UI using assistant-ui
- 🍽️ Meal-planning–focused UX with custom welcome prompts and suggestions
- 🔐 Keycloak authentication (Authorization Code flow with redirect)
- ⚛️ Next.js App Router architecture
- 🎨 Extensible UI, ready for custom theming and branding

**Tech Stack**
- Framework: Next.js (React, App Router)
- UI Library: @assistant-ui/react
- Auth Provider: Keycloak (OIDC)
- Language: TypeScript / JavaScript
- Styles: Tailwind CSS or custom CSS (depending on your setup)

**Getting Started**

Prerequisites

- Node.js (LTS)
- npm / yarn / pnpm
- A running Keycloak instance with:
    - Realm (e.g. Amrutha)
    - Public client (e.g. webapp)
    - Valid redirect URI (e.g. http://localhost:3000/chat)

Installation

```bash
# clone the repo
git clone <your-repo-url>
cd webapp

# install dependencies
npm install
# or
yarn
# or
pnpm install
```

Configuration

Update your Keycloak and app settings in the appropriate config file or page (for example):

```ts
const KEYCLOAK_BASE_URL = "https://amruthaauth.cookerp.com";
const REALM = "Amrutha";
const CLIENT_ID = "webapp";
const APP_BASE_URL = "http://localhost:3000";
```

Ensure the same redirect_uri is configured in Keycloak for your client.

Development

```bash
npm run dev
# then open http://localhost:3000
```

Production Build

```bash
npm run build
npm start
```

**How It Works**

- The chat UI is composed using assistant-ui primitives (e.g. Thread, messages, composer), which handle the chat layout and interactions.
- On login, the app redirects the user to Keycloak, performs the OIDC authorization code flow, and stores the returned access token in localStorage.
- Once authenticated, the user can interact with the AI assistant inside the chat thread, and requests are routed through the configured runtime/provider.

**Project Structure**

```text
app/
  page.tsx          # Landing / chat entry
  chat/page.tsx     # Main chat route
  MyRuntimeProvider.tsx  # Assistant runtime wiring
components/
  assistant-ui/
    thread.tsx      # Chat thread built with assistant-ui
  ui/
    ...             # Shared UI components
```
