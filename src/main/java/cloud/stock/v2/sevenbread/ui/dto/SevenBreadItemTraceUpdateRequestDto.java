package cloud.stock.v2.sevenbread.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadItemTraceUpdateRequestDto {
    @NotEmpty
    private String itemCode;
    private List<String> reoccurDateList;
}
