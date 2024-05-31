<%-- 
    Document   : history
    Created on : Oct 5, 2023, 1:45:42 AM
    Author     : Tuấn Anh
--%>

<%@page import="java.util.List"%>
<%@page import="Devices.MobileDTO"%>
<%@page import="Users.UserDAO"%>
<%@page import="Devices.CartDTO"%>
<%@page import="Users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="viewCart.js"></script>
        <link rel="stylesheet" href="history.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        %>
        <header>Transaction history</header>
        <div class="hello">
            <div class="avatar-container" id="avatarContainer">
                <img src="images/Tuan_Anh.jpg" alt="Avatar" class="avatar" id="avatar">
                <div id="userInfo" class="hidden">
                    <p><%= "userID: " + loginUser.getUserID()%></p>
                    <a href="MainController?action=Logout" id="logoutLink">
                        <i class="fas fa-sign-out-alt"></i> Logout
                    </a>
                </div>
            </div>
            <p><%= loginUser.getFullName()%></p>
        </div>
        <%
            UserDAO dao = new UserDAO();
            List<MobileDTO> mobileList = dao.getHistoryList(loginUser.getUserID());
            if (mobileList != null && mobileList.size() > 0) {
        %>
        <section>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Image</th>
                            <th>Mobile Name</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Has Pay</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int count = 1;
                            for (MobileDTO mobile : mobileList) {
                        %>
                    <form>
                        <tr>
                            <td>
                                <%=count++%>
                            </td>
                            <td>
                                <img src="images/<%=mobile.getMobileID()%>.png" alt="<%= mobile.getMobileName()%> Image" onclick="openModal('images/<%=mobile.getMobileID()%>.png')"/>
                            </td>
                            <td><%=mobile.getDescription()%></td>
                            <td><%=mobile.getPrice()%></td>
                            <td><%=mobile.getQuantity()%></td>
                            <td><%=mobile.getPrice() * mobile.getQuantity()%></td>
                        </tr>
                    </form>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
            <button type="button" onclick="window.location.href = 'MainController?&action=Search&'">Shopping more</button>
            <%
            } else {
            %>
            <p>You haven't placed any orders <a href="user.jsp">Shopping now</a></p> 
            <%
                }
            %>
        </section>
        <div id="myModal" class="modal">
            <span class="close" onclick="document.getElementById('myModal').style.display = 'none'">&times;</span>
            <img src="" id="modalImg" class="modal-content">
        </div>
    </body>
</html>
