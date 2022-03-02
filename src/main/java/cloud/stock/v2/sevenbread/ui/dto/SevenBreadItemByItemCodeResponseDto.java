package cloud.stock.v2.sevenbread.ui.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadItemByItemCodeResponseDto {
    private String itemName;
    private String itemCode;
    private String majorHandler;
    private String capturedDate;
    private List<String> reOccurDateList;
}
