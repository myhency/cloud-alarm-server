package cloud.stock.v2.sevenbread.domain;

import cloud.stock.util.BaseTimeEntity;
import cloud.stock.v2.sevenbread.domain.strategy.AlarmStatus;
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
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;

    public SevenBread(String itemCode,
                      String capturedDate,
                      String majorHandler) {
        this.itemCode = itemCode;
        this.capturedDate = capturedDate;
        this.majorHandler = majorHandler;
        this.alarmStatus = AlarmStatus.ON_LISTED;
    }

    public static SevenBread createSevenBreadCreationRequest(String itemCode,
                                                             String capturedDate,
                                                             String majorHandler) {
        return new SevenBread(itemCode, capturedDate, majorHandler);
    }
}
