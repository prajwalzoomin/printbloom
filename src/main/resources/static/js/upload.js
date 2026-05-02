// Setup Drag and Drop events natively without external libraries
document.querySelectorAll(".drop-zone__input").forEach(inputElement => {
    const dropZoneElement = inputElement.closest(".drop-zone");

    dropZoneElement.addEventListener("click", e => {
        inputElement.click();
    });

    inputElement.addEventListener("change", e => {
        if (inputElement.files.length) {
            updateThumbnail(dropZoneElement, inputElement.files[0]);
        }
    });

    dropZoneElement.addEventListener("dragover", e => {
        e.preventDefault();
        dropZoneElement.classList.add("drop-zone--over");
    });

    ["dragleave", "dragend"].forEach(type => {
        dropZoneElement.addEventListener(type, e => {
            dropZoneElement.classList.remove("drop-zone--over");
        });
    });

    dropZoneElement.addEventListener("drop", e => {
        e.preventDefault();
        if (e.dataTransfer.files.length) {
            inputElement.files = e.dataTransfer.files;
            updateThumbnail(dropZoneElement, e.dataTransfer.files[0]);
        }
        dropZoneElement.classList.remove("drop-zone--over");
    });
});

function updateThumbnail(dropZoneElement, file) {
    let prompt = dropZoneElement.querySelector(".drop-zone__prompt");
    if (prompt) {
        prompt.textContent = `Selected File: ${file.name}`;
        prompt.style.color = "#0056b3";
    }
}

// Global Form Submission Event
document.getElementById("uploadForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const fileInput = document.getElementById("pdfFile");
    const printType = document.getElementById("printType").value;
    const copies = document.getElementById("copies").value;
    const isDuplex = document.getElementById("isDuplex").value;
    const submitBtn = document.getElementById("submitBtn");
    const loadingIndicator = document.getElementById("loadingIndicator");
    const errorMessage = document.getElementById("errorMessage");

    if (fileInput.files.length === 0) {
        errorMessage.textContent = "Please select a PDF document first.";
        errorMessage.classList.remove("hidden");
        return;
    }

    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append("file", file);
    formData.append("printType", printType);
    formData.append("copies", copies);
    formData.append("isDuplex", isDuplex);

    // Freeze UI
    submitBtn.disabled = true;
    loadingIndicator.classList.remove("hidden");
    errorMessage.classList.add("hidden");
    submitBtn.style.display = "none";

    try {
        const response = await fetch("/print/order", {
            method: "POST",
            body: formData
        });

        if (!response.ok) {
            const errText = await response.text();
            throw new Error(errText || "Order generation failed securely on server");
        }

        const data = await response.json();

        // Save session details with the newly generated Active Order ID
        sessionStorage.setItem("printbloom_fileName", file.name);
        sessionStorage.setItem("printbloom_printType", printType);
        sessionStorage.setItem("printbloom_copies", data.copies || copies);
        sessionStorage.setItem("printbloom_isDuplex", data.isDuplex !== undefined ? data.isDuplex : isDuplex);
        sessionStorage.setItem("printbloom_orderId", data.orderId);
        sessionStorage.setItem("printbloom_estimatedCost", data.cost);

        // Redirect seamlessly
        window.location.href = "checkout.html";

    } catch (error) {
        console.error("Upload error:", error);
        errorMessage.textContent = error.message;
        errorMessage.classList.remove("hidden");
        
        // Restore UI
        submitBtn.disabled = false;
        submitBtn.style.display = "block";
        loadingIndicator.classList.add("hidden");
    }
});
