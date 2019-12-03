/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.JaxB;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author ASUS
 */
public class JaxBHouseValidationHandler implements ErrorHandler {

//    @Override
//    public boolean handleEvent(ValidationEvent event) {
//        if (event.getSeverity() == ValidationEvent.FATAL_ERROR
//                || event.getSeverity() == ValidationEvent.ERROR) {
//            ValidationEventLocator locator = event.getLocator();
//            System.out.println("Invalid House: " + locator.getURL());
//            System.out.println("Error:" + event.getMessage());
//            System.out.println("Error at collumn " + locator.getColumnNumber() + ", line " + locator.getLineNumber());
//        }
//        return true;
//    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {

    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println("Invalid House: ");
        System.out.println("Error:" + exception.getMessage());
        System.out.println("Error at collumn " + exception.getColumnNumber() + ", line " + exception.getLineNumber());
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("Invalid House: ");
        System.out.println("Error:" + exception.getMessage());
        System.out.println("Error at collumn " + exception.getColumnNumber() + ", line " + exception.getLineNumber());
    }

}
