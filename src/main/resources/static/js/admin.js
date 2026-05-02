// js/admin.js

async function fetchAllOrders() {
    try {
        const response = await fetch('/admin/orders');
        if (!response.ok) throw new Error('Failed to fetch orders');
        
        const ordersList = await response.json();
        const tbody = document.getElementById('adminBody');
        tbody.innerHTML = '';
        
        if (ordersList.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align:center; padding: 2rem;">No orders found in history</td></tr>';
            return;
        }
        
        ordersList.forEach(order => {
            const tr = document.createElement('tr');
            
            let actions = '';
            // Allow state transitions based on backend rules
            if (order.status === 'PENDING') {
                actions += `<button class="action-btn" onclick="updateOrderStatus(${order.id}, 'PRINTING')">Set Printing</button>`;
            } else if (order.status === 'PRINTING') {
                actions += `<button class="action-btn success" onclick="updateOrderStatus(${order.id}, 'COMPLETED')">Finalize</button>`;
            }
            actions += `<button class="action-btn danger" onclick="deleteOrder(${order.id})">Drop</button>`;
            
            tr.innerHTML = `
                <td>#${order.id}</td>
                <td><small>${order.fileName}</small></td>
                <td>₹${order.cost}</td>
                <td><b>${order.status}</b></td>
                <td>${actions}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error fetching all orders:', error);
    }
}

async function updateOrderStatus(orderId, newStatus) {
    try {
        const params = new URLSearchParams({ orderId: orderId, status: newStatus });
        const response = await fetch('/admin/order/status?' + params.toString(), {
            method: 'PUT'
        });
        if (!response.ok) throw new Error('Update failed');
        // Instantly verify the change instead of waiting for the next poll cycle
        fetchAllOrders();
    } catch (error) {
        alert('Failed to update status.');
    }
}

async function deleteOrder(orderId) {
    if (!confirm('Are you absolutely sure you want to drop this order?')) return;
    try {
        const params = new URLSearchParams({ orderId: orderId });
        const response = await fetch('/admin/order?' + params.toString(), {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('Delete failed');
        fetchAllOrders();
    } catch (error) {
        alert('Failed to delete order.');
    }
}

// Initial fetch and 5 second polling loop
fetchAllOrders();
setInterval(fetchAllOrders, 5000);
