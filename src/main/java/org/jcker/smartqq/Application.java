package org.jcker.smartqq;

import org.jcker.smartqq.callback.MessageCallback;
import org.jcker.smartqq.client.SmartQQClient;
import org.jcker.smartqq.model.DiscussMessage;
import org.jcker.smartqq.model.GroupMessage;
import org.jcker.smartqq.model.Message;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        SmartQQClient client = new SmartQQClient(new MessageCallback() {
            public void onMessage(Message message) {
                System.out.println(message.getContent());
            }

            public void onGroupMessage(GroupMessage message) {
                System.out.println(message.getContent());
            }

            public void onDiscussMessage(DiscussMessage message) {
                System.out.println(message.getContent());
            }
        });

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
