package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.helper.ExcelHelper;
import dtapcs.springframework.Formee.repositories.inf.FormOrderRepo;
import dtapcs.springframework.Formee.services.inf.ExcelService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelServiceImpl implements ExcelService {
    FormOrderRepo formResponseRepo;
    @Override
    public ByteArrayInputStream load(String formId) {
        List<FormOrder> formResponseList = formResponseRepo.findAllByFormId(formId);
        ByteArrayInputStream in = ExcelHelper.FormResponseToExcel(formResponseList);
        return in;
    }
}
