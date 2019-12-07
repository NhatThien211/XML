/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import thienVN.Common.Constraint;

import thienVN.Utils.TextUtils;
import xsd.thien.House;
import xsd.thien.Schools;

/**
 *
 * @author ASUS
 */
public class StAXParser {

    public XMLStreamReader getReader(InputStream is) {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        try {
            reader = xif.createXMLStreamReader(is);
        } catch (XMLStreamException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reader;
    }

    public InputStream getStreamFromUri(String uri) {
        URLConnection connection = null;
        try {
            URL url = new URL(uri);
            connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla 5.0 (Window; U; Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream inputStream = connection.getInputStream();
            return inputStream;
        } catch (MalformedURLException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public InputStream getStreamFromUriStateMachine(String uri) {
        URLConnection connection = null;
        try {
            URL url = new URL(uri);
            connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla 5.0 (Window; U; Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream inputStream = connection.getInputStream();
            String textContent = getString(inputStream);
            if (textContent.contains("")) {
                textContent = textContent.replace("", "hello");
            }
            textContent = TextUtils.refineHtml(textContent);
            InputStream htmlResult = new ByteArrayInputStream(textContent.getBytes("UTF-8"));
            return htmlResult;
        } catch (MalformedURLException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public InputStream getStreamFromUriStateMachineUTF8(String uri) {
        URLConnection connection = null;
        try {
            URL url = new URL(uri);
            connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla 5.0 (Window; U; Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream inputStream = connection.getInputStream();
            String textContent = getString(inputStream);
            textContent = TextUtils.refineHtml(textContent);
            textContent = TextUtils.convertEntities(textContent);
            InputStream htmlResult = new ByteArrayInputStream(textContent.getBytes("UTF-8"));
            return htmlResult;
        } catch (MalformedURLException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static String getString(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ex) {
        }
        return stringBuilder.toString();
    }

    public String getBDSLink(XMLStreamReader reader) {
        String link = "";
        try {
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("a".equals(tagName)) {
                        try {
                            String temp = reader.getAttributeValue("", "href");
                            String text = reader.getElementText();
                            if (Constraint.Link_TEXT.equals(text)) {
                                link = temp;
                                break;
                            }
                        } catch (Exception e) {
                            // do not thing
                        }

                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return link;
    }

    public ArrayList<String> getDBSHomePage(XMLStreamReader reader) {
        ArrayList<String> listLink = new ArrayList<>();
        try {
            boolean foundHome = false;
            boolean foundTitle = false;
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("article".equals(tagName)) {
                        foundHome = true;
                    }
                    if ("h4".equals(tagName) && foundHome) {
                        foundTitle = true;
                    }
                    if ("a".equals(tagName) && foundHome && foundTitle) {
                        String link = reader.getAttributeValue("", "href");
                        listLink.add(link);
                        foundHome = false;
                        foundTitle = false;
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listLink;
    }

    public int getPageCountDBS(XMLStreamReader reader) {
        int count = 0;
        try {
            boolean foundLi = false;
            boolean foundUl = false;
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("ul".equals(tagName)) {
                        String className = reader.getAttributeValue("", "class");
                        if ("pagination pagination".equals(className)) {
                            foundUl = true;
                        }
                    }
                    if ("li".equals(tagName) && foundUl) {
                        foundLi = true;
                    }
                    if (foundLi && "a".equals(tagName) && foundUl) {
                        count++;
                        foundLi = false;
                    }
                }
                if (cursor == XMLStreamConstants.END_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("ul".equals(tagName)) {
                        foundUl = false;
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    private final String HOME_IMAGE_CLASS = "swiper-slide js-pr-img-item";
    private final String PRICE_RENTING = "Giá cho thuê";
    private final String PRICE_ELECTRIC = "Giá điện";
    private final String PRICE_WATER = "Giá nước";
    private final String AREA = "Diện tích";
    private final String LATLNG = "map_wrapper -js-section-vitri";

    public House getHomeDetail(XMLStreamReader reader, int i) {
        House house = new House();
        try {
            boolean isFoundImg = false;
            boolean isFoundName = false;
            boolean isSpecialName = false;
            boolean isFoundAddress = false;
            boolean isFoundAddressTag = false;
            boolean isFoundPhone = false;
            boolean isFoundRentPrice = false;
            boolean isFoundArea = false;
            boolean isFoundElectricPrice = false;
            boolean isFoundWaterPrice = false;
            boolean isFoundLocation = false;
            String imgUrl = "";
            String name = "";
            String address = "";
            String phone = "";
            String rentingPrice = "";
            String area = "";
            String electricPrice = "";
            String water = "";
            String latitude = "";
            String longitude = "";
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (!isFoundImg) {
                        if ("div".equals(tagName)) {
                            String className = reader.getAttributeValue("", "class");
                            if (HOME_IMAGE_CLASS.equals(className)) {
                                isFoundImg = true;
                                imgUrl = reader.getAttributeValue("", "data-background-image");
                                house.setImage(imgUrl);

                            }
                        }
                    }
                    if (!isFoundName) {
                        if ("h1".equals(tagName)) {
                            String style = reader.getAttributeValue("", "class");
                            if ("pr_main_title vip30".equals(style) || "pr_main_title vip20".equals(style) || "pr_main_title vip10".equals(style) || "pr_main_title free".equals(style)) {
                                try {
                                    isFoundName = true;
                                    name = reader.getElementText();
                                    house.setName(name);
                                } catch (Exception e) {
                                    isSpecialName = true;
                                }

                            }
                        }
                    }
                    if (!isFoundAddress) {
                        if ("span".equals(tagName)) {
                            String className = reader.getAttributeValue("", "class");
                            if ("address".equals(className)) {
                                isFoundAddressTag = true;
                            }
                        }
                    }
                    if (!isFoundPhone) {
                        if ("strong".equals(tagName)) {
                            String style = reader.getAttributeValue("", "style");
                            if ("color: red;".equals(style)) {
                                try {
                                    phone = reader.getElementText();
                                    house.setPhone(phone.trim());
                                    isFoundPhone = true;
                                } catch (Exception e) {

                                }

                            }
                        }
                    }
                    if (!isFoundRentPrice) {
                        if ("span".equals(tagName)) {
                            String className = reader.getAttributeValue("", "class");
                            if ("name".equals(className)) {
                                String title = reader.getElementText();
                                if (PRICE_RENTING.equals(title)) {
                                    reader.nextTag();
                                    reader.next();
                                    rentingPrice = reader.getText();
                                    //  rentingPrice = getNumberFromString(rentingPrice);
                                    isFoundRentPrice = true;
                                    house.setRentPrice(rentingPrice);
                                }
                            }
                        }
                    }
                    if (!isFoundArea) {
                        if ("span".equals(tagName)) {
                            try {
                                String className = reader.getAttributeValue("", "class");
                                if ("name".equals(className)) {
                                    String title = reader.getElementText();
                                    if (AREA.equals(title)) {
                                        reader.nextTag();
                                        reader.next();
                                        area = reader.getText();
                                        //       area = getNumberFromString(area);
                                        isFoundArea = true;
                                        house.setArea(area);
                                    }
                                }
                            } catch (Exception e) {
                                // do nothing
                            }
                        }
                    }
                    if (!isFoundElectricPrice) {
                        if ("span".equals(tagName)) {
                            try {
                                String className = reader.getAttributeValue("", "class");
                                if ("name".equals(className)) {
                                    String title = reader.getElementText();
                                    if (PRICE_ELECTRIC.equals(title)) {
                                        reader.nextTag();
                                        reader.next();
                                        electricPrice = reader.getText();
                                        //    electricPrice = getNumberFromString(electricPrice);
                                        isFoundElectricPrice = true;
                                        house.setElectricPrice(electricPrice);
                                    }
                                }
                            } catch (Exception e) {
                                // do no thing
                            }
                        }
                    }
                    if (!isFoundWaterPrice) {
                        if ("span".equals(tagName)) {
                            try {
                                String className = reader.getAttributeValue("", "class");
                                if ("name".equals(className)) {
                                    String title = reader.getElementText();
                                    if (PRICE_WATER.equals(title)) {
                                        reader.nextTag();
                                        reader.next();
                                        water = reader.getText();
                                        //         water = getNumberFromString(water);
                                        isFoundWaterPrice = true;
                                        house.setWaterPrice(water);
                                    }
                                }
                            } catch (Exception e) {
                                // do nothing
                            }
                        }
                    }
                    if (!isFoundLocation) {
                        if ("div".equals(tagName)) {
                            try {
                                String className = reader.getAttributeValue("", "class");
                                if (LATLNG.equals(className)) {
                                    latitude = reader.getAttributeValue("", "data-lat");
                                    longitude = reader.getAttributeValue("", "data-long");
                                    house.setLatitude(BigDecimal.valueOf(Double.parseDouble(latitude)));
                                    house.setLongitude(BigDecimal.valueOf(Double.parseDouble(longitude)));
                                }
                            } catch (Exception e) {
                                // do nothing
                            }
                        }
                    }
                }
                if (cursor == XMLStreamConstants.END_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (isFoundAddressTag) {
                        if ("i".equals(tagName)) {
                            reader.next();
                            address = reader.getText();
                            isFoundAddressTag = false;
                            isFoundAddress = true;
                            house.setAddress(address);
                        }
                    }
                    if (isSpecialName) {
                        if ("div".equals(tagName)) {
                            reader.next();
                            name = reader.getText();
                            isSpecialName = false;
                            house.setName(name);
                        }
                    }
                }
            }
        } catch (XMLStreamException ex) {
            System.out.println("========================" + i);
            return null;
        }
        return house;
    }

    public int getPageCountPhongTro123(XMLStreamReader reader) {
        String page = "";
        try {
            boolean findLi = false;
            String temp = "";
            String rex = "[0-9]{1,}";
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("li".equals(tagName)) {
                        String className = reader.getAttributeValue("", "class");
                        if ("page-item".equals(className)) {
                            findLi = true;
                        }
                    }
                    if (findLi) {
                        if ("a".equals(tagName)) {
                            temp = reader.getElementText();
                            if (temp.matches(rex)) {
                                page = temp;
                            }
                            findLi = false;
                        }
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Integer.parseInt(page);
    }

//    private String getNumberFromString(String input) {
//        String output = "";
//        String rex = "[0-9]{1,}";
//        String rex2 = ".";
//        for (int i = 0; i < input.length(); i++) {
//            String tmp = input.substring(0, i);
//            if (tmp.matches(rex) || tmp.matches(rex2)) {
//                output = tmp;
//            }else{
//                return output;
//            }
//        }
//        return output;
//    }
    public ArrayList<String> getPhongTro123ListLink(XMLStreamReader reader) {
        ArrayList<String> listLink = new ArrayList<>();
        try {
            boolean findDiv = false;
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("div".equals(tagName)) {
                        String className = reader.getAttributeValue("", "class");
                        if ("post-info clearfix".equals(className)) {
                            findDiv = true;
                        }
                    }
                    if (findDiv && "a".equals(tagName)) {
                        String link = reader.getAttributeValue("", "href");
                        listLink.add(link);
                        findDiv = false;
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listLink;
    }

    private String areaSign = "Diện tích:";
    private String RENTIGNSIGN = "Giá cho thuê:";

    public House getPhongTro123Detail(XMLStreamReader reader, int i) {
        House house = new House();
        try {
            boolean findTitle = false;
            boolean findAddress = false;
            boolean findPhone = false;
            boolean findArea = false;
            boolean findAreaSign = false;
            boolean findRentingPrice = false;
            boolean findRentingPriceSign = false;
            boolean findImg = false;
            boolean findLat = false;
            boolean findLng = false;
            String address = "";

            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("h1".equals(tagName) && !findTitle) {
                        String className = reader.getAttributeValue("", "class");
                        if ("page-title".equals(className)) {
                            findTitle = true;
                            house.setName(reader.getElementText());
                        }
                    }
                    if (address.equals("") && !findAddress) {
                        if ("td".equals(tagName)) {
                            String colSpan = reader.getAttributeValue("", "colspan");
                            if (colSpan != null) {
                                findAddress = true;
                                address = reader.getElementText();
                                house.setAddress(address);
                            }
                        }
                    }
                    if ("a".equals(tagName) && !findPhone) {
                        String phone = reader.getAttributeValue("", "data-phone");
                        if (phone != null) {
                            findPhone = true;
                            house.setPhone(phone);
                        }
                    }
                    if (!findArea && findPhone && !findAreaSign) {
                        try {
                            String temp = reader.getElementText();
                            if (areaSign.equals(temp)) {
                                findAreaSign = true;
                            }
                        } catch (Exception e) {

                        }
                    }
                    if (findAreaSign && "span".equals(tagName)) {
                        findAreaSign = false;
                        findArea = true;
                        String temp = reader.getElementText();
                        //            String area = getNumberFromString(temp);
                        house.setArea(temp);
                    }
                    if (!findRentingPrice && findArea && !findRentingPriceSign) {
                        try {
                            String temp = reader.getElementText();
                            if (RENTIGNSIGN.equals(temp)) {
                                findRentingPriceSign = true;
                            }
                        } catch (Exception e) {

                        }
                    }
                    if (findRentingPriceSign && "span".equals(tagName)) {
                        findRentingPriceSign = false;
                        findRentingPrice = true;
                        String temp = reader.getElementText();
                        //         temp = getNumberFromString(temp);
                        house.setRentPrice(temp);
                    }
                    if ("img".equals(tagName) && !findImg) {
                        String alt = reader.getAttributeValue("", "alt");
                        if (alt != null) {
                            findImg = true;
                            house.setImage(reader.getAttributeValue("", "src"));
                        }
                    }
                    if ("div".equals(tagName) && !findLat) {
                        String dataLat = reader.getAttributeValue("", "data-lat");
                        if (dataLat != null) {
                            findLat = true;
                            house.setLatitude(BigDecimal.valueOf(Double.parseDouble(dataLat)));
                        }

                    }
                    if ("div".equals(tagName) && !findLng) {
                        String dataLong = reader.getAttributeValue("", "data-long");
                        if (dataLong != null) {
                            findLng = true;
                            house.setLongitude(BigDecimal.valueOf(Double.parseDouble(dataLong)));
                            break;
                        }
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(StAXParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("====" + i);
        }
        return house;
    }

    public Schools crawlSchool(XMLStreamReader reader) {
        Schools schools = new Schools();
        try {
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("school".equals(tagName)) {
                        String schoolName = reader.getElementText();
                        schoolName = TextUtils.getSchoolName(schoolName);
                        schools.getSchool().add(schoolName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schools;
    }
}
