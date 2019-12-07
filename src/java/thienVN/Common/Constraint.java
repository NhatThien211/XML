/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Common;

/**
 *
 * @author ASUS
 */
public class Constraint {

    public static final String BDS123_URL = "https://bds123.vn/";
    public static final String Link_TEXT = "Cho thuê phòng trọ, nhà trọ";

    public static final String BDS_XML = "WEB-INF/xml/school.xml";
    public static final String BDS_XSL = "WEB-INF/xsl/school.xsl";
    public static final String BDS_XML_OUTPUT = "WEB-INF/output/school_rs.xml";
    // PDF
    public static final String JAXB_XML_HOUSE_FOR_PDF = "WEB-INF/output/house.xml";
    public static final String JAXB_XSL_HOUSE_FOR_PDF = "WEB-INF/xsl/house.xsl";
    public static final String JAXB_FO_HOUSE_FOR_PDF = "WEB-INF/fo/house.fo";
    //schema for jaxb
    public static final String HOUSE_SCHEMA = "WEB-INF/schema/ListHouse.xsd";
    public static final String SCHOOL_SCHEMA = "WEB-INF/schema/school.xsd";

    public static final String PHONGTRO123_URL = "https://phongtro123.com/";

    //page
    public static final int BDS_PAGE_NUMBER = 1;
    public static final int PHONGTRO123_PAGE_NUMBER = 1;

    //distance
    public static final float RADIUS = 10;
    public static final String WATER_UNIT_M3 = "đồng/m";
    public static final String WATER_UNIT_ALL = "đồng/người";
    public static final float AVERAGE_WATER = 6; //6m3 in a month 
    public static final String ELECTRIC_UNIT_ALL = "đồng/Kwh";
    public static final String RENT_PRICE_DEFAULT = "2";
    
    //weighted
    public static final int FIRST_PRIORITY = 5;
    public static final int SECOND_PRIORITY = 3;
    public static final int THIRD_PRIORITY = 2;
    
}
