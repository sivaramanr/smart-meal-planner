"use client";

import { config } from "@/lib/config";

import type { ReactNode } from "react";
import {
  AssistantRuntimeProvider,
  useLocalRuntime,
  type ChatModelAdapter,
} from "@assistant-ui/react";

import { useState, useEffect } from "react";


const FirstModelAdapter: ChatModelAdapter = {
  async run({ messages, abortSignal }) {
    return {
      content: [
        {
          type: "text",
          text: "Loading",
        },
      ],
    };
  },
};

const createModelAdapter = (sessionId: string): ChatModelAdapter => ({
  async run({ messages, abortSignal }) {

    const lastMessage = messages[messages.length - 1];
    const parts = lastMessage?.content ?? [];

    const currentMessage =
      parts
        .filter(
          (part) => part.type === "text" || part.type === "reasoning"
        )
        .map((part) => (part as any).text)
        .join(" ") || "";

    let headers: any = { "Content-Type": "application/json" };

    const storedToken = localStorage.getItem("access_token");

    const userName = localStorage.getItem("user_name");

    if (storedToken)
    {
      headers.Authorization = "Bearer "+storedToken;
    }

    const apiRequestBody = JSON.stringify({
      appName: config.adkAppName,
      userId: userName,
      sessionId,
      newMessage: {
        role: 'user',
        parts: [
          {
            text: currentMessage
          }
        ],
      },
      streaming: false,
      stateDelta: null
    });

    // TODO replace with your own API
    const result = await fetch(config.adkAPIEndpoint, { // Your chat API
       method: "POST",
       headers,
       body: apiRequestBody,
       signal: abortSignal,
     });
    
    const data = await result.json();

    console.log(data.content);
    return {
      content: [
        {
          type: "text",
          text: data.content.parts[0].text,
        },
      ],
    };
  },
});


export function MyRuntimeProvider({
  children,
}: Readonly<{
  children: ReactNode;
}>) {

  const [sessionId, setSessionId] = useState<string | null>(null);

  useEffect(() => {
    const getSession = async () => {
      const storedSessionId = localStorage.getItem("session_id");
      if (!storedSessionId || storedSessionId == "undefined")
      {
        try {
          // 💡 Call your specific API endpoint for creating a session
          const response = await fetch(config.adkSessionEndpoint, {
            method: "POST",
          });
          
          const data = await response.json();
          
          // ASSUMPTION: Your API returns an object like { sessionId: "unique-id" }
          localStorage.setItem("session_id", data.id)
          setSessionId(data.id); 
          
        } catch (error) {
          console.error("Failed to create chat session:", error);
          // Handle error, maybe set a default ID or show an error message
        }
      } else {
        setSessionId(storedSessionId); 
      }
    };

    if (!sessionId) { // Ensure it only runs once
      getSession();
    }
  }, [sessionId]); // Dependency on sessionId to ensure fetch logic only runs when needed

  if (!sessionId) {
    const runtime = useLocalRuntime(FirstModelAdapter);
    return <AssistantRuntimeProvider runtime={runtime}>
      {children}
    </AssistantRuntimeProvider>

  }

  const myModelAdapter = createModelAdapter(sessionId);
  const runtime = useLocalRuntime(myModelAdapter);


  return (
    <AssistantRuntimeProvider runtime={runtime}>
      {children}
    </AssistantRuntimeProvider>
  );
}
