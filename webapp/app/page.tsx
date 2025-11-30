"use client";

import { config } from "@/lib/config";

import { useEffect, useState } from "react";
import { Thread } from "@/components/assistant-ui/thread";
import { MyRuntimeProvider } from "@/app/MyRuntimeProvider";

export default function Login() {

  const [accessToken, setAccessToken] = useState<string | null>(null);

  useEffect(() => {
    const storedToken = localStorage.getItem("access_token");
    if (storedToken) {
      setAccessToken(storedToken);
      return;
    }

    const url = new URL(window.location.href);
    const code = url.searchParams.get("code");
    if (!code) return;

    const exchangeCodeForToken = async () => {
      try {
        const tokenUrl = `${config.keycloakBaseUrl}/realms/${encodeURIComponent(
          config.keycloakRealm
        )}/protocol/openid-connect/token`;

        const body = new URLSearchParams({
          grant_type: "authorization_code",
          code,
          redirect_uri: `${config.appBaseUrl}/`,
          client_id: config.keycloakClientId,
          client_secret: config.keycloakClientSecret,
        });

        const res = await fetch(tokenUrl, {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: body.toString(),
        });

        if (!res.ok) {
          console.error("Token request failed", await res.text());
          return;
        }

        const data = await res.json();
        const token = data.access_token as string;
        if (token) {
          localStorage.setItem("access_token", token);
          setAccessToken(token);

          // fetch user info
          const userInfoRes = await fetch(
            `${config.keycloakBaseUrl}/realms/${encodeURIComponent(
              config.keycloakRealm
            )}/protocol/openid-connect/userinfo`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );

          if (userInfoRes.ok) {
            const userInfo = await userInfoRes.json();
            // store wherever you need
            localStorage.setItem("user_name", userInfo.email);
            localStorage.setItem("display_name", userInfo.given_name);
            window.dispatchEvent(new CustomEvent("display_name_changed", { detail: userInfo.given_name }));
          }          
          window.history.replaceState({}, "", "");
        }
      } catch (e) {
        console.log(e)
      }
    };

    exchangeCodeForToken();
  }, []);

  const handleLoginClick = () => {
    const redirectUri = encodeURIComponent(`${config.appBaseUrl}/`);
    const authUrl = `${config.keycloakBaseUrl}/realms/${encodeURIComponent(
      config.keycloakRealm
    )}/protocol/openid-connect/auth?client_id=${encodeURIComponent(
      config.keycloakClientId
    )}&response_type=code&scope=openid&redirect_uri=${redirectUri}`;
    window.location.href = authUrl;
  };

  const handleLogout = () => {
    // Clear local token and show Login again
    localStorage.removeItem("access_token");
    localStorage.removeItem("session_id");
    localStorage.removeItem("user_name");
    localStorage.removeItem("display_name");
    setAccessToken(null);
    const logoutUrl = `${config.keycloakBaseUrl}/realms/${encodeURIComponent(
      config.keycloakRealm
    )}/protocol/openid-connect/logout?client_id=${encodeURIComponent(
      config.keycloakClientId
    )}&post_logout_redirect_uri=${encodeURIComponent(`${config.appBaseUrl}/`)}`;
    window.location.href = logoutUrl;
  };

  const buttonStyle: React.CSSProperties = {
    padding: "0.75rem 1.5rem",
    borderRadius: "0.5rem",
    border: "none",
    backgroundColor: "#2563eb",
    color: "white",
    fontSize: "1rem",
    cursor: "pointer",
    margin: "0 0.5rem",
  };

    return (
      <>
        {!accessToken && (
          <div
            style={{
              minHeight: "100vh",
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              alignItems: "center",
              fontFamily: "system-ui, -apple-system, BlinkMacSystemFont, sans-serif",
            }}
          >
            <span style={{ fontSize: "1.25rem", fontWeight: 600, marginBottom: "1rem" }}>
              Demo - Smart Meal Planner
            </span>
            <button onClick={handleLoginClick} style={buttonStyle}>
              Login
            </button>
          </div>
        )}
        {
          accessToken && (
          <div className="h-screen flex flex-col">
            <div className="flex-1 overflow-hidden relative">
              <MyRuntimeProvider>
                <Thread />
              </MyRuntimeProvider>

              {/* bottom-left logout, replacing the N bubble */}
              <button
                onClick={accessToken ? handleLogout : handleLoginClick}
                className={
                  `fixed bottom-4 left-4 rounded-full px-4 py-2 text-sm shadow text-white ` +
                  (accessToken ? "bg-red-500" : "bg-blue-600")
                }
              >
                {accessToken? 'Logout': 'Login'}
              </button>
            </div>
          </div>
        )}
      </>
    );

}
