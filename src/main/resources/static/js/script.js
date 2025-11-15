const API_BASE = 'http://localhost:8080';
let currentEventId = null;
let allFeedbacks = [];

// Load events on page load
window.onload = () => loadEvents();

// Show message
function showMessage(elementId, message, type) {
    const el = document.getElementById(elementId);
    el.textContent = message;
    el.className = `message ${type}`;
    el.classList.remove('hidden');
    setTimeout(() => el.classList.add('hidden'), 5000);
}

// Load all events
async function loadEvents() {
    try {
        const res = await fetch(`${API_BASE}/events`);
        const events = await res.json();
        
        const list = document.getElementById('eventsList');
        if (events.length === 0) {
            list.innerHTML = '<p>No events yet. Create one above!</p>';
            return;
        }
        
        list.innerHTML = events.map(e => `
            <div class="event-card" onclick="showEventDetails(${e.id})">
                <strong>${e.name}</strong><br>
                <small>${e.description || 'No description'}</small><br>
                <small>Feedback: ${e.feedbackCount} | Created: ${new Date(e.createdAt).toLocaleString()}</small>
            </div>
        `).join('');
    } catch (err) {
        document.getElementById('eventsList').innerHTML = '<p>Failed to load events</p>';
    }
}

// Add event
async function addEvent() {
    const name = document.getElementById('eventName').value.trim();
    const description = document.getElementById('eventDesc').value.trim();

    try {
        const res = await fetch(`${API_BASE}/events`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({name, description})
        });

        if (res.ok) {
            document.getElementById('eventName').value = '';
            document.getElementById('eventDesc').value = '';
            loadEvents();
            showMessage('createMessage', 'Event created successfully!', 'success');
        } else {
            const error = await res.json();
            const errorMsg = error.details?.join(', ') || error.message || 'Failed to create event';
            showMessage('createMessage', errorMsg, 'error');
        }
    } catch (err) {
        showMessage('createMessage', 'Error: ' + err.message, 'error');
    }
}

// Show event details
async function showEventDetails(eventId) {
    currentEventId = eventId;

    try {
        // Load event, summary, and feedback
        const [event, summary, feedbacks] = await Promise.all([
            fetch(`${API_BASE}/events/${eventId}`).then(r => r.json()),
            fetch(`${API_BASE}/events/${eventId}/summary`).then(r => r.json()),
            fetch(`${API_BASE}/events/${eventId}/feedback`).then(r => r.json())
        ]);

        document.getElementById('eventTitle').textContent = event.name;
        document.getElementById('eventDescription').textContent = event.description || 'No description';
        
        document.getElementById('summary').innerHTML = `
            Total: ${summary.totalFeedback} | 
            Positive: ${summary.totalPositiveFeedback} | 
            Neutral: ${summary.totalNeutralFeedback} | 
            Negative: ${summary.totalNegativeFeedback}
        `;

        allFeedbacks = feedbacks;
        displayFeedbacks(feedbacks);

        // Show details, hide events list
        document.getElementById('eventsSection').classList.add('hidden');
        document.getElementById('createSection').classList.add('hidden');
        document.getElementById('detailsSection').classList.remove('hidden');
    } catch (err) {
        showMessage('createMessage', 'Failed to load event details', 'error');
    }
}

// Display feedbacks
function displayFeedbacks(feedbacks) {
    const list = document.getElementById('feedbackList');
    
    if (feedbacks.length === 0) {
        list.innerHTML = '<p>No feedback yet</p>';
        return;
    }

    list.innerHTML = feedbacks.map(fb => `
        <div class="feedback-item ${fb.sentiment.toLowerCase()}">
            <span class="badge ${fb.sentiment.toLowerCase()}">${fb.sentiment}</span>
            <p>${fb.content}</p>
            <small>
                Scores - Positive: ${(fb.positiveScore * 100).toFixed(1)}% | 
                Neutral: ${(fb.neutralScore * 100).toFixed(1)}% | 
                Negative: ${(fb.negativeScore * 100).toFixed(1)}%
            </small><br>
            <small>${new Date(fb.createdAt).toLocaleString()}</small>
        </div>
    `).join('');
}

// Filter feedback
function filterFeedback() {
    const filter = document.getElementById('filterSentiment').value;
    const filtered = filter === 'ALL' 
        ? allFeedbacks 
        : allFeedbacks.filter(fb => fb.sentiment === filter);
    displayFeedbacks(filtered);
}

// Submit feedback
async function submitFeedback() {
    const content = document.getElementById('feedbackContent').value.trim();

    try {
        const res = await fetch(`${API_BASE}/events/${currentEventId}/feedback`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({content})
        });

        if (res.ok) {
            document.getElementById('feedbackContent').value = '';
            showEventDetails(currentEventId); // Reload
            showMessage('feedbackMessage', 'Feedback submitted successfully!', 'success');
        } else {
            const error = await res.json();
            const errorMsg = error.details?.join(', ') || error.message || 'Failed to submit feedback';
            showMessage('feedbackMessage', errorMsg, 'error');
        }
    } catch (err) {
        showMessage('feedbackMessage', 'Error: ' + err.message, 'error');
    }
}

// Back to events list
function showEventsList() {
    document.getElementById('detailsSection').classList.add('hidden');
    document.getElementById('eventsSection').classList.remove('hidden');
    document.getElementById('createSection').classList.remove('hidden');
    loadEvents();
}
