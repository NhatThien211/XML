<%-- 
    Document   : admin
    Created on : Dec 4, 2019, 3:22:47 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css"/>
        <title>Crawl</title>
    </head>
    <script>
        function show(status) {
            document.getElementById("font1").setAttribute("class", "display_none");
            document.getElementById("font2").setAttribute("class", "display_none");
            if (status == '1') {
                document.getElementById("h4").setAttribute("class", "display");
            } else {
                document.getElementById("h5").setAttribute("class", "display");
            }
        }
    </script>
    <body>
      <a href="MainServlet?action=logout" class="suggestion_box">LOGOUT</a>
            <div class="center_boxs">
                <form action="MainServlet" >
                    <input type="hidden" name="action" value="crawl"/>
                    <font color="green" id="font1">${requestScope.CRAWLED}</font><br/>
                    <button type="submit" class="btn_crawl" onclick="show(1)">CẬP NHẬT DỮ LIỆU</button><br/>
                    <p><h4 style="display: none" id="h4">Đang cập nhật dữ liệu, xin hãy kiên nhẫn...</h4></p>
                </form>
                <form action="MainServlet" >
                    <input type="hidden" name="action" value="crawlSchool"/>
                    <font color="green" id="font2">${requestScope.CRAWLEDSCHOOL}</font><br/>
                    <button type="submit" class="btn_crawl_school" onclick="show(2)">CẬP NHẬT TRƯỜNG HỌC</button><br/>
                    <p><h4 style="display: none" id="h5">Đang cập nhật dữ liệu, xin hãy kiên nhẫn...</h4></p>
                </form>
            </div>

    </body>
</html>
