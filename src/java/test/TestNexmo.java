package test;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

public class TestNexmo {

    public static void main(String[] args) throws Exception {
        AuthMethod auth = new TokenAuthMethod("KEY", "VALUE");
        NexmoClient client = new NexmoClient(auth);

        TextMessage message = new TextMessage("Nexmo", "YOUR PHONE NUMBER TO RECEIVE SMS", "Hello from Nexmo!");
        SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(message);
        for (SmsSubmissionResult response : responses) {
            System.out.println(response);
        }
    }
}
