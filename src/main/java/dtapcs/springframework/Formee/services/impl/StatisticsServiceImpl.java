package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.model.StatisticsDTO;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.entities.FormeeUser;
import dtapcs.springframework.Formee.enums.PeriodType;
import dtapcs.springframework.Formee.enums.StatisticsType;
import dtapcs.springframework.Formee.helper.CommonHelper;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.services.inf.CustomerService;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
import dtapcs.springframework.Formee.services.inf.ProductService;
import dtapcs.springframework.Formee.services.inf.StatisticsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    FormOrderService formOrderService;

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @Override
    public List<StatisticsDTO> getAllStatistics(PeriodType type) {
        List<StatisticsDTO> result = new ArrayList<>();
        UserDetails userDetails = CommonHelper.getCurrentUser();
        String username = userDetails.getUsername();

        // ORDER ("Thống kê đơn hàng"),
        Map<String, String> orderStats = formOrderService.getTotalStatistics(username, type);
        result.add(new StatisticsDTO(StatisticsType.ORDER, orderStats));

        // PRODUCT ("Thống kê sản phẩm"),
        Map<String, String> productStats = productService.getTotalStatistics(username);
        result.add(new StatisticsDTO(StatisticsType.PRODUCT, productStats));

        // CUSTOMER ("Thống kê khách hàng"),
        Map<String, String> customerStats = customerService.getTotalStatistics(username);
        result.add(new StatisticsDTO(StatisticsType.CUSTOMER, customerStats));

        // REVENUE ("Thu nhập và doanh thu") ,
        Map<String, String> revenueStatsByYear = formOrderService.getRevenueStatistics(username, 2022, type);
        result.add(new StatisticsDTO(StatisticsType.REVENUE, revenueStatsByYear));

        // SALES ("Số lượng bán"),
        Map<String, String> productCategoryStats = formOrderService.getCategoryStatistics(username, type);
        result.add(new StatisticsDTO(StatisticsType.SALES, productCategoryStats));

        // TOP_PRODUCTS ("Sản phẩm bán chạy")
        Map<String, String> topProductStats = formOrderService.getProductStatistics(username);
        result.add(new StatisticsDTO(StatisticsType.TOP_PRODUCTS, topProductStats));

        // CUSTOMER_NUMBER ("Số lượng khách hàng")
        Map<String, String> customerStatsByYear = formOrderService.getCustomerStatistics(username, 2022, type);
        result.add(new StatisticsDTO(StatisticsType.CUSTOMER_NUMBER, customerStatsByYear));

        return result;
    }
}
