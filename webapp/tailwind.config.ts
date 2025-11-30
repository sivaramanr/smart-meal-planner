// tailwind.config.ts (or .js)
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
    // 💡 Crucial: Include the path for assistant-ui's internal components
    // If you are using the UI components, this is often necessary:
    "./node_modules/@assistant-ui/react/dist/**/*.js",
  ],
  // ... rest of your config
}