/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.JaxB;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import thienVN.Common.Constraint;

/**
 *
 * @author ASUS
 */
public class JAXBMarshall {
    public static void createHomeXMLFile(Houses dto, String realPath){
        try{
            JAXBContext ctx = JAXBContext.newInstance(Houses.class);
            Marshaller mar = ctx.createMarshaller();
            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(dto, new File(realPath + Constraint.JAXB_XML_HOUSE_FOR_PDF));
        }catch(Exception e){
            
        }
    }
}
