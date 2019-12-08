<%-- 
    Document   : Login
    Created on : Dec 6, 2019, 4:39:22 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css"/>
        <title>LOGIN</title>
    </head>
    <body>
         
               <a href="index.jsp" class="suggestion_box">HOME</a>
            <div class="center_boxs">
                <form action="MainServlet" >
                    <h2 style="color: darkgreen">LOGIN</h2>
                    <input type="text" class="input_Dimention_box" name="name" placeholder="Username" required="true"/><br/>
                    <input type="password" class="input_Dimention_box" name="password" placeholder="Password" required="true"/><br/>
                    <input type="hidden" name="action" value="login"/>
                    <font color="red">${requestScope.INVALID}</font><br/>
                    <button type="submit" class="btn_find">Đăng Nhập</button>
                </form>
            </div>
    </body>
</html>
