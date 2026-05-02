# `admin.html`

## 1. Purpose
This is the visual structure for the Administrator's Dashboard. It provides the HTML layout where an administrator can view all historical and active print jobs, and manage their status.

## 2. Key Concepts

### Internal CSS `<style>`
You'll notice there is a `<style>` block right inside the `<head>` of the document. While most of the site's styling comes from `layout.css` (which is imported), this specific page needs a table that is wider than the standard cards. By putting these specific styles directly into `admin.html`, it ensures they only affect this page and don't accidentally ruin the layout of the checkout or upload pages.

## 3. Code Walkthrough

1. **Header Navigation**: Contains the "PrintBloom Administrator" title and navigation links. Notice that the "Admin" link has the `class="active"`, which `layout.css` uses to make it light blue so the user knows what page they are on.
2. **Table Skeleton**:
   ```html
   <table class="admin-table">
       <thead>
           <tr>
               <th>ID</th>
               <th>Filename</th>
               <th>Cost</th>
               <th>Status</th>
               <th>Actions</th>
           </tr>
       </thead>
       <tbody id="adminBody">
           <!-- Javascript will inject rows -->
       </tbody>
   </table>
   ```
   This creates the empty headers for the data. The important part is `<tbody id="adminBody">`. This is entirely empty in the HTML file. It acts as an anchor point. When `admin.js` runs, it searches for `adminBody` and uses it as the target to inject the data it receives from the backend.
3. **Script Inclusion**: At the very bottom, `<script src="js/admin.js"></script>` connects this HTML file to its javascript brain.
