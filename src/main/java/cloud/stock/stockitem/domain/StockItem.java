package cloud.stock.stockitem.domain;


import lombok.*;

import javax.persistence.*;
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
    private String itemName;
    @Column(name = "item_code", nullable = false)
    private String itemCode;
    private String theme;
}
