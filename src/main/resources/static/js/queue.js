// js/queue.js - Polls the server every 5 seconds for live visual updates

async function fetchQueue() {
    try {
        const response = await fetch('/print/queue');
        if (!response.ok) throw new Error('Failed to fetch queue');
        
        const queueList = await response.json();
        const container = document.getElementById('queueList');
        container.innerHTML = '';
        
        if (queueList.length === 0) {
            container.innerHTML = '<p style="color: #777;">The queue is currently empty. No pending prints!</p>';
            return;
        }
        
        queueList.forEach(item => {
            const li = document.createElement('li');
            li.className = 'queue-item';
            
            li.innerHTML = `
                <div style="display:flex; align-items:center; gap: 15px;">
                    <span class="queue-pos">#${item.queuePosition}</span>
                    <div style="text-align:left;">
                        <strong>Order #${item.orderId}</strong>
                        <div style="font-size:0.85rem; color:#666;">Waiting...</div>
                    </div>
                </div>
                <div class="queue-stats">
                    <span class="badge">${item.status}</span>
                    <div style="font-size:0.85rem; color:#888; font-weight:600; margin-top:5px;">
                        ~${item.estimatedWaitTime} min
                    </div>
                </div>
            `;
            container.appendChild(li);
        });
    } catch (error) {
        console.error('Error fetching queue:', error);
    }
}

// Fetch immediately upon load, and establish a 5 second polling interval
fetchQueue();
setInterval(fetchQueue, 5000);
