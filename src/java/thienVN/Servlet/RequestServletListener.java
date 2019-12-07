/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Servlet;

import java.util.ArrayList;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import thienVN.DAO.HomeDAO;
import thienVN.JaxB.Houses;
import thienVN.JaxB.UniversityDTO;
import thienVN.Utils.XMLUtils;

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
            ArrayList<UniversityDTO> listUniversity = new ArrayList<>();
            listUniversity.add(new UniversityDTO(0, "KHÔNG CÓ"));
            listUniversity.addAll(dao.getListUniversity());
            sre.getServletRequest().setAttribute("UNIVERSITY", listUniversity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
