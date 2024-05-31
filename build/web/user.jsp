<%-- 
    Document   : user
    Created on : Oct 2, 2023, 9:48:44 PM
    Author     : Tuấn Anh
--%>


<%@page import="Devices.MobileDTO"%>
<%@page import="java.util.List"%>
<%@page import="Users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="lightbox.min.css">
        <script src="lightbox.min.js"></script>   
        <link rel="stylesheet" href="user.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mobile Store</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || loginUser.getRole() != 0) {
                response.sendRedirect("login.jsp");
                return;
            }
            String minValue = request.getParameter("minValue");
            if (minValue == null) {
                minValue = "";
            }

            String maxValue = request.getParameter("maxValue");
            if (maxValue == null) {
                maxValue = "";
            }
        %>
        <header>Mobile Store</header>
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
        <section>
            <form action ="MainController">
                <div class ="search-box">
                    Min:<input type = "number" name = "minValue" value = "<%=minValue%>"/>
                    Max:    <input type = "number" name = "maxValue" value = "<%=maxValue%>"/>
                    <button type="submit" name="action" value="Search">Search</button>
                    <button type="submit" name="action" value="View">View card</button>
                    <a href="history.jsp">
                        <button type="button">Transaction history</button>
                    </a>
                </div>
            </form>
            <%
                List<MobileDTO> mobileList = (List<MobileDTO>) request.getAttribute("LIST_MOBILE");
                if (mobileList != null) {
                    if (mobileList.size() > 0) {
            %>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Image</th>
                            <th>Mobile ID</th>
                            <th>Mobile Name</th>
                            <th>Mobile Description</th>
                            <th>Year Of Production</th>
                            <th>Price</th>
                            <th>Add To Cart</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int count = 0;
                            for (MobileDTO mobile : mobileList) {
                        %>
                    <form action="MainController" method="POST">
                        <tr>
                            <td><%= ++count%></td>
                            <td>
                                <img src="images/<%=mobile.getMobileID()%>.png" alt="<%= mobile.getMobileName()%> Image" onclick="openModal('images/<%=mobile.getMobileID()%>.png')"/>
                            </td>
                            <td>
                                <input type = "text" name = "mobileID" value ="<%= mobile.getMobileID()%>" readonly=""/>
                            </td>
                            <td>
                                <input type = "text" name = "mobileName" value ="<%= mobile.getMobileName()%>" readonly=""/>
                            </td>
                            <td>
                                <input type = "text" name = "description" value ="<%= mobile.getDescription()%>" readonly=""/>
                            </td>
                            <td>
                                <input type = "text" name = "yearOfProduction" value ="<%= mobile.getYearOfProduction()%>" readonly=""/>
                            </td>
                            <td>
                                <input type = "text" name = "price" value ="<%= mobile.getPrice()%>" readonly=""/>
                            </td>
                            <td>
                                <input type ="hidden" name ="notSale" value ="<%=mobile.getStatus()%>">
                                <button type="submit" name="action" value="Add">Add</button>
                            </td>
                        <input type ="hidden" name ="minValue" value ="<%=minValue%>">
                        <input type ="hidden" name ="maxValue" value ="<%=maxValue%>">
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
            <h2><% String message = (String) request.getAttribute("MESSAGE");
                if (message == null) {
                    message = "";
                }
                %>
                <%=message%></h2>
        </section>
        <!-- Modal -->
        <div id="myModal" class="modal">
            <span class="close" onclick="document.getElementById('myModal').style.display = 'none'">&times;</span>
            <img src="" id="modalImg" class="modal-content">
        </div>
        <script>
            // Hàm mở modal và hiển thị hình ảnh lớn
            function openModal(imageUrl) {
                var modal = document.getElementById("myModal");
                var modalImg = document.getElementById("modalImg");
                modalImg.src = imageUrl;
                modal.style.display = "block";
                modal.onclick = function () {
                    modal.style.display = "none";
                };
            }
        </script>
    </body>
</html>
