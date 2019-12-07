<%-- 
    Document   : suggestion
    Created on : Dec 4, 2019, 9:40:29 AM
    Author     : ASUS
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css"/>
        <title>Suggestion</title>
    </head>

    <body style="background-color: #F8F8F8">
        <div class="top3">
            <c:set var="list" value="${requestScope.RESULT}"/>
            <c:if test="${not empty list}">
                <div class="topBox">
                    <h2 class="title">TOP 3
                        <form action="MainServlet">
                            <input type="hidden" name="action" value="printPDF"/>
                            <input type="submit"  class="suggestion_PrintPDF" value="PRINT PDF"/>
                        </form>
                    </h2>  
                </div>
                <div style="float:left">
                    <c:forEach var="dto" items="${list}" varStatus="counter">
                        <c:set var="number" value="${dto.numberHomemate}"/>
                        <c:if test="${counter.count == 1}">
                            <a href="MainServlet?action=detail&url=${dto.url}&id=${dto.id}&schoolId=${requestScope.UNIVERITY_ID}" target="_blank" >
                                <c:if test="${number > 0}">
                                    <div class="cartBackgroundTop1" style="height: 400px" >
                                        <img class="cardImage" src="${dto.image}"/>
                                        <div class="insideDiv">
                                            <span class="pTag">${dto.address}</span>
                                            <p class="price">
                                                ₫ ${dto.rentPrice}
                                                <span class="area">(${dto.area})</span>
                                            </p>
                                            <p class="phone">SĐT: ${dto.phone} <span class="area">(Khoảng cách: ${dto.distance}km)</span></p>
                                            <c:set var="school" value="${requestScope.UNIVERITY_NAME}"/>
                                            <c:if test="${not empty school}">
                                                <span class="blue">Có ${dto.numberHomemate} bạn học ${requestScope.UNIVERITY_NAME} cũng quan tâm.</span>
                                            </c:if>


                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${number == 0}">
                                    <div class="cartBackgroundTop1" style="height: 400px">
                                        <img class="cardImage" src="${dto.image}"/>
                                        <div class="insideDiv">
                                            <span class="pTag">${dto.address}</span>
                                            <p class="price">
                                                ₫ ${dto.rentPrice}
                                                <span class="area">(${dto.area})</span>
                                            </p>
                                            <p class="phone">SĐT: ${dto.phone} <span class="area">(Khoảng cách: ${dto.distance}km)</span></p>
                                            <c:set var="school" value="${requestScope.UNIVERITY_NAME}"/>
                                            <c:if test="${not empty school}">
                                                <span class="green">Hãy là người đầu tiên của ${requestScope.UNIVERITY_NAME} chọn nơi này.</span>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>
                            </a>
                        </c:if>
                        <c:if test="${counter.count != 1}">
                            <a href="MainServlet?action=detail&url=${dto.url}&id=${dto.id}&schoolId=${requestScope.UNIVERITY_ID}" target="_blank" >
                                <c:if test="${number > 0}">
                                    <div class="cartBackground" style="height: 400px" >
                                        <img class="cardImage" src="${dto.image}"/>
                                        <div class="insideDiv">
                                            <span class="pTag">${dto.address}</span>
                                            <p class="price">
                                                ₫ ${dto.rentPrice}
                                                <span class="area">(${dto.area})</span>
                                            </p>
                                            <p class="phone">SĐT: ${dto.phone} <span class="area">(Khoảng cách: ${dto.distance}km)</span></p>
                                            <c:set var="school" value="${requestScope.UNIVERITY_NAME}"/>
                                            <c:if test="${not empty school}">
                                                <span class="blue">Có ${dto.numberHomemate} bạn học ${requestScope.UNIVERITY_NAME} cũng quan tâm.</span>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${number == 0}">
                                    <div class="cartBackground" style="height: 400px">
                                        <img class="cardImage" src="${dto.image}"/>
                                        <div class="insideDiv">
                                            <span class="pTag">${dto.address}</span>
                                            <p class="price">
                                                ₫ ${dto.rentPrice}
                                                <span class="area">(${dto.area})</span>
                                            </p>
                                            <p class="phone">SĐT: ${dto.phone} <span class="area">(Khoảng cách: ${dto.distance}km)</span></p>
                                            <c:set var="school" value="${requestScope.UNIVERITY_NAME}"/>
                                            <c:if test="${not empty school}">
                                                <span class="green">Hãy là người đầu tiên của ${requestScope.UNIVERITY_NAME} chọn nơi này.</span>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>
                            </a>
                        </c:if>
                    </c:forEach>
                </div>

            </c:if>
            <c:if test="${empty list}">
                <p style="color: blue">Xin lỗi chúng tôi không có bất kì thông tin nào liên quan tới nơi bạn muốn tìm.</p>
            </c:if>
        </div>

        <c:set var="listHome" value="${requestScope.LISTHOME}"/>

        <c:if test="${not empty listHome}">
            <div>
                <h2 class="title2">TRONG VÒNG 10Km</h2>
                <c:forEach var="dto" items="${listHome}" varStatus="counter">
                    <c:set var="number" value="${dto.numberHomemate}"/>
                    <a href="MainServlet?action=detail&url=${dto.url}&id=${dto.id}&schoolId=${requestScope.UNIVERITY_ID}" target="_blank">
                        <c:if test="${number > 0}">
                            <div class="cartBackground" style="height: 400px">
                                <img class="cardImage" src="${dto.image}"/>
                                <div class="insideDiv">
                                    <span class="pTag">${dto.address}</span>
                                    <p class="price">
                                        ₫ ${dto.rentPrice}
                                        <span class="area">(${dto.area})</span>
                                    </p>
                                    <p class="phone">SĐT: ${dto.phone} <span class="area">(Khoảng cách: ${dto.distance}km)</span></p>
                                    <c:set var="school" value="${requestScope.UNIVERITY_NAME}"/>
                                    <c:if test="${not empty school}">
                                        <span class="blue">Có ${dto.numberHomemate} bạn học ${requestScope.UNIVERITY_NAME} cũng quan tâm.</span>
                                    </c:if>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${number == 0}">
                            <div class="cartBackground" style="height: 400px">
                                <img class="cardImage" src="${dto.image}"/>
                                <div class="insideDiv">
                                    <span class="pTag">${dto.address}</span>
                                    <p class="price">
                                        ₫ ${dto.rentPrice}
                                        <span class="area">(${dto.area})</span>
                                    </p>
                                    <p class="phone">SĐT: ${dto.phone} <span class="area">(Khoảng cách: ${dto.distance}km)</span></p>
                                    <c:set var="school" value="${requestScope.UNIVERITY_NAME}"/>
                                    <c:if test="${not empty school}">
                                        <span class="green">Hãy là người đầu tiên của ${requestScope.UNIVERITY_NAME} chọn nơi này.</span>
                                    </c:if>
                                </div>
                            </div>
                        </c:if>
                    </a>
                </c:forEach>
            </div>

        </c:if>
    </body>
</html>
