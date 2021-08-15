package cloud.stock.alarm.ui.dto;

import cloud.stock.alarm.domain.strategy.AlarmStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmClosingPriceUpdateRequestDto {
    @NotNull
    private String itemCode;
    @NotNull
    private Integer closingPrice;
}
