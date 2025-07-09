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

        <%-- START: ADDED CSS FOR CHATBOX --%>
        <style>
            .chat-toggle-button {
                position: fixed;
                bottom: 30px;
                right: 30px;
                background-color: #f26f21;
                color: white;
                border: none;
                border-radius: 50%;
                width: 60px;
                height: 60px;
                font-size: 28px;
                display: flex;
                justify-content: center;
                align-items: center;
                box-shadow: 0 4px 10px rgba(0,0,0,0.2);
                cursor: pointer;
                z-index: 1000;
                transition: transform 0.2s ease, background-color 0.2s ease;
            }

            .chat-toggle-button:hover {
                transform: scale(1.1);
                background-color: #e0621a;
            }

            .chatbox-container {
                position: fixed;
                bottom: 100px;
                right: 30px;
                width: 370px;
                max-height: 70vh;
                background: #ffffff;
                border-radius: 15px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.3);
                display: none; /* Initially hidden */
                flex-direction: column;
                z-index: 1001;
                overflow: hidden;
                font-family: 'Asap', sans-serif;
                animation: slideUp 0.3s ease-out;
            }

            @keyframes slideUp {
                from {
                    transform: translateY(20px);
                    opacity: 0;
                }
                to {
                    transform: translateY(0);
                    opacity: 1;
                }
            }

            .chatbox-container.active {
                display: flex;
            }

            .chatbox-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 12px 20px;
                background-color: #f26f21;
                color: white;
                border-top-left-radius: 15px;
                border-top-right-radius: 15px;
            }

            .chatbox-header h3 {
                margin: 0;
                font-size: 18px;
            }

            .chatbox-close-button {
                background: none;
                border: none;
                color: white;
                font-size: 24px;
                cursor: pointer;
                padding: 0;
                line-height: 1;
            }

            .chatbox-messages {
                flex-grow: 1;
                padding: 20px;
                overflow-y: auto;
                display: flex;
                flex-direction: column;
                gap: 12px;
                background-color: #f9f9f9;
            }

            .message {
                padding: 10px 15px;
                border-radius: 20px;
                max-width: 85%;
                line-height: 1.4;
                word-wrap: break-word;
            }

            .user-message {
                align-self: flex-end;
                background-color: #f26f21;
                color: white;
                border-bottom-right-radius: 5px;
            }

            .ai-message {
                align-self: flex-start;
                background-color: #e9e9eb;
                color: #333;
                border-bottom-left-radius: 5px;
            }

            .ai-typing {
                align-self: flex-start;
                color: #888;
                font-style: italic;
            }

            .chatbox-input {
                padding: 15px 20px;
                border-top: 1px solid #ddd;
                background-color: #fff;
            }

            #chatForm {
                display: flex;
                gap: 10px;
            }

            #chatInput {
                flex-grow: 1;
                border: 1px solid #ccc;
                border-radius: 20px;
                padding: 10px 15px;
                font-size: 14px;
                outline: none;
            }

            #chatInput:focus {
                border-color: #f26f21;
            }

            #chatForm button {
                background: #f26f21;
                color: white;
                border: none;
                border-radius: 50%;
                width: 42px;
                height: 42px;
                cursor: pointer;
                font-size: 16px;
                flex-shrink: 0;
                transition: background-color 0.2s ease;
            }

            #chatForm button:hover {
                background-color: #e0621a;
            }

            #chatForm button:disabled {
                background-color: #f9bda0;
                cursor: not-allowed;
            }
        </style>
        <%-- END: ADDED CSS FOR CHATBOX --%>
    </head>
    <body>
        <!-- header -->
        <div class="header">
            <%-- (Your existing header content remains here) --%>
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

        <%-- (The rest of your page body content remains here) --%>

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

        <%-- (Your footer section remains here) --%>
        <div class="about-section">
            <div class="about-column newsletter-column">
                <h3>Shop</h3>
                <ul>
                    <li><a href="">Dành Cho Chó</a></li>
                    <li><a href="">Dành Cho Mèo</a></li>
                    <li><a href="">Dành Cho Chuột</a></li>
                </ul>

                <!-- Phần đăng ký email được gộp vào đây -->
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

        <button class="chat-toggle-button" id="chatToggleButton">
            <i class="fas fa-comments"></i>
        </button>

        <div class="chatbox-container" id="chatboxContainer">
            <div class="chatbox-header">
                <h3>AI Assistant</h3>
                <button class="chatbox-close-button" id="chatboxCloseButton">×</button>
            </div>
            <div class="chatbox-messages" id="chatboxMessages">
                <%-- Messages will be dynamically inserted here by JavaScript --%>
            </div>
            <div class="chatbox-input">
                <form id="chatForm">
                    <input type="text" id="chatInput" placeholder="Ask me anything..." autocomplete="off" required>
                    <button type="submit" id="chatSendButton"><i class="fas fa-paper-plane"></i></button>
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

                function initializeChat() {
                    if (chatMessages.children.length === 0) {
                        const welcomeDiv = document.createElement('div');
                        welcomeDiv.className = 'message ai-message';
                        welcomeDiv.textContent = 'Xin chào! Tôi có thể giúp gì bạn ngày hôm nay?';
                        chatMessages.appendChild(welcomeDiv);
                    }
                }

                chatToggleButton.addEventListener('click', () => {
                    chatboxContainer.classList.toggle('active');
                    if (chatboxContainer.classList.contains('active')) {
                        initializeChat();
                        chatInput.focus();
                    }
                });

                chatboxCloseButton.addEventListener('click', () => {
                    chatboxContainer.classList.remove('active');
                });

                function addUserMessage(message) {
                    const msgDiv = document.createElement('div');
                    msgDiv.className = 'message user-message';
                    msgDiv.textContent = message;
                    chatMessages.appendChild(msgDiv);
                    chatMessages.scrollTop = chatMessages.scrollHeight;
                }

                function showTypingIndicator() {
                    const typingDiv = document.createElement('div');
                    typingDiv.className = 'message ai-typing';
                    typingDiv.id = 'typing-indicator'; // Give it an ID to remove it later
                    typingDiv.textContent = 'PetFPT đang phản hồi...';
                    chatMessages.appendChild(typingDiv);
                    chatMessages.scrollTop = chatMessages.scrollHeight;
                }

                function removeTypingIndicator() {
                    const typingIndicator = document.getElementById('typing-indicator');
                    if (typingIndicator) {
                        typingIndicator.remove();
                    }
                }

                function addAiMessage(message) {
                    const msgDiv = document.createElement('div');
                    msgDiv.className = 'message ai-message';
                    msgDiv.innerHTML = message;
                    chatMessages.appendChild(msgDiv);
                    chatMessages.scrollTop = chatMessages.scrollHeight;
                }

                chatForm.addEventListener('submit', async (e) => {
                    e.preventDefault();
                    const query = chatInput.value.trim();
                    if (!query)
                        return;

                    addUserMessage(query);
                    chatInput.value = ''; // Clear input

                    chatInput.disabled = true;
                    chatSendButton.disabled = true;
                    showTypingIndicator();

                    try {
                        const url = 'aichat?query=' + encodeURIComponent(query);

                        const res = await fetch(url, {
                            method: 'GET',
                            headers: {
                                'Accept': 'application/json',
                                'X-Requested-With': 'XMLHttpRequest'
                            }
                        });

                        if (!res.ok) {
                            throw new Error(`Server error: ${res.status}`);
                        }

                        const data = await res.json();

                        removeTypingIndicator();

                        if (data && data.response) {
                            addAiMessage(data.response);
                        } else {
                            addAiMessage('Không nhận được phản hồi hợp lệ.');
                        }

                    } catch (err) {
                        console.error('Chat error:', err);
                        removeTypingIndicator();
                        addAiMessage('Có lỗi xảy ra. Xin thử lại.');
                    } finally {
                        chatInput.disabled = false;
                        chatSendButton.disabled = false;
                        chatInput.focus();
                    }
                });
            })();
        </script>
    </body>
</html>