package cloud.stock.stockitem.domain;


import cloud.stock.alarm.domain.alarm.Alarm;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "stock_item", uniqueConstraints = {@UniqueConstraint(
        name = "item_code_unique",
        columnNames = {"item_code"}
)})
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "item name is required")
    private String itemName;
    @NotEmpty(message = "item code is required")
    @Column(name = "item_code", nullable = false)
    private String itemCode;
    private String theme;

    public StockItem(@NotEmpty(message = "item name is required") String itemName, @NotEmpty(message = "item code is required") String itemCode, String theme) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.theme = theme;
    }

    public static StockItem createStockItemCreationRequest(String itemName,
                                                           String itemCode,
                                                           String theme) {
        return new StockItem(itemName, itemCode, theme);
    }
}
