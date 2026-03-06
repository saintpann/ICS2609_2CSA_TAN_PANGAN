<%@ page isErrorPage="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error page!</title>
        <style>
            .image{
                width: 15em;
                height: 15em;
                border-radius: 15px;
            }
        </style>
    </head>
    <body>
        <header class="sticky-header">
            <jsp:include page="<%= (String) application.getAttribute("header") %>" />
        </header>
        <main class="content-container">
            <div class="login-card">
                