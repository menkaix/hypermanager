    const chatHistory = document.getElementById('chat-history');
    const userInput = document.getElementById('user-input');
    const sendButton = document.getElementById('send-button');


    let chatHistoryArray = [];
        
    sendButton.addEventListener('click', sendMessage);
    userInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            sendMessage();
        }
    });

    function sendMessage() {
        try {
            const userMessage = userInput.value;
            appendMessage('user', userMessage);

            axios.post(base_url+'/discuss', {
                prompt: userMessage,
                history: chatHistoryArray,
                path: gcsPath
            }, {
                headers: {
                    'x-api-key': api_key
                }
            })
            .then(response => {
                const botMessage = response.data.message;
                chatHistoryArray = response.data.history;
                appendMessage('bot', botMessage);
            })
            .catch(error => {
                console.error('Error:', error);
                appendMessage('bot', 'An error occurred. Please try again later.');
            });
            userInput.value = '';
        } catch (error) {
            console.error('Error sending message:', error);
            appendMessage('bot', 'An unexpected error occurred. Please try again later.');
        }
    }

    function appendMessage(sender, message) {
        try {
            const messageDiv = document.createElement('div');
            messageDiv.classList.add('message', `${sender}-message`);

            if (typeof message === 'object' && message !== null) {
                const formattedContent = marked.parse(message.content);
                const decodedContent = formattedContent.replace(/\\u([\d\w]{4})/gi, (match, grp) => {
                    return String.fromCharCode(parseInt(grp, 16));
                });
                messageDiv.innerHTML = `${message.role}: ${decodedContent}`;
            } else {
                messageDiv.textContent = message; 
            }

            if (sender === 'user') {
                messageDiv.style.color = '#2196f3';
                messageDiv.style.textAlign = 'right';
                messageDiv.style.backgroundColor = '#e3f2fd';
                messageDiv.style.padding = '8px 16px';
                messageDiv.style.borderRadius = '4px';
                messageDiv.style.marginBottom = '8px';
            } else {
                messageDiv.style.backgroundColor = '#f5f5f5';
                messageDiv.style.padding = '8px 16px';
                messageDiv.style.borderRadius = '4px';
                messageDiv.style.marginBottom = '8px';
            }

            chatHistory.appendChild(messageDiv);
            chatHistory.scrollTop = chatHistory.scrollHeight;
        } catch (error) {
            console.error('Error appending message:', error);
        }
    }