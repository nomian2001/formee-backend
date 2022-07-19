package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.entities.Customer;
import dtapcs.springframework.Formee.helper.CommonHelper;
import dtapcs.springframework.Formee.repositories.inf.CustomerRepo;
import dtapcs.springframework.Formee.services.inf.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void updateCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    @Override
    public List<Customer> findAllByUserId(String userId) {
        return customerRepo.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    @Override
    public List<Customer> filterCustomers(String keywords) {
        UserDetails userDetails = CommonHelper.getCurrentUser();
        List<Customer> customerList = customerRepo.findAllByCreatedByOrderByCreatedDateDesc(userDetails.getUsername());
        Stream<Customer> customerStream = customerList.stream();

        if (StringUtils.hasText(keywords)) {
            customerStream = customerStream.filter(product -> product.getName().toLowerCase().contains(keywords)
                    || product.getPhone().toLowerCase().contains(keywords)
                    || product.getAddress().toLowerCase().contains(keywords));
        }

        return customerStream.collect(Collectors.toList());
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

    @Override
    public void deleteById(UUID customerId) {
        customerRepo.deleteById(customerId);
    }
}
