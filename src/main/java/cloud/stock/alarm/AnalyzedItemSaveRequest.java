package cloud.stock.alarm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder @AllArgsConstructor @NoArgsConstructor @Data
@Schema(title = "알리미 등록 Request Body")
public class AnalyzedItemSaveRequest {

    @Schema(description = "종목명")
    @NotEmpty
    private String itemName;

    @Schema(description = "종목코드")
    @NotEmpty
    private String itemCode;

    @Schema(description = "돌파가격", defaultValue = "0")
    @NotNull
    private int recommendPrice;

    @Schema(description = "손절가격", defaultValue = "0")
    @NotNull
    private int losscutPrice;

    @Schema(description = "코멘트")
    private String comment;

    @Schema(description = "테마")
    private String theme;
}
