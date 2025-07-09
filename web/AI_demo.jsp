<%-- 
    Document   : AI_demo
    Created on : 9 Jul 2025, 20:37:34
    Author     : Lenovo
--%>

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
        #chat-box p {
            margin: 5px 0;
        }
        form {
            margin-bottom: 10px;
        }
    </style>
    <script>
        function sendQuery(event) {
            event.preventDefault();
            const query = document.getElementById("query").value.trim();
            if (query === "") return;

            const xhr = new XMLHttpRequest();
            xhr.open("GET", "aichat?query=" + encodeURIComponent(query), true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    document.getElementById("chat-box").innerHTML = xhr.responseText;
                    document.getElementById("query").value = "";
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
        <!-- Chat history appears here -->
    </div>
</body>
</html>

