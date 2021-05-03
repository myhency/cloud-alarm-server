package cloud.stock;

import cloud.stock.alarm.domain.Alarm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Fixtures {
    public static final Long ALARM1_ID = 1L;
    public static final Long ALARM2_ID = 2L;

    public static Alarm alarmForCreation() {
        final Alarm alarm = new Alarm();
        alarm.setId(ALARM1_ID);
        alarm.setItemName("AP시스템");
        alarm.setItemCode("265520");
        alarm.setRecommendPrice(30500);
        alarm.setLosscutPrice(27800);
        alarm.setComment("손절 27800");
        alarm.setTheme("반도체 장비, 플렉서블 디스플레이, LCD장비, OLED(유기 발광 다이오드)");
        alarm.setCreatedAt(LocalDateTime.now());
        alarm.setLastUpdatedAt(LocalDateTime.now());
        alarm.setAlarmedAt(null);
        alarm.setLosscutAt(null);
        alarm.setAlarmStatus(null);
        return alarm;
    }

    public static Alarm alarmForUpdate() {
        final Alarm alarm = new Alarm();
        alarm.setId(ALARM1_ID);
        alarm.setItemName("AP시스템");
        alarm.setItemCode("265520");
        alarm.setRecommendPrice(40000);
        alarm.setLosscutPrice(30000);
        alarm.setComment("손절 30000");
        alarm.setTheme("반도체 장비, 플렉서블 디스플레이, LCD장비, OLED(유기 발광 다이오드)");
        alarm.setCreatedAt(LocalDateTime.now());
        alarm.setLastUpdatedAt(LocalDateTime.now());
        alarm.setAlarmedAt(null);
        alarm.setLosscutAt(null);
        alarm.setAlarmStatus(null);
        return alarm;
    }
}


