/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author ASUS
 */
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String realPath = sce.getServletContext().getRealPath("/");
//        createBDSXMLFile(realPath);
//        createDBSHomeFile(realPath + Constraint.BDS_XML_OUTPUT, realPath + Constraint.BDS_XSL, realPath + Constraint.BDS_XML_SOURCE_OUTPUT);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void createBDSXMLFile(String realPath) {
//        String link = getBDSLink();
//        Houses house = new Houses();
//        house.setLink(link);
//        house.setXmlns("test");
//        house.setHost(Constraint.BDS123_URL);
//        try {
//            JAXBContext context = JAXBContext.newInstance(Houses.class);
//            Marshaller mar = context.createMarshaller();
//            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            //  mar.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
//            mar.marshal(house, new File(realPath + Constraint.BDS_XML_OUTPUT));
//        } catch (JAXBException ex) {
//            Logger.getLogger(MyServletContextListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

//    private void createDBSHomeFile(String xml, String xsl, String output) {
//        try {
//            //run crawler
//            DOMResult rs = Crawler.crawl(xml, xsl);
//            //init transformer
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer transformer = factory.newTransformer();
//            StreamResult sr = new StreamResult(new FileOutputStream(output));
//            //tranform to xml file
//            transformer.transform(new DOMSource(rs.getNode()), sr);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//    private void createHouseObject(String realPath) {
//        String output = realPath + "WEB-INF/output";
//        SchemaCompiler sc = XJC.createSchemaCompiler();
//        sc.setErrorListener(new ErrorListener() {
//            @Override
//            public void error(SAXParseException saxpe) {
//                saxpe.printStackTrace();
//            }
//
//            @Override
//            public void fatalError(SAXParseException saxpe) {
//                saxpe.printStackTrace();
//            }
//
//            @Override
//            public void warning(SAXParseException saxpe) {
//                saxpe.printStackTrace();
//            }
//
//            @Override
//            public void info(SAXParseException saxpe) {
//                saxpe.printStackTrace();
//            }
//        });
//
//        sc.forcePackageName("thienVN.JaxB");
//        File schema = new File(realPath + "WEB-INF/schema/ListHouse.xsd");
//        InputSource is = new InputSource(schema.toURI().toString());
//        sc.parseSchema(is);
//        S2JJAXBModel model = sc.bind();
//        JCodeModel code = model.generateCode(null, null);
//        try {
//            code.build(new File(output));
//        } catch (IOException ex) {
//            Logger.getLogger(MyServletContextListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
