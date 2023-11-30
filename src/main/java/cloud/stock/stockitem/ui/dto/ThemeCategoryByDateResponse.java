package cloud.stock.stockitem.ui.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeCategoryByDateResponse {
    private String categoryName;
    private Integer count;
}
