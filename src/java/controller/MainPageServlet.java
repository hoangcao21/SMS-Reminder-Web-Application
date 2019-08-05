/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ReminderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Reminder;
import scheduler.ReminderScheduler;

/**
 *
 * @author Hoang Cao
 */
public class MainPageServlet extends HttpServlet {

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
            String shortDes = request.getParameter("shortDes");
            String dateAndTime = request.getParameter("dateAndTime");
            String responseMessage = "";
            boolean checked = false;

            // Cần phải check nếu đã có 3 reminder, trả về tin nhắn là đã full
            if (new ReminderDAO().countReminders() == 3) {
                responseMessage = "Only 3 reminders allowed!";
                checked = true;
            }

            if (shortDes != null && !shortDes.equals("") && dateAndTime != null && !dateAndTime.equals("")) {
                if (checked == false) {
                    System.out.println("MainPageServlet.java - Check dateAndTime = " + dateAndTime.replaceFirst("T", " "));

                    // ID được tính toán bằng sử dụng hàm COUNT sql cộng với 1
                    String rid = "R" + (new ReminderDAO().countReminders() + 1);
                    new ReminderDAO().insertReminder(new Reminder(rid, shortDes, dateAndTime.replaceFirst("T", " ")));

                    // Sinh ra một luồng độc lập để lập lịch gửi reminder
                    new ReminderScheduler(rid, shortDes, dateAndTime).startScheduler();

                    responseMessage = "A SMS reminder was added!";
                }
            }

            request.getSession().setAttribute("responseMessage", responseMessage);
            request.getRequestDispatcher("pages/top-page.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(MainPageServlet.class.getName()).log(Level.SEVERE, null, ex);
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
