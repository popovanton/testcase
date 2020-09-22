package com.anpopov.testcase.testModels.subscriber;

import com.anpopov.testcase.annotation.Subscriber;
import com.anpopov.testcase.subscriber.IEventSubscriber;
import com.anpopov.testcase.testModels.MessageWithProperties;
import org.springframework.stereotype.Component;

@Component
@Subscriber(themes = {"sport"})
public class SportSubscriber extends SubscriberObject implements IEventSubscriber {

    @Override
    public void onEvent(String name, Object obj) {
        super.setMessageReceived(true);
        if (name.equals("sport")) {
            super.setObject((MessageWithProperties) obj);
        }
    }
}
