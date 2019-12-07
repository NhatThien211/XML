/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thienVN.Common.Constraint;
import thienVN.DAO.HomeDAO;
import thienVN.JaxB.HouseDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "FindHomeServlet", urlPatterns = {"/FindHomeServlet"})
public class FindHomeServlet extends HttpServlet {

    private final String URL = "suggestion.jsp";
    private final String DISTANCE = "distance";
    private final String AREA = "area";
    private final String PRICE = "price";
   ArrayList<String> priority = new ArrayList<>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            String university = request.getParameter("txtUniversity");
            String[] temp = university.split(",");
            String firstPriotity = request.getParameter("first");
            String secondPriotity = request.getParameter("second");
            priority.add(firstPriotity);
            priority.add(secondPriotity);
            addLastPriority();
            int number = 0;
            HomeDAO dao = new HomeDAO();
            ArrayList<HouseDTO> listHouse = dao.findHome(Float.parseFloat(latitude), Float.parseFloat(longitude), Constraint.RADIUS, getPriority(DISTANCE), getPriority(AREA), getPriority(PRICE));
            for (HouseDTO houseDTO : listHouse) {
                // add homemate
                number = dao.getNumberHomemate(houseDTO.getId(), Integer.parseInt(temp[0]));
                if (number > 0) {
                    houseDTO.setNumberHomemate(number);
                    float point = houseDTO.getPoint();
                    houseDTO.setPoint(point + caculatePointForHomemate(number));
                }
            }
            Collections.sort(listHouse);
            ArrayList<HouseDTO> result = new ArrayList<>();
            int size = 0;
            if (listHouse.size() >= 3) {
                size = 3;
            } else {
                size = listHouse.size();
            }
            for (int i = 0; i < size; i++) {
                result.add(listHouse.get(i));
            }
            request.setAttribute("RESULT", result);
            request.setAttribute("LISTHOME", listHouse);
            if (!temp[0].equals("0")) {
                request.setAttribute("UNIVERITY_NAME", temp[1]);
                request.setAttribute("UNIVERITY_ID", temp[0]);
            }
            request.setAttribute("LATITUDE", latitude);
            request.setAttribute("LONGITUDE", longitude);
            HttpSession session = request.getSession();
            session.setAttribute("TOP3", result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(URL).forward(request, response);
        }
    }

    private void addLastPriority() {
        if(!priority.contains(DISTANCE)){
            priority.add(DISTANCE);
        }else if(!priority.contains(AREA)){
            priority.add(AREA);
        }else if(!priority.contains(PRICE)){
            priority.add(PRICE);
        }
    }
    
    private int getPriority(String name){
        switch (priority.indexOf(name)) {
            case 0:
                return Constraint.FIRST_PRIORITY;
            case 1:
                return Constraint.SECOND_PRIORITY;
            case 2:
                return Constraint.THIRD_PRIORITY;
            default:
                break;
        }
        return 1;
    }
    
    private float caculatePointForHomemate(int homemateNumber){
       return homemateNumber /10;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
