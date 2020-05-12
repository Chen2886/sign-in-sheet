package DataBaseUtil;

public enum DBOrder {
    ENTRIES(0);

    private final int value;

    private DBOrder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
