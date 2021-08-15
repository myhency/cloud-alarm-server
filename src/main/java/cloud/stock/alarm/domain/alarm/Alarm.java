package cloud.stock.alarm.domain.alarm;

import cloud.stock.alarm.domain.exceptions.InvalidAlarmCreationDataException;
import cloud.stock.alarm.domain.strategy.AlarmStatus;
import cloud.stock.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;
    private String itemName;
    private String itemCode;
    private Integer closingPrice;
    private Integer recommendPrice;
    private Integer losscutPrice;
    private String comment;
    private String theme;
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;

    private Alarm(String itemName,
                String itemCode,
                Integer closingPrice,
                Integer recommendPrice,
                Integer losscutPrice,
                String comment,
                String theme) {
            validate(itemName, itemCode, recommendPrice, losscutPrice);

        this.itemName = itemName;
        this.itemCode = itemCode;
        this.closingPrice = closingPrice;
        this.recommendPrice = recommendPrice;
        this.losscutPrice = losscutPrice;
        this.comment = comment;
        this.theme = theme;
        this.alarmStatus = AlarmStatus.ALARM_CREATED;
    }

    private void validate(
            String itemName,
            String itemCode,
            Integer recommendPrice,
            Integer losscutPrice
    ) {
        if(itemName == null ||
                itemCode == null ||
                recommendPrice == null ||
                losscutPrice == null
        )
            throw new InvalidAlarmCreationDataException();
    }

    public static Alarm createAlarmCreationRequest(String itemName,
                                                   String itemCode,
                                                   Integer closingPrice,
                                                   Integer recommendPrice,
                                                   Integer losscutPrice,
                                                   String comment,
                                                   String theme) {
        return new Alarm(itemName, itemCode, closingPrice, recommendPrice, losscutPrice, comment, theme);
    }
}
