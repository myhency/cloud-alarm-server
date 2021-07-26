package cloud.stock.sevenbread.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadItemCreationRequestDto {
    private String itemName;
    private String itemCode;
    private String majorHandler;
    private String capturedDate;
    private String theme;
}
