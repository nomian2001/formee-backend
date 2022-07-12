package dtapcs.springframework.Formee.enums;

import java.util.Arrays;

public enum PeriodType {
    WEEK("8 days"),
    MONTH("1 month"),
    YEAR("1 year");
    
    private final String name;

    PeriodType(String name) {
        this.name = name;
    }

    public static final PeriodType getByValue(String name) {
        return Arrays.stream(PeriodType.values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }
}
