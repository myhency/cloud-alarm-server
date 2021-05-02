package cloud.stock.alarm;

import org.junit.jupiter.api.Test;
import cloud.stock.alarm.domain.Alarm;

import static org.assertj.core.api.Assertions.assertThat;


class AlarmTest {

    @Test
    public void builder() {
        Alarm alarm = Alarm.builder()
                .itemName("AP시스템")
                .itemCode("265520")
                .recommendPrice(20000)
                .losscutPrice(10000)
                .comment("돌파시 매수하세요")
                .theme("위성")
                .build();

        assertThat(alarm).isNotNull();
    }

}