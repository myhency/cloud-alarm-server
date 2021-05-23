package cloud.stock.stockitem.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class StockItem {

    @Id
    @GeneratedValue
    private Long id;
    private String itemName;
    private String itemCode;
    private String theme;
}
