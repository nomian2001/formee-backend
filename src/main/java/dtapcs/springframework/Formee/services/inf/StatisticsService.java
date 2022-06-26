package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.dtos.model.StatisticsDTO;

import java.util.List;

public interface StatisticsService {
    List<StatisticsDTO> getAllStatistics();
}
