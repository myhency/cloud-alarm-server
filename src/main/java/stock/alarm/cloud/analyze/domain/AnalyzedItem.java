package stock.alarm.cloud.analyze.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
@Entity
public class AnalyzedItem {

    @Id
    @GeneratedValue
    private Integer id;
    private String itemName;
    private String itemCode;
    private int recommendPrice;
    private int losscutPrice;
    private String comment;
    private String theme;
    private Date createdAt;
    private Date updatedAt;
    private Date alarmedAt;
    private Date cutAt;

}
