/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ReminderDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Reminder;

/**
 *
 * @author Hoang Cao
 */
public class LoadServlet extends HttpServlet {

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
        try {
            String pageName = request.getParameter("pageName");
            String url = "";

            if (pageName != null) {
                request.getSession().setAttribute("pageName", pageName);

                if (pageName.equals("Saved")) {
                    request.getSession().removeAttribute("responseMessage");
                    request.setAttribute("listReminders", new ReminderDAO().selectTop3Reminders());
                    url = "pages/saved-page.jsp";
                }

                if (pageName.equals("Main")) {
                    request.setAttribute("dateAndTime", this.getCurrentDateAndTime());
                    url = "pages/main-page.jsp";
                }

                if (pageName.equals("Edit")) {
                    Reminder rem = new ReminderDAO().selectReminderById(request.getParameter("remId"));

                    String formatDate = rem.getDate();
                    System.out.println("LoadServlet.java - formatDate = " + formatDate);
                    formatDate = formatDate.substring(0, 16).replaceFirst(" ", "T");
                    System.out.println("LoadServlet.java - formatDateSubString = " + formatDate);

                    rem.setDate(formatDate);
                    request.setAttribute("rem", rem);
                    request.setAttribute("currentDate", getCurrentDateAndTime());

                    url = "pages/edit-page.jsp";
                }
            }

            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(LoadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCurrentDateAndTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK);
        String strDate = dateFormat.format(date);

        System.out.println("LoadServlet.java - strDate orgin: " + strDate);
        String[] tempArr = strDate.split(" ");
        strDate = tempArr[0] + "T" + tempArr[1];
        System.out.println("LoadServlet.java - strDate = " + strDate);

        return strDate;
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
