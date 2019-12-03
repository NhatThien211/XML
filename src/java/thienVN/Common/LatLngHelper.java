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
public class LatLngHelper {

    public static LatLng convertGPSCoordinatesToLatLng(String latGPS, String lngGPS) {
        double lat = convert(latGPS);
        double lng = convert(lngGPS);
        LatLng latlng = new LatLng(lat, lng);
        return latlng;
    }

    private static double convert(String angle) {
        int dec = angle.indexOf("°");
        int mins = angle.indexOf("'");
        int subs = angle.indexOf(".");
        int deg = Integer.parseInt(angle.substring(0, dec));
        int min = Integer.parseInt(angle.substring(dec + 1, mins));
        int sec = Integer.parseInt(angle.substring(mins+ 1, subs));
        int sub = Integer.parseInt(angle.substring(subs+1, angle.length()-1));
        String hem = angle.substring(angle.length()-1);

        double value = deg + min / 60.0f + sec / 3600.0f + sub / 360000.0f;
        double sign = 0.0;
        if ("S".equals(hem)) {
            sign = -1.0;
        } else {
            sign = 1.0;
        }
        return sign * value;
    }

    public static void main(String[] args) {
        convertGPSCoordinatesToLatLng("10°50'07.7N", "106°39'37.2E");
    }
}
