package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.Customer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CustomerService {
    void createCustomer(Customer customer);

    void updateCustomer(Customer customer);

    List<Customer> findAllByUserId(String userId);

    List<Customer> filterCustomers(String keywords);

    Map<String, String> getTotalStatistics(String username);

    void deleteById(UUID customerId);
}
