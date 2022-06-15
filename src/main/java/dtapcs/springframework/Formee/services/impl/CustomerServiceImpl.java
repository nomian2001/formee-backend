package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.Customer;
import dtapcs.springframework.Formee.repositories.inf.CustomerRepo;
import dtapcs.springframework.Formee.services.inf.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Override
    public void createCustomer(Customer customer) {
        List<Customer> check = customerRepo.findByPhoneAndUserId(customer.getPhone(), customer.getUserId());
        if (check.size() > 0) {
            return;
        }
        customerRepo.save(customer);
    }

    @Override
    public List<Customer> findAllByUserId(String userId) {
        return customerRepo.findAllByUserIdOrderByCreatedDateDesc(userId);
    }
}
