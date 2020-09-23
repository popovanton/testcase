package com.anpopov.testcase.service;

import com.anpopov.testcase.annotation.Subscriber;
import com.anpopov.testcase.subscriber.IEventSubscriber;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class NotificationServiceImpl implements NotificationService, ApplicationListener<ApplicationReadyEvent>, BeanPostProcessor {

    private final ApplicationContext applicationContext;
    private final Map<String, List<IEventSubscriber>> annotatedBeans = new HashMap<>();

    private NotificationServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Async
    public void notify(String name, Object message) {
        List<IEventSubscriber> iEventSubscribers = annotatedBeans.get(name);
        iEventSubscribers.parallelStream().forEach(it -> {
            it.onEvent(name, message);
        });
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof IEventSubscriber) {
            IEventSubscriber castedBean = (IEventSubscriber) bean;
            Subscriber annotation = bean.getClass().getAnnotation(Subscriber.class);
            if (annotation != null) {
                for (String t : annotation.themes()) {
                    List<IEventSubscriber> iEventSubscribers = annotatedBeans.get(t);
                    if (iEventSubscribers != null) {
                        iEventSubscribers.add(castedBean);
                    } else {
                        LinkedList<IEventSubscriber> eventSubscribersList = new LinkedList<>();
                        eventSubscribersList.add(castedBean);
                        annotatedBeans.put(t, eventSubscribersList);
                    }
                }
            }
        }
        return bean;
    }

        @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Subscriber.class);

        beansWithAnnotation.values().forEach(it -> {

            String[] themes = it.getClass().getAnnotation(Subscriber.class).themes();
            for (String t : themes) {
                List<IEventSubscriber> objects = annotatedBeans.get(t);
                if (objects != null) {
                    objects.add((IEventSubscriber) it);
                } else {
                    LinkedList<IEventSubscriber> beans = new LinkedList<>();
                    beans.add((IEventSubscriber) it);
                    annotatedBeans.put(t, beans);
                }
            }
        });
    }
}