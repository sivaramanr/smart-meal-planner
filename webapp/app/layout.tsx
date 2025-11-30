import "./globals.css";
import type { ReactNode } from "react";

export default function RootLayout({
  children,
}: Readonly<{
  children: ReactNode;
}>) {
  return (
      <html lang="en">
        <head>
          <title>Smart Meal Planner - Agents Intensive - Capstone Project</title>
        </head>
        <body>{children}</body>
      </html>
  );
}
