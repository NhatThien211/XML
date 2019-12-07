<%-- 
    Document   : index
    Created on : Dec 2, 2019, 8:37:29 PM
    Author     : ASUS
--%>
<%@page import="thienVN.JaxB.Houses" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css"/>
        <title>HOMESU</title>
        <script type="text/javascript" src="js/default.js"></script>

    </head>
    <body >
        <script>
            regObj = '${requestScope.INFO}';
        </script>
        <div class="header">
            <a href="form.jsp" class="suggestion_box">Suggestion</a>
            <a href="login.jsp" class="login_box">Login</a>
            <form name="myForm" id="form" action="MainServlet">
                <div class="searchBox">
                    <input class="insert_box" type="text" placeholder="Địa chỉ nơi bạn cần tìm..." name="txtName" value="${requestScope.SEARCH_VALUE}"/>
                    <input type="button" name="btSearch" class="btn_insert"
                           onclick="return searchProcess();"/>
                    <input type="hidden" name="action" value="search"/>
                </div>
            </form>
        </div>
        <c:set var="listHouse" value="${requestScope.LIST}"/>
        <div id="listHouse">
            <c:if test="${not empty listHouse}">
                <c:forEach var="dto" items="${listHouse}" varStatus="counter">
                    <a href="MainServlet?action=detail&url=${dto.url}&id=${dto.id}" target="_blank">
                        <div class="cartBackground">
                            <img class="cardImage" src="${dto.image}"/>
                            <div class="insideDiv">
                                <span class="pTag">${dto.address}</span>
                                <p class="price">
                                    <c:if test="${dto.rentPrice >= 100}">
                                        ₫ ${dto.rentPrice}.000/THÁNG 
                                    </c:if>
                                    <c:if test="${dto.rentPrice < 100}">
                                        ₫ ${dto.rentPrice} TRIỆU/THÁNG
                                    </c:if>    
                                    <span class="area">(${dto.area}m²)</span>
                                </p>
                                <p class="phone">SĐT: ${dto.phone}</p>
                            </div>
                        </div>
                    </a>

                </c:forEach>
            </c:if>
            <c:if test="${not empty requestScope.IS_SEARCH}">
                <c:if test="${empty listHouse}">
                    <p style="color: darkgreen; text-align: center">Xin lỗi chúng tôi không có bất kì thông tin nào liên quan tới nơi bạn muốn tìm.</p>
                </c:if>
            </c:if>

        </div>
    </body>
</html>
