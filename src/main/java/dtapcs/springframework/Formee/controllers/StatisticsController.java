package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.StatisticsDTO;
import dtapcs.springframework.Formee.dtos.model.UserDTO;
import dtapcs.springframework.Formee.enums.PeriodType;
import dtapcs.springframework.Formee.services.inf.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
public class StatisticsController extends BaseController {
    @Autowired
    StatisticsService statisticsService;

    @GetMapping("")
    public ResponseEntity getAllStatistics(@RequestParam PeriodType type) {
        List<StatisticsDTO> result = statisticsService.getAllStatistics(type);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(result)
                .build();
        return ResponseEntity.ok(response);
    }
}
