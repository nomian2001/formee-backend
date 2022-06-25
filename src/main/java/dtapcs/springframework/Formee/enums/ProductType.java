package dtapcs.springframework.Formee.enums;

import java.util.Arrays;

public enum ProductType {
    CLOTHES("Thời trang"),
    COSMETICS("Mỹ phẩm"),
    FOOD("Thực phẩm"),
    JEWELRY("Trang sức"),
    HEALTH("Y tế"),
    ELECTRONICS("Điện tử"),
    SERVICE("Dịch vụ"),
    BOOKS("Sách báo");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public static final ProductType getByValue(String name) {
        return Arrays.stream(ProductType.values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }
}