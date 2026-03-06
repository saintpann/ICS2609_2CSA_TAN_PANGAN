<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="miscs.User, javax.servlet.http.HttpSession" %>
<%-- PREVENT BROWSER CACHING --%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Success Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/success.css">
</head>
<body>
    <% 
        User currentuser = (User) session.getAttribute("user");
        String username = currentuser.getUsername();
        String role = currentuser.getRole();
        Object obj = request.getAttribute("list");
        List<String[]> list = (ArrayList<String[]>) obj;
    %>
        
    <header class="sticky-header">
        <jsp:include page="<%= (String)application.getAttribute("header") %>" />
    </header>
        
    <main class="content-container">
        <div class="login-card success-content">
            <% if(role.equals("Guest")) {%>
            <h2><%=username%></h2>
            <p>Role: <strong><%=role%></strong></p>
            <form action="logout">
                <button type="submit" class="submit-btn">Logout</button>
            </form>
            <% } else if(role.equals("Admin")){%>
                <div class="success-div-btn">
                    <div><h2>Welcome <strong><%=username%></strong></h2></div>
                    <div class="success-div-btn">
                        <div></div>
                        <div class="success-div-btn">
                            <form action="add" method="POST">
                                <button type="submit" class="submit-btn" value="add">Add</button>
                            </form>
                            <form action="logout">
                                <button type="submit" class="submit-btn">Logout</button>
                            </form>
                        </div>
                    </div>
                </div>
                <!--<table border="1">-->
                    <div class="success-div">
                    <div><p>Username</p></div>
                    <div><p>Password</p></div>
                    <div><p>Role</p></div> 
                    <div><p>Actions</p></div>
                    <!--<tbody>-->
                <%
                    
                    for(String[] sar : list){
                        boolean isMe = currentuser != null && sar[0].equals(currentuser.getUsername());
                %>
                        <div><p><%=sar[0]%></p></div>
                        <div><p><%=sar[1]%></p></div>
                        <div><p><%=sar[2]%></p></div>
                        <div class = "success-div-body-btn">
                            <%if(!isMe){%>
                            <form action="crud" method="POST">  
                                <input type="hidden" name="username" value="<%= sar[0] %>">
                                <input type="hidden" name="password" value="<%= sar[1] %>">
                                <input type="hidden" name="role" value="<%= sar[2] %>">
                                <button type="submit" class="submit-btn" name="btn" value="delete">Delete</input>
                            </form>
                                <%} else { %>
                                <span style="color:gray;">(You)</span>
                                <% } %>
                            <form action="update" method="POST">  
                                <input type="hidden" name="username" value="<%= sar[0] %>">
                                <input type="hidden" name="password" value="<%= sar[1] %>">
                                <input type="hidden" name="role" value="<%= sar[2] %>">
                                <button type="submit" class="submit-btn" value="update">Update</input>
                            </form>
                        </div>
                <%}%>
                    </div>
            <%}%>
        </div>
    </main>
         
    <footer class="pill-footer">
        <jsp:include page="<%= (String)application.getAttribute("footer") %>" />
    </footer>

</body>
</html>