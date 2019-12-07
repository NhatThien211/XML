/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import thienVN.Common.Constraint;
import thienVN.DAO.HomeDAO;
import thienVN.DAO.SchoolDAO;
import thienVN.JaxB.JaxBHouseValidationHandler;
import thienVN.Utils.CrawlHelper;
import thienVN.Utils.UtimateURIResolver;
import thienVN.parser.StAXParser;
import xsd.thien.House;
import xsd.thien.Houses;
import xsd.thien.Schools;

/**
 *
 * @author ASUS
 */
public class Crawler {

    HomeDAO dao = new HomeDAO();

    public static DOMResult crawl(String configPath, String xslPath) throws FileNotFoundException, TransformerConfigurationException, TransformerException {
        // init file 
        StreamSource xslCate = new StreamSource(xslPath);
        InputStream is = new FileInputStream(configPath);
        //init transfomer API
        TransformerFactory factory = TransformerFactory.newInstance();
        DOMResult domRs = new DOMResult();
        UtimateURIResolver resolver = new UtimateURIResolver();
        //apply uriResolver
        factory.setURIResolver(resolver);
        Transformer transformer = factory.newTransformer(xslCate);
        transformer.transform(new StreamSource(is), domRs);
        return domRs;
    }

    private static String getBDSLink() {
        StAXParser parser = new StAXParser();
        InputStream stream = parser.getStreamFromUri(Constraint.BDS123_URL);
        stream = CrawlHelper.preProcessInputStream(stream, "<body", "</body>");
        XMLStreamReader reader = parser.getReader(stream);
        //https://bds123.vn/cho-thue-phong-tro-nha-tro.html
        String link = parser.getBDSLink(reader);
        return link;
    }

    private static int getDBSHomePageCount() {
        String url = getBDSLink();
        StAXParser parser = new StAXParser();
        InputStream is = parser.getStreamFromUri(url);
        is = CrawlHelper.preProcessInputStream(is, "<div class=\"leftCol", "<aside class=\"rightCol\">");
        XMLStreamReader reader = parser.getReader(is);
        int pageCount = parser.getPageCountDBS(reader);
        return pageCount;
    }

    private static int getPageCount(String url, int guess) {
        int count = 0;
        StAXParser parser = new StAXParser();
        int i = 0;
        InputStream is = parser.getStreamFromUri(url + "?page=" + guess);
        if (is != null) {
            return guess;
        }
        while (true) {
            i++;
            System.out.println(i);
            is = parser.getStreamFromUri(url + "?page=" + i);
            if (is == null) {
                return count;
            }
            count++;
        }

    }

    private static ArrayList<String> getListLinkHomeDBS() {
        ArrayList<String> listLink = new ArrayList<>();
        String url = getBDSLink();
        int pageCount = getPageCount(url, Constraint.BDS_PAGE_NUMBER);
        for (int i = 1; i <= pageCount; i++) {
            String tempUrl = url + "?page=" + i;
            StAXParser parser = new StAXParser();
            InputStream is = parser.getStreamFromUri(tempUrl);
            is = CrawlHelper.preProcessInputStream(is, "<div class=\"leftCol", "<aside class=\"rightCol\">");
            XMLStreamReader reader = parser.getReader(is);
            listLink.addAll(parser.getDBSHomePage(reader));
        }
        return listLink;
    }

    public void getAllHomeDetail(String realPath) throws JAXBException, SAXException, SAXException, SAXException, IOException {
        ArrayList<String> listLink = getListLinkHomeDBS();
        Houses listHouse = new Houses();
        StAXParser parser = new StAXParser();
        for (int i = 0; i < listLink.size(); i++) {
            String url = listLink.get(i);
            InputStream stream = parser.getStreamFromUri(url);
            stream = CrawlHelper.preProcessInputStream(stream, "<article class=\"RealEstate_Detail\"", "</article>");
            XMLStreamReader reader = parser.getReader(stream);
            House house = parser.getHomeDetail(reader, i);
            if (house != null) {
                house.setUrl(url);
                listHouse.getHouse().add(house);
                System.out.println("finished get house DBS " + (i + 1));
            }
        }
        validateHouse(listHouse, realPath);
        listHouse = dao.checkBeforeInsert(listHouse);
        insertDB(listHouse);
    }

