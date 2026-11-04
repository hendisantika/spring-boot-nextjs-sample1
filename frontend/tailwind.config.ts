import type {Config} from "tailwindcss";

export default {
    content: [
        "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
        "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
        "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
    ],
    theme: {
        extend: {
            colors: {
                paper: "#faf7f2",
                ink: "#1c1917",
                accent: {
                    50: "#fff4ed",
                    100: "#ffe6d4",
                    500: "#e8590c",
                    600: "#c2410c",
                    700: "#9a3412",
                },
            },
            fontFamily: {
                sans: ["var(--font-sans)", "ui-sans-serif", "system-ui", "sans-serif"],
                display: ["var(--font-display)", "ui-serif", "Georgia", "serif"],
            },
            boxShadow: {
                card: "0 1px 2px rgba(28,25,23,0.04), 0 4px 16px -4px rgba(28,25,23,0.08)",
            },
        },
    },
    plugins: [],
} satisfies Config;
