package org.jcker.smartqq.callback;

import org.jcker.smartqq.model.DiscussMessage;
import org.jcker.smartqq.model.GroupMessage;
import org.jcker.smartqq.model.Message;

public interface MessageCallback
{
    void onMessage(Message paramMessage);

    void onGroupMessage(GroupMessage paramGroupMessage);

    void onDiscussMessage(DiscussMessage paramDiscussMessage);
}
