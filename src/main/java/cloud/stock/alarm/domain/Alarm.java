package cloud.stock.alarm.domain;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
//@EqualsAndHashCode(of="id")
//@Entity
public class Alarm {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NotNull @NotEmpty
    private String itemName;
//    @NotNull @NotEmpty
    private String itemCode;
//    @NotNull
    private Integer recommendPrice;
//    @NotNull
    private Integer losscutPrice;
//    @Nullable
    private String comment;
//    @Nullable
    private String theme;
//    @Nullable
    private String alarmStatus;
//    @NotNull
    private LocalDateTime createdAt;
//    @NotNull
    private LocalDateTime lastUpdatedAt;
//    @Nullable
    private LocalDateTime alarmedAt;
//    @Nullable
    private LocalDateTime losscutAt;

}
