
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Epunda+Sans:ital,wght@0,300..900;1,300..900&family=Epunda+Slab:ital,wght@0,300..900;1,300..900&family=IM+Fell+English:ital@0;1&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=McLaren&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/indexStyles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>error</title>
        <style>
            .image{
                width: 15em;
                height: 15em;
                border-radius: 15px;
            }
        </style>
    </head>
    <body>
        <header>
            <jsp:include page="<%= (String)application.getAttribute("header") %>" />
        </header>
        <main>
            <div class="login-container">
                