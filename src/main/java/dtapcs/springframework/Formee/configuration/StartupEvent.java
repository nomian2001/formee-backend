package dtapcs.springframework.Formee.configuration;

import dtapcs.springframework.Formee.entities.AddressCommons;
import dtapcs.springframework.Formee.entities.Role;
import dtapcs.springframework.Formee.enums.ERole;
import dtapcs.springframework.Formee.repositories.inf.AddressRepo;
import dtapcs.springframework.Formee.repositories.inf.RoleRepo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class StartupEvent implements ApplicationRunner {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private AddressRepo addressRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // add default role
        Role role = roleRepo.findByName(ERole.ROLE_USER).orElse(null);
        if (role == null) {
            Role userRole = new Role(ERole.ROLE_USER);
            roleRepo.save(userRole);
        }

        // add address commons
        if (addressRepo.count() == 0) {
//            File file = ResourceUtils.getFile("classpath:dm_dia_chi_commons.xls");
            InputStream in = new ClassPathResource("\\dm_dia_chi_commons.xls").getInputStream();
            Workbook workbook = WorkbookFactory.create(in);
            for (int i = 0; i < workbook.getNumberOfSheets(); ++i) {
                Sheet sheet = workbook.getSheetAt(i);
                for (int j = 1; j <= sheet.getLastRowNum(); ++j) {
                    Row row = sheet.getRow(j);
                    if (row != null) {
                        AddressCommons address = new AddressCommons();
                        if (row.getCell(0) != null) {
                            address.setCode(row.getCell(0).getStringCellValue());
                            if (row.getCell(1) != null) {
                                address.setName_(row.getCell(1).getStringCellValue());
                                if (row.getCell(3) != null) {
                                    address.setType_(row.getCell(3).getStringCellValue());
                                    Cell parentCode = row.getCell(2);
                                    if (parentCode == null) {
                                        address.setParentCode(null);
                                    }
                                    else {
                                        address.setParentCode(row.getCell(2).getStringCellValue());
                                    }
                                    addressRepo.save(address);
                                }
                            }
                        }
                    }
                }
            }
        }

        // add form templates
    }
}
