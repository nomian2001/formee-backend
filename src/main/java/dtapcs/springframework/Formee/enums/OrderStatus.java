package dtapcs.springframework.Formee.enums;

import java.util.Arrays;

public enum OrderStatus {
    PENDING("Pending"),
    PREPARING("Preparing"),
    ON_THE_WAY("On the way"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    CONFIRMED("Confirmed"),
    REQUESTED("Requested");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static final OrderStatus getByValue(String name) {
        return Arrays.stream(OrderStatus.values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }
}
