package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.Customer;
import dtapcs.springframework.Formee.repositories.inf.CustomerRepo;
import dtapcs.springframework.Formee.services.inf.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Override
    public void createCustomer(Customer customer) {
        Customer check = customerRepo.findByPhoneAndUserId(customer.getPhone(), customer.getUserId());
        if (check != null) {
            check.setTotalOrders(check.getTotalOrders() + 1);
        }
        else {
            check = customer;
            check.setTotalOrders(1L);
        }
        customerRepo.save(check);
    }

    @Override
    public List<Customer> findAllByUserId(String userId) {
        return customerRepo.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    @Override
    public Map<String, String> getTotalStatistics(String username) {
        Map<String, String> result = new HashMap<>();
        Long total = customerRepo.countByCreatedBy(username);
        result.put("total", String.valueOf(total));
        Long newCustomers = customerRepo.countByCreatedByAndTotalOrders(username, 1L);
        result.put("new", String.valueOf(newCustomers));
        Long returningCustomers = customerRepo.countByCreatedByAndTotalOrdersGreaterThan(username, 1L);
        result.put("returning", String.valueOf(returningCustomers));
        return result;
    }
}
