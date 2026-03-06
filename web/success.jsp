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
        User currentuser = (User)session.getAttribute("user");
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
                <button type="submit" class="submit-btn">LOGOUT</button>
            </form>
            <% } else if(role.equals("Admin")){%>
                <h2>Welcome <strong><%=role%></strong></h2>
                <form action="logout">
                <button type="submit" class="submit-btn">LOGOUT</button>
                </form>
                <form action="add" method="POST">
                    <button type="submit" class="submit-btn" value="add">Add</button>
                </form>
                <table border="1">
                    <thead>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Role</th> 
                    <th>Actions</th>
                    </thead>
                    <tbody>
                <%
                    
                    for(String[] sar : list){
                        boolean isMe = currentuser != null && sar[0].equals(currentuser.getUsername());
                %>
                      <tr>  
                        <td><%=sar[0]%></td>
                        <td><%=sar[1]%></td>
                        <td><%=sar[2]%></td>
                        <td><%if(!isMe){%>
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
                        </form></td>
                      </tr>
                <%}%>
                    </tbody>
                </table>
            <%}%>
        </div>
    </main>
         
    <footer class="pill-footer">
        <jsp:include page="<%= (String)application.getAttribute("footer") %>" />
    </footer>

</body>
</html>