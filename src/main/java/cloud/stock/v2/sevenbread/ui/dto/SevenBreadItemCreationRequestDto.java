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
public class SevenBreadItemCreationRequestDto {
    @NotEmpty
    private String itemCode;
    @NotEmpty
    private String majorHandler;
    @NotEmpty
    private String capturedDate;
}
