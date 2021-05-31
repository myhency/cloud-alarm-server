package cloud.stock.alarm.domain;

import cloud.stock.alarm.domain.exceptions.InvalidAlarmCreationDataException;
import cloud.stock.alarm.domain.strategy.AlarmStatus;
import cloud.stock.util.BaseTimeEntity;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
    private Integer recommendPrice;
        private Integer losscutPrice;
        private String comment;
        private String theme;
        @Enumerated(EnumType.STRING)
        private AlarmStatus alarmStatus;

    private Alarm(String itemName,
                String itemCode,
                Integer recommendPrice,
                Integer losscutPrice,
                String comment,
                String theme) {
            validate(itemName, itemCode, recommendPrice, losscutPrice);

        this.itemName = itemName;
        this.itemCode = itemCode;
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
                                                   Integer recommendPrice,
                                                   Integer losscutPrice,
                                                   String comment,
                                                   String theme) {
        return new Alarm(itemName, itemCode, recommendPrice, losscutPrice, comment, theme);
    }
}
