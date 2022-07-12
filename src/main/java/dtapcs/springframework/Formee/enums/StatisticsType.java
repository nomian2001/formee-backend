package dtapcs.springframework.Formee.enums;

import java.util.Arrays;

public enum StatisticsType {
    ORDER("Thống kê đơn hàng"),
    PRODUCT("Thống kê sản phẩm"),
    CUSTOMER("Thống kê khách hàng"),
    REVENUE("Thu nhập"),
    SALES("Số lượng bán"),
    TOP_PRODUCTS("Sản phẩm bán chạy (theo số lượng)"),
    TOP_PRODUCTS_INCOME("Sản phẩm bán chay (theo doanh thu)"),
    CUSTOMER_NUMBER("Số lượng khách hàng"),
    TOTAL_INCOME("Tổng doanh thu"),
    TOTAL_REVENUE("Tổng thu nhập");

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
