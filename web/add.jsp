    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ page import="miscs.User, javax.servlet.http.HttpSession" %>
<%-- PREVENT BROWSER CACHING --%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>
<!DOCTYPE html>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>add</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>

    <header class="sticky-header">
        <jsp:include page="<%= (String)application.getAttribute("header") %>" />
    </header>

    <main class="content-container">
        <div class="login-card">
            <form action ="crud" method="POST">
                <h1>Add</h1>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" autocomplete="off">
            </div>
            
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="text" id="password" name="password" autocomplete="off">
            </div>

            <div class="form-group">
                <label for="role">Role:</label>
                <select id="role" name="role">
                    <option value="Guest">Guest</option>
                    <option value="Admin">Admin</option>
                </select>
            </div> 
                
                <button type="submit" class="submit-btn" name="btn" value="add">Add</button>
            </form>
            <form action="success" method="GET">
                <button type="submit" class="submit-btn" >Return</button>
            </form>
        </div>
    </main>

    <footer class="pill-footer">
        <jsp:include page="<%= (String)application.getAttribute("footer") %>" />
    </footer>

</body>
</html>