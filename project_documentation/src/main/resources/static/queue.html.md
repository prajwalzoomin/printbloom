# `queue.html`

## 1. Purpose
This HTML file acts as a real-time monitor. It's designed to be put up on a screen in a printing shop or viewed on a student's phone to see where their document is in the print queue.

## 2. Key Concepts

### The Empty List Container
```html
<ul id="queueList" class="queue-list">
    <!-- Javascript will inject queue items here dynamically -->
</ul>
```
Just like `admin.html`, this page contains almost no actual content. It provides the visual card container and an empty Unordered List (`<ul>`) with the ID `queueList`. This is the blank canvas that `queue.js` will paint on every 5 seconds.

## 3. Code Walkthrough

1. **Inline Styles**: Contains specific CSS for styling the individual queue items (making them look like floating cards) and adding a nice hover effect (`transform: translateX(5px);`).
2. **Header**: The standard navigation bar, with the "Live Queue" link highlighted.
3. **Main Container**: The glassmorphism card containing the title and the empty `ul#queueList`.
4. **Script Linking**: Links to `queue.js` at the bottom so the live polling loop can start immediately upon load.
