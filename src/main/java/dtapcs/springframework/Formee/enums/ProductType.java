package dtapcs.springframework.Formee.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProductType {
    CLOTHES("Thời trang"),
    COSMETICS("Mỹ phẩm"),
    FOOD("Thực phẩm"),
    JEWELRY("Trang sức"),
    HEALTH("Y tế"),
    ELECTRONICS("Điện tử"),
    SERVICE("Dịch vụ");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public static ProductType getByValue(String name) {
        return Arrays.stream(ProductType.values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public static List<ProductType> getAllEnums() {
        return Arrays.stream(ProductType.values()).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }
}