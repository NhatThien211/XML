/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Servlet;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import thienVN.DAO.HomeDAO;
import thienVN.Utils.XMLUtils;
import xsd.thien.Houses;

/**
 * Web application lifecycle listener.
 *
 * @author ASUS
 */
public class RequestServletListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("request destroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HomeDAO dao = new HomeDAO();
        try {
            System.out.println("request initialized!!");
            Houses houses = dao.getTop100Houses();
            String str = XMLUtils.marshallToString(houses);
            sre.getServletRequest().setAttribute("INFO", str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
