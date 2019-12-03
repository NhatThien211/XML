/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ASUS
 */
public class CrawlHelper {

    // STACK
    public static boolean isAlphaChar(char x) {
        return (x >= 'a' && x <= 'z');
    }

    public static String getTagName(String content) {
        if (content.charAt(content.length() - 2) == '/') {
            return null;
        }
        String res = "";
        int i = 1;
        if (content.charAt(i) == '/') {
            res = res + '/';
            i++;
        }
        while (isAlphaChar(content.charAt(i))) {
            res = res + content.charAt(i);
            i++;
        }
        if (res.length() == 0 || (res.length() == 1 && res.charAt(0) == '/')) {
            return null;
        }
        return res;
    }

    public static String fixString(String content) {
        List<String> stack = new ArrayList<String>();
        List<Integer> li = new ArrayList<>();
        List<String> addTag = new ArrayList<>();

        int sz = content.length();
        int mark[] = new int[sz];
        Arrays.fill(mark, -1);
        int i = 0;
        while (i < content.length()) {
            if (content.charAt(i) == '<') {
                int j = i + 1;

                String tagTmp = "" + content.charAt(i);

                while (j < content.length() && content.charAt(j) != '>') {
                    tagTmp = tagTmp + content.charAt(j);
                    j++;
                }
                int curEnd = j;
                tagTmp = tagTmp + '>';
                i = j + 1;
                String tag = getTagName(tagTmp);
                if (tag != null) {
                    if (tag.charAt(0) != '/') {
                        stack.add(tag);
                        li.add(curEnd);
                    } else {
                        while (stack.size() > 0) {
                            if (stack.get(stack.size() - 1).equals(tag.substring(1))) {
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                                break;
                            } else {
                                // need to
                                addTag.add(stack.get(stack.size() - 1));
                                mark[li.get(li.size() - 1)] = addTag.size() - 1;
                                // remove
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                            }
                        }
                    }
                }
            } else {
                i++;
            }
        }
        while (stack.size() > 0) {
            // need to
            addTag.add(stack.get(stack.size() - 1));
            mark[li.get(li.size() - 1)] = addTag.size() - 1;
            // remove
            stack.remove(stack.size() - 1);
            li.remove(li.size() - 1);
        }
        String newContent = "";
        for (int j = 0; j < content.length(); j++) {
            newContent = newContent + content.charAt(j);
            if (mark[j] != -1) {
                newContent = newContent + "</" + addTag.get(mark[j]) + ">";
            }
        }
        return newContent;
    }

    // Hashing
    public static int hashingString(String content) {
        int mod = 1000000007;
        int base = 30757;
        int hashValue = 0;
        for (int i = 0; i < content.length(); i++) {
            hashValue = (int) (((long) hashValue * base * (long) content.charAt(i)) % mod);
        }
        return hashValue;
    }

    public static String getBody(String startStr, String endStr, String src) {
        String result = src;

        String expression = startStr + ".*?" + endStr;
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

        return result;
    }

    public static InputStream preProcessInputStream(InputStream httpResult, String begin, String end) {
        try {
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
            temp = CrawlHelper.getBody(begin, end, temp);
            temp = CrawlHelper.removeMiscellaneousTags(temp);
            temp = temp.replace("< 3", " ");
            temp = CrawlHelper.fixString(temp);
            temp = CrawlHelper.fixString(temp);
            String newString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<html xmlns=\"http://www.w3.org/199/xhtml\">";
            newString = newString + temp + "</html>";
            InputStream result = new ByteArrayInputStream(newString.getBytes(StandardCharsets.UTF_8));
            return result;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CrawlHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
