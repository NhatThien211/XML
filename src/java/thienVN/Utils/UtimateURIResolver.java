package thienVN.Utils;

import java.io.InputStream;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import thienVN.parser.StAXParser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ASUS
 */
public class UtimateURIResolver implements URIResolver {

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (href != null) {
            StAXParser parse = new StAXParser();
            InputStream is = parse.getStreamFromUriStateMachineUTF8(href);
            StreamSource ss = new StreamSource(is);
            return ss;
        }
        return null;
    }
}
