# `upload.js`

## 1. Purpose
This file manages the first screen the user sees. It handles the interactive "Drag and Drop" file upload zone, collects the user's printing preferences (Color, Duplex, Copies), and submits the file to the backend to generate the initial order and cost estimate.

## 2. Key Concepts

### Event Listeners
JavaScript is an "event-driven" language. It spends most of its time waiting for things to happen. Code like `element.addEventListener('click', ...)` tells JavaScript: *"Go to sleep, but the millisecond the user clicks this specific button, wake up and run this block of code."*

### `FormData`
Normally, when you send JSON to a server, it's just text. But when you need to send a physical file (like a PDF), text isn't enough. The `FormData` object is a special browser feature that packages up binary files and text fields together into a format called `multipart/form-data` so the server can read the file correctly.

## 3. Code Walkthrough

1. **Drag and Drop Zone**:
   - The first chunk of code creates the visual drag-and-drop effect.
   - When a file is dragged over the zone (`dragover`), it adds a CSS class (`drop-zone--over`) to make the box highlight or bounce.
   - When the file is dropped (`drop`), it catches the file data from the operating system and updates a text label to show the file name.

2. **Form Submission (`uploadForm`)**:
   - Attached to the "Upload and Calculate Cost" button.
   - `e.preventDefault()` stops the browser from doing its default behavior (which is to reload the whole page).
   - It gathers the file, print type, copies, and duplex settings.
   - It packages them into a `FormData` object.
   - **UI Freeze**: It disables the submit button and shows a loading spinner so the user knows something is happening and doesn't click the button 10 times.
   - It `fetch`es the `/print/order` API endpoint (triggering the `PrintOrderService` on the backend).
   - **Session Storage**: If successful, the backend returns the new Order ID and calculated cost. This JavaScript takes those values and carefully places them into `sessionStorage`.
   - Finally, it redirects the browser to `checkout.html` using `window.location.href`, where `checkout.js` will read those saved values and continue the process.
