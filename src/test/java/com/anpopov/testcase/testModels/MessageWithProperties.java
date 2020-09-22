package com.anpopov.testcase.testModels;

import java.util.Objects;

public class MessageWithProperties {
    private String name;
    private String number;

    public MessageWithProperties(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageWithProperties that = (MessageWithProperties) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }
}
