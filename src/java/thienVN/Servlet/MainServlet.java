/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thienVN.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {

    private final String SEARCH = "SearchServlet";
    private final String CRAWL = "CrawlServlet";
    private final String DETAIL = "DetailServlet";
    private final String FIND_HOME = "FindHomeServlet";
    private final String LOGIN = "LoginServlet";
    private final String CRAWL_SCHOOL = "CrawlSchoolServlet";
    private final String PRINT_PDF = "PrintPDFServlet";
    private final String LOGOUT = "LogoutServlet";

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
        String url = "";
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "search":
                    url = SEARCH;
                    break;
                case "crawl":
                    url = CRAWL;
                    break;
                case "detail":
                    url = DETAIL;
                    break;
                case "find":
                    url = FIND_HOME;
                    break;
                case "login":
                    url = LOGIN;
                    break;
                case "crawlSchool":
                    url = CRAWL_SCHOOL;
                    break;
                case "printPDF":
                    url = PRINT_PDF;
                    break;
                case "logout":
                    url = LOGOUT;
                    break;
            }
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
