package ru.innopolis.university;

public class BooleanBox {
    private boolean value = false;

    BooleanBox(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
