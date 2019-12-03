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
        <title>SEARCH PAGE</title>
        <script>
            var regObj;
            var xmlDOM = new ActiveXObject("Microsoft.XMLDOM");
            var count = 0;
            var cells = [];
            function searchProcess() {
                if (!regObj) {
                    return false;
                }

                if (regObj) {
                    xmlDOM.async = false;
                    xmlDOM.loadXML(regObj);
                    var list = document.getElementById("listHouse");
                    if (xmlDOM.parseError.errorCode != 0) {
                        alert("error :" + xmlDoc.parseError.reason);
                    } else {
                        //delete screen
                        while (list.hasChildNodes()) {
                            list.removeChild(list.firstChild);
                        }
                        //find node
                        searchNode2(xmlDOM, myForm.txtName.value);

                    }
                    if (!list.hasChildNodes()) {
                        document.getElementById("form").submit();
                    }
                }
            }
            function searchNode2(node, strSearch) {
                if (node == null) {
                    return;
                }
                if (node.tagName == "address") {
                    var tmp = node.firstChild.nodeValue;
                    if (tmp.toLowerCase().indexOf(strSearch.toLowerCase(), 0) > -1) {
                        var imageTag = node.previousSibling;
                        while (imageTag.tagName != "image") {
                            var imageTag = imageTag.previousSibling;
                        }
                        var image = imageTag.firstChild.nodeValue;
                        var divListHouse = document.getElementById('listHouse');
                        // create a tag 
                        var url = findNodeValue(node, "url", true);
                        var aTag = document.createElement("a");
                        //add url
                        aTag.setAttribute("href", url);
                        aTag.setAttribute("target", "_blank");
                        divListHouse.appendChild(aTag);
                        //create div 
                        var card = document.createElement("div");
                        card.setAttribute("class", "cartBackground");
                        //add image
                        addImage(card, image);
                        aTag.appendChild(card);
                        //create insidediv
                        var insideDiv = document.createElement("div");
                        insideDiv.setAttribute("class", "insideDiv");
                        card.appendChild(insideDiv);
                        //to address tag
                        var address = node.firstChild.nodeValue;
                        addAddress(insideDiv, address);
                        // add price and area
                        var price = findNodeValue(node, "rentPrice", false);
                        var area = findNodeValue(node, "area", false);
                        addPrice(insideDiv, price, area);

                        //add phone
                        var phone = findNodeValue(node, "phone", false);
                        addPhone(insideDiv, phone);
                    }
                }
                var childs = node.childNodes;
                for (var i = 0; i < childs.length; i++) {
                    searchNode2(childs[i], strSearch);
                }
            }

            function addImage(card, src) {
                var img = document.createElement("img");
                img.setAttribute("src", src);
                img.setAttribute("class", "cardImage");
                card.appendChild(img);
            }

            function addAddress(insideDiv, address) {
                var p = document.createElement("span");
                p.setAttribute("class", "pTag");
                if (address.length > 45) {
                    address = address.substring(0, 45) + "...";
                }
                p.innerHTML = address;
                insideDiv.appendChild(p);
            }

            function addPrice(insideDiv, price, area) {
                var p = document.createElement("p");
                p.setAttribute("class", "price");
                if (price.length >= 5) {
                    p.innerHTML = "₫" + price + ".000/THÁNG ";
                } else {
                    p.innerHTML = "₫" + price + " TRIỆU/THÁNG ";
                }
                var span = document.createElement("span");
                span.setAttribute("class", "area");
                span.innerHTML = "(" + area.substring(0, area.length - 2) + "m²)";
                p.appendChild(span);
                insideDiv.appendChild(p);
            }



            function addPhone(insideDiv, phone) {
                var p = document.createElement("p");
                p.setAttribute("class", "phone");
                p.innerHTML = "SĐT: " + phone;
                insideDiv.appendChild(p);
            }

            function findNodeValue(node, name, isPrevious) {
                if (isPrevious) {
                    while (node.tagName != name) {
                        var node = node.previousSibling;
                    }
                } else {
                    while (node.tagName != name) {
                        var node = node.nextSibling;
                    }
                }
                return node.firstChild.nodeValue;
            }

        </script>
        <style>
            .cartBackground{border: 0.1px solid black;height: 350px;
                            width: 350px;float: left; margin: 50px;margin: 30px;
                            border-radius: 5px;
                            border-bottom: 1px solid lightgrey;
                            border-right: 1px solid lightgrey;
                            border-top: 0.5px solid lightgrey;
                            border-left: 0.5px solid lightgrey;}
            .cardImage{
                object-fit: cover;
                width: 350px;
                height: 200px;
            }
            .insideDiv{
                width: 350px;
                height: 150px;
                padding: 16px;
            }
            .pTag{
                text-overflow: ellipsis;
                display: inline;
                color: black;
            }
            .price{
                color: #f57224;
                font-size: 18px;
            }
            .area{
                color: #9e9e9e;
                font-size: 14px;
            }
            .phone{
                color:#212121;
            }
            .insert_box{
                border: none;
                height: 30px;
                width: 90%;
                border-radius: 5px;
                text-align: left;
            }
            .btn_insert{
                background-size: cover;
                width: 8%;
                border: 0px solid white;
                background-color: white;
                background-image: url('image/search-icon.svg');
                cursor: pointer;
            }
            .searchBox{
                box-shadow: 0px 0px 0px 1px rgba(0,0,0,0.1), 0px 2px 4px 0px rgba(0,0,0,0.16);
                border-radius: 6px;
                border: none;
                padding: 5px;
                width: 40%;
                height: 30px;
                margin-left: 400px;
            }
            .header{
                width: 100%;
                height: 60px;
                text-align: center;
                border-bottom: 0.5px solid lightgrey;
            }
        </style>
    </head>
    <body >
        <script>
            regObj = '${requestScope.INFO}';
        </script>
        <div class="header">
            <form name="myForm" id="form" action="MainServlet">
                <div class="searchBox">
                    <input class="insert_box" type="text" name="txtName" value="${requestScope.SEARCH_VALUE}"/>
                    <input type="button" name="btSearch" class="btn_insert"
                           onclick="return searchProcess('dataTable');"/>
                    <input type="hidden" name="action" value="search"/>
                </div>
            </form>
        </div>
        <c:set var="listHouse" value="${requestScope.LIST}"/>
        <div id="listHouse">
            <c:if test="${not empty listHouse}">
                <c:forEach var="dto" items="${listHouse}" varStatus="counter">
                    <a href="${dto.url}">
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
        </div>
    </body>
</html>
