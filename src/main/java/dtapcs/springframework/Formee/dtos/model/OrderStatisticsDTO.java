package dtapcs.springframework.Formee.dtos.model;

import lombok.Data;

@Data
public class OrderStatisticsDTO {
    int month;
    int year;
    int numberOfOrder;
    double totalSale;
    double profit;
}
