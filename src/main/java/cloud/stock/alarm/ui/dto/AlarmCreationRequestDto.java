package cloud.stock.alarm.ui.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmCreationRequestDto {
    @NotNull
    private String itemName;
    @NotNull
    private String itemCode;
    @NotNull
    private Integer recommendPrice;
    @NotNull
    private Integer losscutPrice;
    private String comment;
    private String theme;
}
