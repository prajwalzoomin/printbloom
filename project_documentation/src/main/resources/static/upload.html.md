# `upload.html`

## 1. Purpose
This is the starting point of the user journey. It provides the interface for users to submit a PDF and define exactly how they want it printed.

## 2. Key Concepts

### `<input type="file">`
```html
<input type="file" id="pdfFile" class="drop-zone__input" accept="application/pdf" required>
```
This is the standard HTML element used for uploading files. We add `accept="application/pdf"` to physically prevent the user from selecting images or Word documents from their computer, ensuring only PDFs get sent to our backend.

### Form Grouping
The form uses `display: flex; gap: 1rem;` to put the "Number of Copies" input and the "Print Sides" dropdown side-by-side on the same row, making the UI look modern and compact.

## 3. Code Walkthrough

1. **The Drag & Drop Zone**:
   Visually, this looks like a large dashed box. The actual `<input type="file">` is hidden by CSS. When a user drops a file onto the box, `upload.js` intercepts it and passes the file to the hidden input securely.
2. **Settings Inputs**:
   - A `<select>` dropdown for Print Color Strategy (Black/White or Color).
   - A `<input type="number">` for copies (restricted to a minimum of 1).
   - A `<select>` dropdown for Duplex (Single or Double Sided).
3. **Hidden UI Elements**:
   At the bottom, there is a `loadingIndicator` (the spinning circle) and an `errorMessage` box. Both have the `.hidden` CSS class applied initially. When the user clicks submit, `upload.js` removes the hidden class from the spinner while it waits for the server, and uses the error box if something goes wrong.
