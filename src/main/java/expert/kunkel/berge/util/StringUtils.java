/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.kunkel.berge.util;

/**
 *
 * @author thorsten
 */
public class StringUtils {

    public static String encodeHTML(String s) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>') {
                out.append("&#" + (int) c + ";");
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
