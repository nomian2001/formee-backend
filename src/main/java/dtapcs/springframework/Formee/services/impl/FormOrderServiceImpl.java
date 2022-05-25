package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.repositories.inf.FormOrderRepo;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FormOrderServiceImpl implements FormOrderService {
    @Autowired
    FormOrderRepo formOrderRepo;

    @Override
    public FormOrder createOrder(FormOrder order) {
        return formOrderRepo.save(order);
    }

    @Override
    public FormOrder getById(UUID id) {
        return formOrderRepo.getById(id);
    }

    @Override
    public List<FormOrder> findAllByUserName(String userName) {
        return formOrderRepo.findAllByCreatedBy(userName);
    }
}
