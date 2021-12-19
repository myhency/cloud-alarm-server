package cloud.stock.v2.sevenbread.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadDailyBuyingTraceRequestDto {
    private String itemCode;
    private String closingDate;
    @JsonProperty(value="wBuyAmount")
    private Integer wBuyAmount;
    @JsonProperty(value="gBuyAmount")
    private Integer gBuyAmount;
    @JsonProperty(value="pBuyAmount")
    private Integer pBuyAmount;
}
