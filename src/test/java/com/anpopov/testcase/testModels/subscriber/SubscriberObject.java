package com.anpopov.testcase.testModels.subscriber;

import com.anpopov.testcase.testModels.MessageWithProperties;

public abstract class SubscriberObject {
    private boolean isMessageReceived;
    private MessageWithProperties object;

    public boolean isMessageReceived() {
        return isMessageReceived;
    }

    public void setMessageReceived(boolean messageReceived) {
        isMessageReceived = messageReceived;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(MessageWithProperties object) {
        this.object = object;
    }
}
