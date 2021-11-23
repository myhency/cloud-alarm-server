package cloud.stock.stockitem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ThemeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "category name is required")
    private String categoryName;
    @NotEmpty(message = "category code is required")
    @Column(name = "category_code", nullable = false)
    private String categoryCode;
    @Column(name = "item_code", nullable = false)
    private String itemCode;
    @Column(columnDefinition = "LONGTEXT")
    private String theme;

    public ThemeCategory(@NotEmpty(message = "category name is required") String categoryName,
                         @NotEmpty(message = "category code is required") String categoryCode,
                         String itemCode,
                         String theme) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.theme = theme;
        this.itemCode = itemCode;
    }

    public static ThemeCategory createThemeCategoryCreationRequest(String categoryName,
                                                               String categoryCode,
                                                               String itemCode,
                                                               String theme) {
        return new ThemeCategory(categoryName, categoryCode, itemCode, theme);
    }
}
