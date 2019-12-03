package thienVN.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

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
       String a = "";
        if (href != null) {
            try {
                URL url = new URL(href);
                URLConnection connection = url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla 5.0 (Window; U; Windows NT 5.1; en-US; rv:1.8.0.11) ");
                InputStream inputStream = connection.getInputStream();
                StreamSource ss = preProcessInputStream(inputStream);
                return ss;
            } catch (MalformedURLException ex) {
                Logger.getLogger(UtimateURIResolver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UtimateURIResolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private StreamSource preProcessInputStream(InputStream httpResult) throws UnsupportedEncodingException, IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(httpResult, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("<html") && !line.contains("xmlns=\"http://www.w3.org/1999/xhtml\"")) {
                line = line.replace("<html", "<html xmlns=\"http://www.w3.org/199/xhtml\"");
            }
            if (line.contains("src") || line.contains("href")) {
                line = line.replace("&", "&amp;");
            }
            line = line.replace("&reg;", "&#174;").replace("&hellip;", "").replace("&nbsp;", "").replace("&aacute;", "&#225;").replace("&agrave", "&#224;");
            sb.append(line + "\n");
        }
        String temp = sb.toString();
        //   temp = CrawlHelper.getBody("<div Class=\"leftCol\"", "<aside", temp);
        int begin = temp.indexOf(String.valueOf("<div class=\"leftCol"));
        int end = temp.indexOf(String.valueOf("<aside"));
        temp = temp.substring(begin, end);
        temp = CrawlHelper.removeMiscellaneousTags(temp);
        temp = CrawlHelper.fixString(temp);
        String newString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<html xmlns=\"http://www.w3.org/199/xhtml\">";
        newString = newString + temp +"</html>";
        InputStream htmlResult = new ByteArrayInputStream(newString.getBytes(StandardCharsets.UTF_8));
        return new StreamSource(htmlResult);
    }

    public static XPath getXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        return xpath;
    }
}
