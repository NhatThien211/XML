<%-- 
    Document   : form
    Created on : Dec 5, 2019, 2:48:12 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css"/>
        <title>SUGGESTION</title>
    </head>
    <script>
        var temp = "";
        function checkChange(id) {
            if (temp !== "") {
                document.getElementById(temp).disabled = false;
            }
            document.getElementById(id).disabled = 'disabled';
            temp = id;
        }

        function setCombo() {
            temp = "first1";
            document.getElementById(temp).disabled = 'disabled';
        }

        function validateForm() {
            var latitude = document.getElementById("first").value;
            var logitude = document.getElementById("second").value;
            if (latitude === logitude) {
                window.alert("Xin hãy đặt độ ưu tiên khác nhau");
                return false;
            }
            return true;
        }
    </script>

    <body style="background-color: #F8F8F8" onload="setCombo()">
        <a href="index.jsp" class="suggestion_box">HOME</a>
        <div class="center_boxs_form">
            <form action="MainServlet" target="_blank" onsubmit="return validateForm()" >
                <h2 style="color: darkgreen">NƠI BẠN MUỐN Ở GẦN</h2>
                <input type="number" step="any" class="input_Dimention_box" name="latitude" placeholder="Kinh Độ" required="true" /><br/>
                <input type="text" class="input_Dimention_box" name="longitude" placeholder="Vĩ Độ" required="true"/><br/>
                <h2 style="color: darkgreen">NƠI BẠN ĐANG LÀM VIỆC</h2>
                <select name="txtUniversity">
                    <c:forEach var="dto" items="${requestScope.UNIVERSITY}">
                        <option value="${dto.id},${dto.name}">${dto.name}</option>
                    </c:forEach> 
                </select><br/>
                <div class = "combobox">
                    <span class="span">Ưu Tiên Số 1</span><select name="first" id="first">
                        <option name="option" value="distance" id="first0" onclick="checkChange('second0')" >Khoảng cách địa lí</option>
                        <option name="option" value="area" id="first1" onclick="checkChange('second1')">Diện tích phòng trọ</option>
                        <option name="option" value="price" id="first2" onclick="checkChange('second2')" >Giá phòng trọ phòng trọ</option>
                    </select><br/>
                </div>
                <div class = "combobox">
                    <span class="span">Ưu Tiên Số 2</span><select name="second" id="second">
                        <option value="distance" id="second0" onclick="checkChange('first0')" >Khoảng cách địa lí</option>
                        <option value="area" id="second1" onclick="checkChange('first1')" selected="true">Diện tích phòng trọ</option>
                        <option value="price" id="second2" onclick="checkChange('first2')" >Giá phòng trọ phòng trọ</option>
                    </select><br/>
                </div>

                <input type="hidden" name="action" value="find"/>
                <button type="submit" class="btn_find">Tìm Kiếm</button>
            </form>
        </div>
    </div>
</body>
</html>
