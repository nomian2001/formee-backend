package dtapcs.springframework.Formee.enums;

import java.util.Arrays;

public enum StatisticsType {
    ORDER("Thống kê đơn hàng"),
    PRODUCT("Thống kê sản phẩm"),
    CUSTOMER("Thống kê khách hàng"),
    REVENUE("Thu nhập"),
    SALES("Số lượng bán"),
    TOP_PRODUCTS("Sản phẩm bán chạy"),
    CUSTOMER_NUMBER("Số lượng khách hàng");

    private final String name;

    StatisticsType(String name) {
        this.name = name;
    }

    public static final StatisticsType getByValue(String name) {
        return Arrays.stream(StatisticsType.values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }
}
