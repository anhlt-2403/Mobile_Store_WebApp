<%-- 
    Document   : AdminPage
    Created on : Sep 29, 2022, 7:51:46 AM
    Author     : Tuan Anh
--%>

<%@page import="Users.UserError"%>
<%@page import="java.util.List"%>
<%@page import="Users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="manager.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <title>Admin Page</title>
</head>
<body>
        <h1>Staff Management</h1>
                <%
                    UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
                    if (loginUser == null || loginUser.getRole() != 1) {
                        response.sendRedirect("login.jsp");
                        return;
                    }
                    String search = request.getParameter("search");
                    if (search == null) {
                        search = "";
                    }
                %>
                 <div class="hello">
                    <div class="avatar-container" id="avatarContainer">
                        <img src="images/Tuan_Anh.jpg" alt="Avatar" class="avatar" id="avatar">
                        <div id="userInfo" class="hidden">
                            <p><%= "userID: " + loginUser.getUserID() %></p>
                            <a href="MainController?action=Logout" id="logoutLink">
                                <i class="fas fa-sign-out-alt"></i> Logout
                            </a>
                        </div>
                    </div>
                    <p><%= loginUser.getFullName() %></p>
                </div>
    <section>
        <div class="admin-box">
            <div class="admin-value">
                <form action="MainController">
                    <div class="search-box">
                        <input type="text" name="search" value="<%= search %>"/>
                        <button type="submit" name="action" value="Search">Search</button>
                        <a href="create.jsp">
                          <button type="button">Add new staff</button>
                        </a>
                    </div>
                </form>  
                <%
                    List<UserDTO> listUser = (List<UserDTO>) request.getAttribute("LIST_USER");
                    if (listUser != null) {
                        if (listUser.size() > 0) {
                %>
                <div class="table-container">
                         <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>User ID</th>
                            <th>Full Name</th>
                            <th>Password</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int count = 0;
                            for (UserDTO user : listUser) {
                        %>
                        <form action="MainController" method="POST">
                            <tr>
                                <td><%= ++count %></td>
                                <td>
                                    <input type="text" name="userID" value="<%= user.getUserID()%>" readonly=""/>
                                </td>
                                <td>
                                    <input type="text" name="fullName" value="<%= user.getFullName()%>" required=""/>
                                </td>
                                <td><%= user.getPassword()%></td>
                                <td>
                                    <button type="submit" name="action" value="Delete">Delete</button>
                                </td>
                                <td class = "Update">
                                    <button type="submit" name="action" value="Update">Update</button>
                                    <input type="hidden" name="search" value="<%= search%>"/>
                                </td>
                            </tr>
                        </form>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                </div>
                <%
                        }
                    }
                %>
                <h2>
                <%
                    String error = (String) request.getAttribute("ERROR");
                    if (error == null) {
                        error = "";
                    }
                %> 
                <%= error%>
                </h2>
            </div>
        </div>
    </section>
</body>
</html>