    public static void validateHouse(Houses houses, String realPath) throws JAXBException, SAXException, IOException {
        JAXBContext context = JAXBContext.newInstance(Houses.class);
        JAXBSource source = new JAXBSource(context, houses);

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(realPath + Constraint.HOUSE_SCHEMA));

        Validator validator = schema.newValidator();
        JaxBHouseValidationHandler handler = new JaxBHouseValidationHandler();
        validator.setErrorHandler(handler);
        validator.validate(source);
    }

    private static int phongTro123PageCount() {
        StAXParser parser = new StAXParser();
        InputStream is = parser.getStreamFromUri(Constraint.PHONGTRO123_URL);
        is = CrawlHelper.preProcessInputStream(is, "<ul class=\"pagination", "</ul>");
        XMLStreamReader reader = parser.getReader(is);
        int pageCount = parser.getPageCountPhongTro123(reader);
        return pageCount;
    }

    public static ArrayList<String> getListLinkHomePhongTro123() {
        ArrayList<String> listLink = new ArrayList<>();
        //    int pageCount = phongTro123PageCount();
        for (int i = 1; i <= Constraint.PHONGTRO123_PAGE_NUMBER; i++) {
            String tempUrl = Constraint.PHONGTRO123_URL + "?page=" + i;
            StAXParser parser = new StAXParser();
            InputStream is = parser.getStreamFromUriStateMachine(tempUrl);
            XMLStreamReader reader = parser.getReader(is);
            listLink.addAll(parser.getPhongTro123ListLink(reader));
        }
        return listLink;
    }

    public void getPhongTro123Detail(String realPath) throws JAXBException, SAXException, IOException {
        ArrayList<String> listLink = getListLinkHomePhongTro123();
        Houses listHouse = new Houses();
        StAXParser parser = new StAXParser();
        for (int i = 0; i < listLink.size(); i++) {
            String url = listLink.get(i);
            InputStream is = parser.getStreamFromUriStateMachine(url);
            XMLStreamReader reader = parser.getReader(is);
            House house = parser.getPhongTro123Detail(reader, i);
            house.setUrl(url);
            listHouse.getHouse().add(house);
            System.out.println("finish get house Phong tro: " + i);
        }
        validateHouse(listHouse, realPath);
        listHouse = dao.checkBeforeInsert(listHouse);
        insertDB(listHouse);
    }

    private void insertDB(Houses listHouses) {
        for (House dto : listHouses.getHouse()) {
            try {
                boolean check = dao.isHomeExisted(dto);
                if (!check) {
                    dao.insertHouse(dto);
                } else {
                    dao.updateHouse(dto);
                }
            } catch (Exception e) {
                System.out.println("insert fail: " + dto.toString());
            }
        }
        System.out.println("finished INSERT ======================================================");
    }

    public void getSchool(String realPath) {
        FileInputStream inpputFile = null;
        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            File file = new File(realPath + Constraint.BDS_XML_OUTPUT);
            inpputFile = new FileInputStream(file);
            XMLStreamReader reader = xif.createXMLStreamReader(inpputFile);
            StAXParser parser = new StAXParser();
            Schools schools = parser.crawlSchool(reader);
            validateSchool(schools, realPath);
            insertSchool(schools);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inpputFile != null) {
                try {
                    inpputFile.close();
                } catch (IOException ex) {
                    Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void validateSchool(Schools schools, String realPath) throws JAXBException, SAXException, IOException {
        JAXBContext context = JAXBContext.newInstance(Schools.class);
        JAXBSource source = new JAXBSource(context, schools);

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(realPath + Constraint.SCHOOL_SCHEMA));

        Validator validator = schema.newValidator();
        JaxBHouseValidationHandler handler = new JaxBHouseValidationHandler();
        validator.setErrorHandler(handler);
        validator.validate(source);
    }

    private void insertSchool(Schools schools) {
        SchoolDAO dao = new SchoolDAO();
        for (String schoolName : schools.getSchool()) {
            try {
                boolean check = dao.isSchoolExisted(schoolName);
                if (!check) {
                    dao.insertSchool(schoolName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
