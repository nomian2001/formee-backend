package dtapcs.springframework.Formee.dtos.model;

import dtapcs.springframework.Formee.enums.StatisticsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    private StatisticsType type;
    private Map<String, String> data;
//    private Map<String, List<String>> periodOptions;

//    public StatisticsDTO(StatisticsType type, Map<String, String> data) {
//        this.type = type;
//        this.data = data;
//    }
}
