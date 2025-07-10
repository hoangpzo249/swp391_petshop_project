<%-- 
    Document   : home_page
    Created on : 19 May 2025, 11:13:48 pm
    Author     : HuyHoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/header_footer.css" rel="stylesheet" type="text/css"/>
        <link href="css/home_page.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <%-- START: IMPROVED CSS FOR CHATBOX --%>
        <style>
            :root {
                --brand-color: #f26f21;
                --brand-color-dark: #e0621a;
                --brand-color-light: #f9bda0;
                --text-light: #ffffff;
                --text-dark: #333333;
                --bg-light: #f5f7fa;
                --border-color: #e5e7eb;
            }

            /* --- Toggle Button --- */
            .chat-toggle-button {
                position: fixed;
                bottom: 30px;
                right: 30px;
                background-color: var(--brand-color);
                color: var(--text-light);
                border: none;
                border-radius: 50%;
                width: 60px;
                height: 60px;
                font-size: 28px;
                display: flex;
                justify-content: center;
                align-items: center;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                cursor: pointer;
                z-index: 1000;
                transition: transform 0.2s ease, background-color 0.2s ease, box-shadow 0.2s ease;
            }

            .chat-toggle-button:hover {
                transform: scale(1.1);
                background-color: var(--brand-color-dark);
                box-shadow: 0 6px 16px rgba(0, 0, 0, 0.2);
            }

            .chat-toggle-button.active {
                transform: rotate(90deg);
            }

            /* --- Main Chatbox Container --- */
            .chatbox-container {
                position: fixed;
                bottom: 110px;
                right: 30px;
                width: 380px;
                max-height: 75vh;
                background: var(--text-light);
                border-radius: 15px;
                box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
                display: flex; /* Changed from none to flex */
                flex-direction: column;
                z-index: 1001;
                overflow: hidden;
                font-family: 'Asap', sans-serif;

                /* Visibility controlled by opacity and transform */
                opacity: 0;
                transform: translateY(20px);
                pointer-events: none;
                transition: opacity 0.3s ease-out, transform 0.3s ease-out;
            }

            .chatbox-container.active {
                opacity: 1;
                transform: translateY(0);
                pointer-events: auto;
            }

            /* --- Chatbox Header --- */
            .chatbox-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 15px 20px;
                background-color: var(--brand-color);
                color: var(--text-light);
                flex-shrink: 0;
            }

            .chatbox-header h3 {
                margin: 0;
                font-size: 1.1rem;
                font-weight: 600;
            }

            .chatbox-close-button {
                background: none;
                border: none;
                color: var(--text-light);
                font-size: 24px;
                cursor: pointer;
                padding: 0;
                line-height: 1;
                opacity: 0.8;
                transition: opacity 0.2s ease;
            }
            .chatbox-close-button:hover {
                opacity: 1;
            }

            /* --- Messages Area --- */
            .chatbox-messages {
                flex-grow: 1;
                padding: 20px;
                overflow-y: auto;
                display: flex;
                flex-direction: column;
                gap: 18px;
                background-color: var(--bg-light);
                scroll-behavior: smooth;
            }

            .message-row {
                display: flex;
                align-items: flex-end; /* Aligns avatar and bubble bottom */
                gap: 10px;
                max-width: 90%;
            }

            .message-row.user {
                justify-content: flex-end;
                align-self: flex-end;
            }

            .message-row.ai {
                align-self: flex-start;
            }

            .chat-avatar {
                width: 32px;
                height: 32px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-shrink: 0;
                font-size: 16px;
                background-color: #ccc;
            }

            .message-row.user .chat-avatar {
                background-color: var(--brand-color);
                color: var(--text-light);
            }

            .message-row.ai .chat-avatar {
                background-color: #d1d5db;
                color: #4b5563;
            }

            .message {
                padding: 10px 15px;
                border-radius: 18px;
                line-height: 1.5;
                word-wrap: break-word;
                animation: message-fade-in 0.3s ease;
            }

            @keyframes message-fade-in {
                from {
                    opacity: 0;
                    transform: translateY(10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .user-message {
                background-color: var(--brand-color);
                color: var(--text-light);
                border-bottom-right-radius: 5px;
            }

            .ai-message {
                background-color: var(--text-light);
                color: var(--text-dark);
                border: 1px solid var(--border-color);
                border-bottom-left-radius: 5px;
            }

            .ai-message.typing {
                display: flex;
                align-items: center;
                gap: 5px;
                padding: 12px 15px;
            }

            .ai-message.typing span {
                height: 8px;
                width: 8px;
                border-radius: 50%;
                background-color: #b0b0b0;
                animation: typing-blink 1.4s infinite both;
            }
            .ai-message.typing span:nth-child(2) {
                animation-delay: 0.2s;
            }
            .ai-message.typing span:nth-child(3) {
                animation-delay: 0.4s;
            }

            @keyframes typing-blink {
                0% {
                    opacity: 0.2;
                }
                20% {
                    opacity: 1;
                }
                100% {
                    opacity: 0.2;
                }
            }

            .status-message {
                text-align: center;
                color: #888;
                padding: 10px;
            }

            .chatbox-input-area {
                padding: 15px 20px;
                border-top: 1px solid var(--border-color);
                background-color: #fff;
                flex-shrink: 0;
            }

            #chatForm {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            #chatInput {
                flex-grow: 1;
                border: 1px solid #ccc;
                border-radius: 20px;
                padding: 10px 18px;
                font-size: 14px;
                outline: none;
                transition: border-color 0.2s, box-shadow 0.2s;
            }

            #chatInput:focus {
                border-color: var(--brand-color);
                box-shadow: 0 0 0 2px var(--brand-color-light);
            }

            #chatForm button {
                background: var(--brand-color);
                color: var(--text-light);
                border: none;
                border-radius: 50%;
                width: 42px;
                height: 42px;
                cursor: pointer;
                font-size: 16px;
                flex-shrink: 0;
                display: flex;
                align-items: center;
                justify-content: center;
                transition: background-color 0.2s ease;
            }

            #chatForm button:hover {
                background-color: var(--brand-color-dark);
            }

            #chatForm button:disabled {
                background-color: var(--brand-color-light);
                cursor: not-allowed;
            }
        </style>
    </head>
    <body>
        <!-- header -->
        <div class="header">
            <div class="header1">

                <div>
                    <a href="homepage">
                        <img src="images/logo_banner/logo2.png" alt=""/>
                    </a>
                </div>

                <form action="listshoppet" method="get">

                    <input type="hidden" name="species" value="${param.species}" />
                    <input type="hidden" name="breed" value="${param.breed}" />
                    <input type="hidden" name="gender" value="${param.gender}" />
                    <input type="hidden" name="color" value="${param.color}" />
                    <input type="hidden" name="origin" value="${param.origin}" />
                    <input type="hidden" name="dobFrom" value="${param.dobFrom}" />
                    <input type="hidden" name="dobTo" value="${param.dobTo}" />
                    <input type="hidden" name="priceRange" value="${param.priceRange}" />
                    <input type="hidden" name="vaccination" value="${param.vaccination}" />
                    <div class="search">
                        <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm thú cưng ..." />
                        <button type="submit" class="search-button">
                            <img src="images/support button/search.png" width="20" height="20" alt="search" />
                        </button>
                    </div>
                </form>

                <div class="accountcard">
                    <c:choose>
                        <c:when test="${sessionScope.userAccount != null}">
                            <div class="account-dropdown">
                                <a href="#" class="account-trigger">
                                    <img src="images/support button/account.png" width="50" height="50" alt="account"/>
                                    <p class="username">Tài khoản</p>
                                </a>
                                <div class="dropdown-content">
                                    <c:choose>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Admin'}">
                                            <a href="admin-panel" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Admin</span>
                                            </a>
                                        </c:when>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Seller'}">
                                            <a href="displaysalesstatistic" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Seller</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Manager'}">
                                            <a href="displaybreed" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Manager</span>
                                            </a>
                                        </c:when>

                                        <c:when test="${sessionScope.userAccount.accRole eq 'Shipper'}">
                                            <a href="shipper_panel" class="dropdown-item">
                                                <i class="fas fa-user"></i> 
                                                <span>Quản lý Shipper</span>
                                            </a>
                                        </c:when>

                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="profile" class="dropdown-item">
                                        <i class="fas fa-user"></i> 
                                        <span>Thông tin cá nhân</span>
                                    </a>
                                    <c:choose>
                                        <c:when test="${sessionScope.userAccount.accRole eq 'Customer'}">
                                            <a href="orders?status=Pending" class="dropdown-item">
                                                <i class="fas fa-shopping-bag"></i> 
                                                <span>Đơn hàng đã mua</span>
                                            </a>
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <a href="logout" class="dropdown-item logout">
                                        <i class="fas fa-sign-out-alt"></i> 
                                        <span>Đăng xuất</span>
                                    </a>
                                </div>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <div class="account">
                                <a href="login">
                                    <img src="images/support button/account.png" width="50" height="50" alt="account"/>
                                    <p class="logintext">Đăng nhập</p>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${empty sessionScope.userAccount or sessionScope.userAccount.accRole eq 'Customer'}">

                        <div class="card">
                            <a href="displaycart">
                                <img src="images/card.png" width="50" height="50" alt="card"/>
                                <p class="cardtext">
                                    Giỏ Hàng
                                    <span id="cart-count" class="cartcount">${sessionScope.cartcount}</span>
                                </p>
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
            <div>
                <nav>
                    <ul class="menu">
                        <li><a href="homepage">Trang Chủ</a></li>
                        <li><a href="listshoppet">Thú Cưng</a></li>
                        <li><a href="menu_about.jsp">Giới Thiệu</a></li>
                        <li><a href="menu_contact.jsp">Liên Hệ</a></li>
                    </ul>
                </nav>
            </div>
        </div>

        <c:if test="${not empty loginSuccess}">
            <div class="login-success-alert">
                ${loginSuccess}
            </div>
            <c:remove var="loginSuccess" scope="session" />
        </c:if>
        <c:if test="${not empty successMess}">
            <div class="alert-message">${successMess}</div>
            <c:remove var="successMess" scope="session" />
        </c:if>
        <c:if test="${not empty errMess}">
            <div class="alert-message error">${errMess}</div>
            <c:remove var="errMess" scope="session" />
        </c:if>

        <!-- banner -->
        <div class="banner">
            <a href="homepage">
                <img src="images/logo_banner/banner.png" alt=""/>
            </a>
        </div>

        <div class="box">

            <!-- dog-product -->
            <h2 class="text-item">Thú Cưng Nổi Bật</h2>
            <div class="view-all-item">
                <a href="listshoppet?species=&sort=popular">Xem Tất Cả</a>
            </div>

            <div class="detail-item-product">
                <c:forEach items="${listBreed}" var="b">
                    <div class="detail-item">
                        <a href="listshoppet?species=${b.breedSpecies}&breed=${b.breedId}&gender=&color=&origin=&priceRange=&dobFrom=&dobTo=&vaccination=">
                            <img src="${b.displayBreedImage()}" width="800" height="800" alt="${b.breedName}"/>
                            <p class="item-name">${b.breedName}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <!-- cat-product -->

            <h2 class="text-item">Thành Viên Mới</h2>
            <div class="view-all-item">
                <a href="listshoppet">Xem Tất Cả</a>
            </div>

            <div class="detail-item-product">
                <c:forEach items="${listPet}" var="x">
                    <div class="detail-item">
                        <a href="displaypet?id=${x.petId}">
                            <img src="${x.getFirstImage()}" alt="${x.petName}" class="main-image" />
                            <p class="item-name">${x.petName}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <h2 class="text-favo-item">Boss nào hợp với bạn?</h2>
            <div class="pet-match-section">
                <div class="pet-options-container">
                    <a href="listshoppet?species=Mèo&sort=popular" class="pet-option">
                        <div class="pet-image">
                            <img src="images/cat.png" alt="Mèo" class="pet-img">
                        </div>
                        <span class="pet-name">Mèo</span>
                    </a>

                    <a href="listshoppet?species=Chó&sort=popular" class="pet-option">
                        <div class="pet-image">
                            <img src="images/dog.png" alt="Chó" class="pet-img">
                        </div>
                        <span class="pet-name">Chó</span>
                    </a>
                </div>
            </div>
        </div>

        <div class="about-section">
            <div class="about-column newsletter-column">
                <h3>Shop</h3>
                <ul>
                    <li><a href="">Dành Cho Chó</a></li>
                    <li><a href="">Dành Cho Mèo</a></li>
                    <li><a href="">Dành Cho Chuột</a></li>
                </ul>

                <div class="newsletter-block">
                    <h3>Đăng Ký Nhận Tin</h3>
                    <p>Cập nhật thông tin về thú cưng và ưu đãi hấp dẫn</p>
                    <div class="newsletter-form">
                        <form action="" method="post">
                            <div class="newsletter-input-container">
                                <input type="email" name="email" placeholder="Email của bạn" required>
                                <button type="submit"><i class="fas fa-paper-plane"></i></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="about-column">
                <h3>PETFPT Shop</h3>
                <ul>
                    <li><a href="">Giới Thiệu</a></li>
                    <li><a href="">Chính sách</a></li>
                    <li><a href="">Phương Thức Thanh Toán</a></li>
                    <li><a href="">Điều Khoản Sử Dụng</a></li>                    
                </ul>
            </div>

            <div class="about-column">
                <h3>Liên Hệ</h3>
                <p class="contactpet">PETFPT Shop</p>
                <p><i class="fas fa-phone"></i>0767676770</p>
                <p><i class="fas fa-map-marker-alt"></i>Khu Công nghệ cao Hòa Lạc <br>
                    Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>
                <p><i class="fas fa-envelope email"></i>
                    <a href="mailto:hoangnhhe181051@fpt.edu.vn">petfpt@gmail.com</a>
                </p>
                <div class="social-container">
                    <a href="https://www.facebook.com/petfptshop" target="_blank" class="social-icon">
                        <i class="fab fa-facebook fa-2x"></i>
                    </a>
                </div>
            </div>
        </div>
        <footer>
            © 2025 FPTPet - Đồng hành cùng bạn và thú cưng mỗi ngày!
        </footer>

        <button class="chat-toggle-button" id="chatToggleButton" aria-label="Toggle AI Assistant Chat">
            <i class="fas fa-comments"></i>
        </button>

        <div class="chatbox-container" id="chatboxContainer">
            <div class="chatbox-header">
                <h3>PetFPT</h3>
                <button class="chatbox-close-button" id="chatboxCloseButton" aria-label="Close Chat">×</button>
            </div>
            <div class="chatbox-messages" id="chatboxMessages">
            </div>
            <div class="chatbox-input-area">
                <form id="chatForm">
                    <input type="text" id="chatInput" placeholder="Ask me anything..." autocomplete="off" required>
                    <button type="submit" id="chatSendButton" aria-label="Send Message"><i class="fas fa-paper-plane"></i></button>
                </form>
            </div>
        </div>

        <script>
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
        </script>
    </body>
</html>