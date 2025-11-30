export const config = {

    keycloakBaseUrl: process.env.NEXT_PUBLIC_KEYCLOAK_BASE_URL!,

    keycloakRealm: process.env.NEXT_PUBLIC_KEYCLOAK_REALM!,

    keycloakClientId: process.env.NEXT_PUBLIC_KEYCLOAK_CLIENT_ID!,

    appBaseUrl: process.env.NEXT_PUBLIC_APP_BASE_URL!,

    keycloakClientSecret: process.env.NEXT_PUBLIC_KEYCLOAK_CLIENT_SECRET!,

    adkAPIEndpoint: process.env.NEXT_PUBLIC_ADK_API_ENDPOINT!,

    adkSessionEndpoint: process.env.NEXT_PUBLIC_ADK_SESSION_ENDPOINT!,

    adkAppName: process.env.NEXT_PUBLIC_ADK_APP_NAME!,

};
