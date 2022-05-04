package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.domain.FormResponse;
import dtapcs.springframework.Formee.repositories.FormResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class FormResponseServiceImpl implements FormResponseService {
    private final FormResponseRepository formResponseRepository;

    public FormResponseServiceImpl(FormResponseRepository formResponseRepository) {
        this.formResponseRepository = formResponseRepository;
    }

    @Override
    public List<FormResponse> getFormResponseList(int filterValue, int pageNumber, int rowPerPage) {
        return null;
    }

    @Override
    public String exportFile(List<UUID> responseId, int exportType) {
        //code export file
        return null;
    }

    @Override
    public String importFile(String formResponse) {
        //code import file
        return null;
    }
}
