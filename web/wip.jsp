<%-- 
    Document   : wip
    Created on : 25 Jul 2025, 17:34:09
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trang Web Đang Được Xây Dựng</title>
    
    <link href="https://fonts.googleapis.com/css2?family=Asap:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: 'Asap', sans-serif;
            background-color: #f8f9fa;
            color: #343a40;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            text-align: center;
            padding: 40px;
            border-radius: 10px;
            background-color: #ffffff;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
        }

        .logo img {
            max-width: 180px;
            margin-bottom: 25px;
        }

        .icon {
            font-size: 5rem;
            color: #ffc107;
            margin-bottom: 20px;
        }

        h1 {
            font-size: 2.5rem;
            margin-top: 0;
            margin-bottom: 15px;
            color: #212529;
            font-weight: 700;
        }

        p {
            font-size: 1.2rem;
            color: #6c757d;
            line-height: 1.6;
            max-width: 600px;
        }
        
        .return-button {
            display: inline-block;
            margin-top: 30px;
            padding: 12px 25px;
            background-color: #343a40;
            color: #ffffff;
            text-decoration: none;
            font-weight: 700;
            border-radius: 6px;
            transition: background-color 0.3s ease, transform 0.2s ease;
            font-size: 1rem;
        }

        .return-button:hover {
            background-color: #495057;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="logo">
            <img src="images/logo_banner/logo2.png" alt="PETFPT Logo"/>
        </div>

        <div class="icon">
            <i class="fas fa-tools"></i>
        </div>

        <h1>Trang Web Đang Được Xây Dựng</h1>
        <p>
            Chúng tôi đang nỗ lực làm việc để sớm ra mắt trang này. <br>
            Vui lòng quay lại sau. Xin chân thành cảm ơn!
        </p>
        
        <a href="homepage" class="return-button">Quay Về Trang Chủ</a>
    </div>

</body>
</html>