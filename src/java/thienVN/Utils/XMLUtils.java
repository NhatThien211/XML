/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Utils;

import java.io.Serializable;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import xsd.thien.Houses;

/**
 *
 * @author ASUS
 */
public class XMLUtils implements Serializable {

    public static String marshallToString(Houses houses) {
        try {
            JAXBContext context = JAXBContext.newInstance(Houses.class);
            Marshaller mar = context.createMarshaller();
//            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            mar.marshal(houses, sw);

            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
