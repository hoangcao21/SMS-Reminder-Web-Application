/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import model.Reminder;

/**
 *
 * @author Hoang Cao
 */
public class ReminderDAO {

    Connection con;

    public ReminderDAO() throws Exception {
        con = DBContext.getConnection();
    }

    public void insertReminder(Reminder re) throws Exception {
        String sql = "INSERT INTO [dbo].[Reminder]\n"
                + "  ([rid], [shortDes]\n"
                + "   ,[date])"
                + "VALUES(?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, re.getId());
            ps.setString(2, re.getShortDes());
            ps.setString(3, re.getDate());

            ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            DBContext.closeAllResources(con, ps, null);
        }
    }

    public void deleteReminderById(String id) throws Exception {
        String sql = "DELETE FROM [dbo].[Reminder] WHERE rid = ?";

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);

            ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            DBContext.closeAllResources(con, ps, null);
        }
    }

    public void updateReminderById(Reminder re) throws Exception {
        String sql = "UPDATE [dbo].[Reminder] SET shortDes = ?, date = ? WHERE rid = ?";

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, re.getShortDes());
            ps.setString(2, re.getDate());
            ps.setString(3, re.getId());

            ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            DBContext.closeAllResources(con, ps, null);
        }
    }

    public ArrayList<Reminder> selectTop3Reminders() throws Exception {
        ArrayList<Reminder> listReminders = new ArrayList<>();

        String sql = "SELECT TOP (3) [rid]\n"
                + "      ,[shortDes]\n"
                + "      ,FORMAT([date],'MM/dd/yyyy hh:mm tt') as [date]\n"
                + "  FROM [SMSReminder].[dbo].[Reminder]";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("rid");
                String shortDes = rs.getString("shortDes");
                String date = rs.getString("date");

                System.out.println("check date: " + date);

                listReminders.add(new Reminder(id, shortDes, date));
            }

            return listReminders;
        } catch (Exception ex) {
            throw ex;
        } finally {
            DBContext.closeAllResources(con, ps, rs);
        }
    }

    public Reminder selectReminderById(String rid) throws Exception {
        Reminder rem = null;

        String sql = "SELECT * FROM [dbo].[Reminder] WHERE rid = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, rid);

            rs = ps.executeQuery();

            if (rs.next()) {
                String id = rs.getString("rid");
                String shortDes = rs.getString("shortDes");
                String date = rs.getString("date").substring(0, 19);
                System.out.println("check date: " + date);

                rem = new Reminder(id, shortDes, date);
            }

            return rem;
        } catch (Exception ex) {
            throw ex;
        } finally {
            DBContext.closeAllResources(con, ps, rs);
        }
    }

    public int isReminderExist(String rid) throws Exception {
        String sql = "SELECT COUNT(1)\n"
                + "FROM dbo.Reminder\n"
                + "WHERE rid = ?";

        int count = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, rid);

            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
                System.out.println("isReminderExist = " + count);
            }

            return count;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public int countReminders() throws Exception {

        String sql = "SELECT COUNT(rid) FROM dbo.Reminder";

        int count = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
                System.out.println("count = " + count);
            }

            return count;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
