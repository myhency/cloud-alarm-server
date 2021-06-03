package cloud.stock.alarm.infra;

import cloud.stock.alarm.domain.alarm.Alarm;
import cloud.stock.alarm.domain.alarmHistory.AlarmHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long> {
}
