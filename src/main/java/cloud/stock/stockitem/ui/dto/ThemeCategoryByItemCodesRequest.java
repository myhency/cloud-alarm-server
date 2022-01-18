package cloud.stock.stockitem.ui.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeCategoryByItemCodesRequest {
    private List<String> itemCodes;
}
