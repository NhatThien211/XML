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
            var id = findNodeValue(node, "id", false);
            var aTag = document.createElement("a");
            //add url
            var href = "MainServlet?action=detail&url=" + url + "&id=" + id;
            aTag.setAttribute("href", href);
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
    span.innerHTML = "(" + area + "m²)";
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



