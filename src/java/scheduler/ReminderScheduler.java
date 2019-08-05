/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import dao.ReminderDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import model.Reminder;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import java.util.ArrayList;

/**
 *
 * @author Hoang Cao
 */
public class ReminderScheduler {

    private String rid;
    private String shortDes;
    private String sendDate;
    private Timer timer;
//    private static ArrayList<Timer> listTimer = new ArrayList<>();

    public ReminderScheduler(String rid, String shortDes, String sendDate) {
        this.rid = rid;
        this.shortDes = shortDes;
        this.sendDate = sendDate;
    }

//    public static void stopAllTimer() {
//        System.out.println("ReminderScheduler.java - listTimer.size() = " + listTimer.size());
//        for (int i = 0; i < listTimer.size(); i++) {
//            listTimer.get(i).stop();
//            System.out.println("ReminderScheduler.java - Is this timer running? " + listTimer.get(i).isRunning());
//
//        }
//        System.out.println("------------------------------");
//        System.out.println("ReminderScheduler.java - TIMER STOPS!");
//    }
    public void startScheduler() {
        timer = new Timer(100 * 60 * 1, new ActionListener() {
            {
                System.out.println("ReminderScheduler.java - Run only once when timer.");
            }

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    System.out.println("-------------------------");
                    System.out.println("ReminderScheduler.java - Reminder Scheduler is running...");
                    System.out.println("ReminderScheduler.java - RID = " + rid);
//                    System.out.println("ReminderScheduler.java - listTimer.size = " + listTimer.size());

                    // Trong trường hợp người dùng xoá reminder, thì timer nên dừng
                    if (new ReminderDAO().isReminderExist(rid) == 0) {
                        System.out.println("ReminderScheduler.java - Reminder is not exist with this rid: " + rid);
                        timer.stop();
                        return;
                    }

                    Reminder newRem = new ReminderDAO().selectReminderById(rid);

                    // Trong trường hợp người dùng thay đổi thông tin reminder, thì thay đổi thông tin tương ứng
                    if (newRem != null) {
                        if (!newRem.getShortDes().equals(shortDes)) {
                            shortDes = newRem.getShortDes();
                            System.out.println("ReminderScheduler.java - Change happens.");
                        }

                        if (!newRem.getDate().equals(sendDate)) {
                            sendDate = newRem.getDate();
                            System.out.println("ReminderScheduler.java - Change happens.");
                        }
                    }

                    Date now = new Date();
                    Date endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(sendDate);

                    String nowFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(now);
                    String endDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(endDate);
                    System.out.println("ReminderScheduler.java - Now: " + nowFormat);
                    System.out.println("ReminderScheduler.java - End Date: " + endDateFormat);

                    if (nowFormat.equals(endDateFormat)) {
                        // Sử dụng API để gửi
                        AuthMethod auth = new TokenAuthMethod("KEY", "VALUE"); // Lấy KEY và VALUE từ tài khoản demo Nexmo của bạn
                        NexmoClient client = new NexmoClient(auth);

                        TextMessage message = new TextMessage("Nexmo", "YOUR PHONE NUMBER TO RECEIVE SMS", "<--- SMS Reminder --->\nPlanned at " + endDateFormat + ".\nYour message: " + shortDes + ".");
                        SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(message);
                        for (SmsSubmissionResult response : responses) {
                            System.out.println(response);
                        }

                        // // Trong trường hợp gửi thành công reminder, xoá reminder tương ứng trong Database
                        new ReminderDAO().deleteReminderById(rid);
                        System.out.println("ReminderScheduler.java - Timer is stop!");
                        timer.stop();
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ReminderScheduler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ReminderScheduler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

//        listTimer.add(timer);
//        System.out.println("listTimer.size = " + listTimer.size());
        timer.start();
    }
}
