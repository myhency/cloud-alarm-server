package stock.alarm.cloud.analyze;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Integer id;
    private String itemName;
    private String itemCode;
}
