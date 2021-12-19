package cloud.stock.v2.sevenbread.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadDailyTraceCreationRequestDto {
    @NotEmpty
    private String itemCode;
    @NotEmpty
    private String closingDate;
}
