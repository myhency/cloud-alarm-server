package cloud.stock.alarm.ui.dataholder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDataHolder {
    private Long alarmId;
    private String itemName;
    private String itemCode;
    private Integer recommendPrice;
    private Integer losscutPrice;
    private String comment;
    private String theme;
    private String alarmStatus;
    private LocalDateTime createdDate;
}
