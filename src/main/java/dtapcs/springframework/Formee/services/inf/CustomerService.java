package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.Customer;

import java.util.List;

public interface CustomerService {
    void createCustomer(Customer customer);

    List<Customer> findAllByUserId(String userId);
}
