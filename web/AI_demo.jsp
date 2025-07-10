<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AI Chat</title>
    <style>
        #chat-box {
            border: 1px solid #ccc;
            padding: 10px;
            height: 300px;
            overflow-y: auto;
            margin-top: 10px;
        }
        .message {
            margin: 5px 0;
            padding: 8px 12px;
            border-radius: 10px;
            max-width: 80%;
            clear: both;
        }
        .user {
            background-color: #e0f7fa;
            float: right;
            text-align: right;
        }
        .ai {
            background-color: #f1f8e9;
            float: left;
        }
        form {
            margin-bottom: 10px;
        }
    </style>
    <script>
        function sendQuery(event) {
            event.preventDefault();
            const queryInput = document.getElementById("query");
            const query = queryInput.value.trim();
            if (query === "") return;

            const xhr = new XMLHttpRequest();
            xhr.open("GET", "aichat?query=" + encodeURIComponent(query), true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const chatBox = document.getElementById("chat-box");
                    const messages = JSON.parse(xhr.responseText);

                    // Clear chat box
                    chatBox.innerHTML = "";

                    // Render messages
                    messages.forEach(function (msg) {
                        const p = document.createElement("div");
                        p.className = "message " + msg.sender;
                        p.innerHTML = "<strong>" + (msg.sender === "user" ? "You" : "AI") + ":</strong> " + msg.text;
                        chatBox.appendChild(p);
                    });

                    // Scroll to bottom
                    chatBox.scrollTop = chatBox.scrollHeight;

                    // Clear input
                    queryInput.value = "";
                }
            };
            xhr.send();
        }
    </script>
</head>
<body>
    <h1>AI Chat</h1>
    <form onsubmit="sendQuery(event)">
        <input type="text" name="query" id="query" placeholder="Ask me something..." />
        <input type="submit" value="Send" />
    </form>
    <div id="chat-box">
        <!-- Chat messages will be inserted here -->
    </div>
</body>
</html>
