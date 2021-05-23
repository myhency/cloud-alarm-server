package cloud.stock.alarm.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCreationResponseDto {
    private Long alarmId;
    private String itemName;
    private String itemCode;
    private Integer recommendPrice;
    private Integer losscutPrice;
    private String comment;
    private String theme;
    private LocalDateTime createdDate;
}