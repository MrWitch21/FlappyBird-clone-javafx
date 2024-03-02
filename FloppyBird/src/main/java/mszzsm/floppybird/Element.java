package mszzsm.floppybird;

public class Element {
    private String key;
    private int value;

    public Element(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
