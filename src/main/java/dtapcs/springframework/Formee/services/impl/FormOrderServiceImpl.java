package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.repositories.inf.FormOrderRepo;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FormOrderServiceImpl implements FormOrderService {
    @Autowired
    FormRepo formRepo;
    @Autowired
    FormOrderRepo formOrderRepo;

    @Override
    public FormOrder createOrder(FormOrder order) {
        Optional<Form> check = formRepo.findById(UUID.fromString(order.getFormId()));
        if (check.isPresent()) {
            Form form = check.get();
            formRepo.save(form); // new last modified date
        }
        return formOrderRepo.save(order);
    }

    @Override
    public FormOrder getById(UUID id) {
        return formOrderRepo.getById(id);
    }

    @Override
    public List<FormOrder> findAllByFormId(String formId) {
        return formOrderRepo.findAllByFormId(formId);
    }
}
