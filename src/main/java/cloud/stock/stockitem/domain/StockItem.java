package cloud.stock.stockitem.domain;


import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
public class StockItem {
    private Long id;
    private String itemName;
    private String itemCode;
    private String theme;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
