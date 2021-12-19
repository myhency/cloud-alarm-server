package cloud.stock.v2.sevenbread.domain;

import cloud.stock.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(
        name = "item_code_unique",
        columnNames = {"item_code"}
)})
public class SevenBread extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "item_code", nullable = false)
    private String itemCode;
    private Integer capturedPrice;
    private Integer lowestPrice;
    private Double fluctuationRate;
    private String majorHandler;
    private String capturedDate;

    public SevenBread(String itemCode,
                      String capturedDate,
                      String majorHandler) {
        this.itemCode = itemCode;
        this.capturedDate = capturedDate;
        this.majorHandler = majorHandler;
    }

    public static SevenBread createSevenBreadCreationRequest(String itemCode,
                                                             String capturedDate,
                                                             String majorHandler) {
        return new SevenBread(itemCode, capturedDate, majorHandler);
    }
}
