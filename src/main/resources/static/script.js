let chatId = null;

// Função para adicionar mensagens no chat
function addMessage(content, isUser) {
    const messageContainer = document.createElement('div');
    messageContainer.className = 'message';

    if (!isUser) {
        messageContainer.style.justifyContent = 'flex-start'; // Alinha mensagens do bot à esquerda
    }

    const messageBubble = document.createElement('div');
    messageBubble.className = isUser ? 'user-message' : 'bot-message';
    messageBubble.textContent = content;

    messageContainer.appendChild(messageBubble);
    document.getElementById('messages').appendChild(messageContainer);

    // Scroll para o final do chat
    const messagesDiv = document.getElementById('messages');
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}


// Função para enviar a mensagem
async function sendMessage(message) {
    addMessage(message, true);

    try {
        const url = chatId
            ? `http://localhost:8080/api/v1/customer-assistant/support?message=${encodeURIComponent(message)}&chatId=${chatId}`
            : `http://localhost:8080/api/v1/customer-assistant/support?message=${encodeURIComponent(message)}`;

        const response = await fetch(url, {
            method: 'GET', // Pode ser 'POST' dependendo da API
            headers: {
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();

        // Se for a primeira resposta, pegar o chatId
        if (!chatId && data.chatId) {
            chatId = data.chatId;
        }

        // Adicionar a resposta do bot ao chat
        addMessage(data.content, false);
    } catch (error) {
        console.error('Erro ao enviar mensagem:', error);
        addMessage('Erro ao se comunicar com o servidor.', false);
    }
}

// Configuração dos eventos de clique e de teclado
document.getElementById('send-button').addEventListener('click', () => {
    const messageInput = document.getElementById('message-input');
    const message = messageInput.value.trim();

    if (message !== '') {
        sendMessage(message);
        messageInput.value = '';
    }
});

document.getElementById('message-input').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        document.getElementById('send-button').click();
    }
});

// Iniciar a conversa com a primeira mensagem
sendMessage('Olá!');