package cloud.stock.alarm.app;

import cloud.stock.alarm.domain.Alarm;
import cloud.stock.alarm.infra.AlarmDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cloud.stock.Fixtures.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AlarmServiceTest {
    private final AlarmDao alarmDao = new InMemoryAlarmDao();

    private AlarmService alarmService;

    @BeforeEach
    void setup() {
        alarmService = new AlarmService(alarmDao);
    }

    @DisplayName("알람등록 정상적인 케이스")
    @Test
    void create() {
        //given
        final Alarm expected = alarmForCreation();

        //when
        final Alarm actual = alarmService.create(expected);

        //then
        assertAlarm(expected, actual);
    }

    @DisplayName("알람수정 정상적인 케이스")
    @Test
    void changeAlarm() {
        //given
        final Alarm prevAlarm = alarmForCreation();
        final Alarm expected = alarmForUpdate();

        //when
        alarmService.create(prevAlarm);
        final Alarm actual = alarmService.changeAlarm(prevAlarm.getId(), expected);

        //then
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
                () -> assertThat(actual.getRecommendPrice()).isEqualTo(expected.getRecommendPrice()),
                () -> assertThat(actual.getLosscutPrice()).isEqualTo(expected.getLosscutPrice()),
                () -> assertThat(actual.getComment()).isEqualTo(expected.getComment())
        );
    }

    private void assertAlarm(final Alarm expected, final Alarm actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
                () -> assertThat(actual.getItemName()).isEqualTo(expected.getItemName()),
                () -> assertThat(actual.getItemCode()).isEqualTo(expected.getItemCode())
        );
    }


}