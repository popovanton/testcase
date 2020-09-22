package com.anpopov.testcase.subscriber;

public interface IEventSubscriber {

    void onEvent(String name, Object obj);
}
