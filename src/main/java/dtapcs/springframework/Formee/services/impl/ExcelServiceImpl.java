package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.FormResponse;
import dtapcs.springframework.Formee.helper.ExcelHelper;
import dtapcs.springframework.Formee.repositories.inf.FormResponseRepo;
import dtapcs.springframework.Formee.services.inf.ExcelService;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    FormResponseRepo formResponseRepo;
    @Override
    public ByteArrayInputStream load() {
        List<FormResponse> formResponseList = formResponseRepo.findAll();
        ByteArrayInputStream in = ExcelHelper.FormResponseToExcel(formResponseList);
        return in;
    }
}
