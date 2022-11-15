package ru.pylaev.toDoProject.presentLayer;

import java.util.concurrent.TimeUnit;

public abstract class BaseInputGetter {
    protected abstract String getUserInput ();

    protected abstract void setNull();

    public String getNotEmptyInput() {
        setNull();
        String result = null;
        try {
            while (result == null) {
                result = getUserInput();
                TimeUnit.MILLISECONDS.sleep(250);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}