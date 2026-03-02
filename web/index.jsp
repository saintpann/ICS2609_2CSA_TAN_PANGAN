    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ page import="objects.User, javax.servlet.http.HttpSession" %>
<%-- PREVENT BROWSER CACHING --%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MP2</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>

    <header class="sticky-header">
        HEADER PLACEHOLDER TEXT (static / sticky )
    </header>

    <main class="content-container">
        <div class="login-card">
            <form action ="login" method="POST">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username">
            </div>
            
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password">
            </div>

            <button type="submit" class="submit-btn">Submit</button>
            </form>
        </div>
    </main>

    <footer class="pill-footer">
        FOOTER PLACEHOLDER TEXT
    </footer>

</body>
</html>