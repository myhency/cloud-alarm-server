package cloud.stock.alarm.domain.alarmHistory;

import cloud.stock.alarm.domain.strategy.AlarmStatus;
import cloud.stock.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AlarmHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;
    private String itemName;
    private String itemCode;
    private Integer recommendPrice;
    private Integer losscutPrice;
    private String comment;
    private String theme;
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;

    private AlarmHistory(String itemName,
                  String itemCode,
                  Integer recommendPrice,
                  Integer losscutPrice,
                  String comment,
                  String theme) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.recommendPrice = recommendPrice;
        this.losscutPrice = losscutPrice;
        this.comment = comment;
        this.theme = theme;
        this.alarmStatus = AlarmStatus.ALARM_CREATED;
    }
}
