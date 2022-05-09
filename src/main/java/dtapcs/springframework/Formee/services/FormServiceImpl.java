package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.domain.Form;
import dtapcs.springframework.Formee.repositories.FormRepository;
import org.springframework.stereotype.Service;

@Service
public class FormServiceImpl implements FormService {
    private final FormRepository formRepository;

    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Override
    public String createForm(Form form) {
        formRepository.save(form);
        return null;
    }
}
