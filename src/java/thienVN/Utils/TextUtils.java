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

    public static String refineHtml(String src) {
        src = getBody(src);
        src = removeMiscellaneousTags(src);

        XmlSyntaxChecker checker = new XmlSyntaxChecker();
        src = checker.check(src);

        // crop one more times 
        src = getBody(src);
        return src;
    }

    public static String getBody(String src) {
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

    public static String getNumberFromString(String input) {
        String output = "";
        String rex2 = "[0-9]+(\\.)?";
        String rex3 = "[0-9]+(.[0-9]{1,})?";
        for (int i = 1; i <= input.length(); i++) {
            String tmp = input.substring(0, i);
            if (tmp.matches(rex3) || tmp.matches(rex2)) {
                output = tmp;
            } else {
                return output;
            }
        }
        return output;
    }

    public static String convertEntities(String text) {
        text = text.replace("ocirc;", "&#244;").replace("&amp;", "").replace("agrave;", "&#224;").replace("ograve;", "&#242;")
                .replace("acute;", "&#180;").replace("ndash;", "&#8211;");
     
        return text;
    }
    
    public static String getSchoolName(String name){
        int dotIndex = name.indexOf(".");
        String temp = name.substring(dotIndex + 2);
        return temp;
    }
}
