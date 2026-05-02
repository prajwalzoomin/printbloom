# `layout.css`

## 1. Purpose
This file contains the modern, aesthetic styling for the PrintBloom frontend. While HTML provides the skeleton of a web page, CSS (Cascading Style Sheets) provides the skin, clothing, and makeup. This specific file implements a "Glassmorphism" design trend.

## 2. Key Concepts

### CSS Variables & External Fonts
```css
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap');
body { font-family: 'Inter', sans-serif; }
```
This imports a professional-looking font directly from Google Fonts and applies it to the entire body of the webpage, replacing the ugly browser default font.

### Glassmorphism (`.glass-card`)
```css
.glass-card {
    background: rgba(255, 255, 255, 0.65);
    backdrop-filter: blur(16px);
    border-radius: 24px;
}
```
This is the core of the modern design. By using a semi-transparent white background (`rgba`) combined with a `backdrop-filter: blur()`, it creates a frosted glass effect that looks highly premium.

### Flexbox (`display: flex`)
You'll see `display: flex` used heavily in `header` and `.upload-container`. Flexbox is a modern CSS layout system that makes it incredibly easy to align items side-by-side and center them perfectly on the screen, something that used to be very difficult in older CSS.

### Micro-Animations
```css
.primary-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(0, 123, 255, 0.3);
}
```
When a user hovers their mouse over a primary button, the button slightly lifts up (`translateY`) and casts a glowing shadow. These tiny micro-animations make the website feel alive and responsive.
