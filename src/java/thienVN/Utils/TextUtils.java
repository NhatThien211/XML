/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import xmlChecker.XmlSyntaxChecker;

/**
 *
 * @author DATTTSE62330
 */
public class TextUtils {
    public static String refineHtml(String src){
        src = getBody(src);
        src = removeMiscellaneousTags(src);
        
        XmlSyntaxChecker checker = new XmlSyntaxChecker();
        src = checker.check(src);
        
        // crop one more times 
        src = getBody(src);
        return src;
    }
    public static String getBody(String src){
        String result = src; 
        
        String expression = "<body.*?</body>";
        Pattern pattern = Pattern.compile(expression);
        
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            result = matcher.group(0);
        }
        return result;
    }
    public static String removeMiscellaneousTags(String src) {
        String result = src; 
        
        // REMOVE ALL SCRIPT TAG 
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");
        
        // REMOVE ALL COMMENTS
        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        
        // REMOVE ALL WHITESHPACE;
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");
        
        return result;
    }
}
