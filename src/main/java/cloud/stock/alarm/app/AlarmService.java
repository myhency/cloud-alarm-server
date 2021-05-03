package cloud.stock.alarm.app;

import cloud.stock.alarm.domain.Alarm;
import cloud.stock.alarm.infra.AlarmDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AlarmService {

    private final AlarmDao alarmDao;

    public AlarmService(final AlarmDao alarmDao) {
        this.alarmDao = alarmDao;
    }

    @Transactional
    public Alarm create(final Alarm alarm) {
        alarm.setCreatedAt(LocalDateTime.now());
        alarm.setLastUpdatedAt(LocalDateTime.now());

        return alarmDao.create(alarm);
    }

    @Transactional
    public Alarm changeAlarm(final Long id, final Alarm alarm) {
        final Alarm savedAlarm = alarmDao.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        savedAlarm.setRecommendPrice(alarm.getRecommendPrice());
        savedAlarm.setLosscutPrice(alarm.getLosscutPrice());
        savedAlarm.setComment(alarm.getComment());
        savedAlarm.setLastUpdatedAt(LocalDateTime.now());

        alarmDao.create(savedAlarm);

        return savedAlarm;
    }
}
