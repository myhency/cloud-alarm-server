package cloud.stock.alarm.domain.alarmHistory;

import cloud.stock.alarm.domain.strategy.AlarmStatus;
import cloud.stock.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AlarmHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long alarmId;
    private String itemName;
    private String itemCode;
    private Integer recommendPrice;
    private Integer losscutPrice;
    private String comment;
    private String theme;
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public AlarmHistory(Long alarmId, String itemName, String itemCode, Integer recommendPrice, Integer losscutPrice, String comment, String theme, AlarmStatus alarmStatus, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.alarmId = alarmId;
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.recommendPrice = recommendPrice;
        this.losscutPrice = losscutPrice;
        this.comment = comment;
        this.theme = theme;
        this.alarmStatus = alarmStatus;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
