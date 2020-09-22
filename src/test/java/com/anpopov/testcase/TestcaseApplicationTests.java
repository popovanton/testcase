package com.anpopov.testcase;

import com.anpopov.testcase.annotation.Subscriber;
import com.anpopov.testcase.service.NotificationService;
import com.anpopov.testcase.subscriber.IEventSubscriber;
import com.anpopov.testcase.testModels.MessageWithProperties;
import com.anpopov.testcase.testModels.subscriber.SubscriberObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TestcaseApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testOkMessageReceivedAndObjectIsRetrievedThemeIsCars() {
        NotificationService notificationService =
                (NotificationService) applicationContext.getBean("notificationServiceImpl");

        MessageWithProperties messageWithProperties = new MessageWithProperties("anton", "popov");
        notificationService.notify("auto", messageWithProperties);

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Subscriber.class);
        Object[] objects = beansWithAnnotation.values().toArray();
        for (Object object : objects) {
            IEventSubscriber subscriber = (IEventSubscriber) object;
            String[] themes = subscriber.getClass().getAnnotation(Subscriber.class).themes();
            SubscriberObject subscriberObject = (SubscriberObject) object;
            if (themes[0].equals("auto")) {
                assertTrue(subscriberObject.isMessageReceived());
                assertEquals(subscriberObject.getObject(), messageWithProperties);
            } else {
                assertFalse(subscriberObject.isMessageReceived());
                assertNull(subscriberObject.getObject());
            }
        }
    }

    @Test
    public void testOkMessageReceivedAndObjectIsRetrievedThemeIsSport() {
        NotificationService notificationService =
                (NotificationService) applicationContext.getBean("notificationServiceImpl");

        MessageWithProperties messageWithProperties = new MessageWithProperties("anton", "popov");
        notificationService.notify("sport", messageWithProperties);

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Subscriber.class);
        Object[] objects = beansWithAnnotation.values().toArray();
        for (Object object : objects) {
            IEventSubscriber subscriber = (IEventSubscriber) object;
            String[] themes = subscriber.getClass().getAnnotation(Subscriber.class).themes();
            SubscriberObject subscriberObject = (SubscriberObject) object;
            if (themes[0].equals("sport")) {
                assertTrue(subscriberObject.isMessageReceived());
                assertEquals(subscriberObject.getObject(), messageWithProperties);
            } else {
                assertFalse(subscriberObject.isMessageReceived());
                assertNull(subscriberObject.getObject());
            }
        }
    }

}
