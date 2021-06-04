package cloud.stock.alarm.infra;

import cloud.stock.alarm.domain.alarm.Alarm;
import cloud.stock.alarm.domain.alarmHistory.AlarmHistory;
import cloud.stock.alarm.domain.strategy.AlarmStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long> {
    List<AlarmHistory> findAllByAlarmStatusOrderByModifiedDateDesc(AlarmStatus alarmStatus);
}
