/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

(function () {
                const chatToggleButton = document.getElementById('chatToggleButton');
                const chatboxContainer = document.getElementById('chatboxContainer');
                const chatboxCloseButton = document.getElementById('chatboxCloseButton');
                const chatForm = document.getElementById('chatForm');
                const chatInput = document.getElementById('chatInput');
                const chatSendButton = document.getElementById('chatSendButton');
                const chatMessages = document.getElementById('chatboxMessages');

                function createMessageRow(msg) {
                    const isUser = msg.sender === 'user';

                    const row = document.createElement('div');
                    row.className = `message-row ${isUser ? 'user' : 'ai'}`;

                    const avatar = document.createElement('div');
                    avatar.className = 'chat-avatar';
                    avatar.innerHTML = isUser ? '<i class="fas fa-user"></i>' : '<i class="fas fa-robot"></i>';

                    const msgDiv = document.createElement('div');
                    const messageClass = isUser ? 'user-message' : 'ai-message';
                    msgDiv.className = `message ${messageClass}`;
                    msgDiv.innerHTML = msg.text.replace(/\n/g, '<br>'); // Render newlines

                    if (isUser) {
                        row.appendChild(msgDiv);
                        row.appendChild(avatar);
                    } else {
                        row.appendChild(avatar);
                        row.appendChild(msgDiv);
                    }
                    return row;
                }

                function showLoadingIndicator() {
                    chatMessages.innerHTML = '';
                    const loadingDiv = document.createElement('div');
                    loadingDiv.className = 'status-message';
                    loadingDiv.id = 'loading-indicator';
                    loadingDiv.innerHTML = `<span><i class="fas fa-spinner fa-spin"></i> Đang tải...</span>`;
                    chatMessages.appendChild(loadingDiv);
                }

                function removeLoadingIndicator() {
                    const indicator = document.getElementById('loading-indicator');
                    if (indicator)
                        indicator.remove();
                }

                function showTypingIndicator() {
                    if (document.getElementById('typing-indicator'))
                        return;
                    const typingDiv = document.createElement('div');
                    typingDiv.className = 'message-row ai'; // Use the same row structure
                    typingDiv.id = 'typing-indicator';
                    typingDiv.innerHTML = `
                <div class="chat-avatar"><i class="fas fa-robot"></i></div>
                <div class="message ai-message typing">
                    <span></span><span></span><span></span>
                </div>
            `;
                    chatMessages.appendChild(typingDiv);
                    scrollToBottom();
                }

                function removeTypingIndicator() {
                    const indicator = document.getElementById('typing-indicator');
                    if (indicator)
                        indicator.remove();
                }

                function scrollToBottom() {
                    chatMessages.scrollTop = chatMessages.scrollHeight;
                }

                function renderChatHistory(history) {
                    chatMessages.innerHTML = '';

                    if (!history || history.length === 0) {
                        const welcomeRow = createMessageRow({
                            sender: 'ai',
                            text: 'Xin chào! Hãy bắt đầu cuộc trò chuyện.'
                        });
                        chatMessages.appendChild(welcomeRow);
                        return;
                    }

                    history.forEach(msg => {
                        const messageRow = createMessageRow(msg);
                        chatMessages.appendChild(messageRow);
                    });

                    scrollToBottom();
                }


                async function loadChatHistory() {
                    showLoadingIndicator();
                    try {
                        const res = await fetch('aichat', {
                            method: 'GET',
                            headers: {'Accept': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                        });
                        if (!res.ok)
                            throw new Error(`Server error: ${res.status}`);
                        const history = await res.json();
                        removeLoadingIndicator();
                        renderChatHistory(history);
                    } catch (err) {
                        console.error('Failed to load chat history:', err);
                        removeLoadingIndicator();
                        const errorRow = createMessageRow({
                            sender: 'ai',
                            text: 'Không thể tải lịch sử trò chuyện. Vui lòng thử lại.'
                        });
                        chatMessages.appendChild(errorRow);
                    }
                }

                chatToggleButton.addEventListener('click', () => {
                    const isActive = chatboxContainer.classList.toggle('active');
                    chatToggleButton.classList.toggle('active');
                    if (isActive) {
                        loadChatHistory();
                        chatInput.focus();
                    }
                });

                chatboxCloseButton.addEventListener('click', () => {
                    chatboxContainer.classList.remove('active');
                    chatToggleButton.classList.remove('active');
                });

                chatForm.addEventListener('submit', async (e) => {
                    e.preventDefault();
                    const query = chatInput.value.trim();
                    if (!query)
                        return;

                    const userMsgRow = createMessageRow({sender: 'user', text: query});
                    chatMessages.appendChild(userMsgRow);
                    scrollToBottom();

                    const originalInput = chatInput.value;
                    chatInput.value = '';
                    chatInput.disabled = true;
                    chatSendButton.disabled = true;
                    showTypingIndicator();

                    try {
                        const res = await fetch('aichat?query=' + encodeURIComponent(originalInput), {
                            method: 'GET',
                            headers: {'Accept': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                        });

                        if (!res.ok)
                            throw new Error(`Server error: ${res.status}`);

                        removeTypingIndicator();
                        const data = await res.json();
                        renderChatHistory(data);

                    } catch (err) {
                        console.error('Chat error:', err);
                        removeTypingIndicator();
                        const errorRow = createMessageRow({
                            sender: 'ai',
                            text: 'Có lỗi xảy ra. Xin thử lại.'
                        });
                        chatMessages.appendChild(errorRow);
                    } finally {
                        chatInput.disabled = false;
                        chatSendButton.disabled = false;
                        chatInput.focus();
                        scrollToBottom();
                    }
                });

            })();
