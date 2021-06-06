package cloud.stock.alarm.infra;

import cloud.stock.alarm.domain.alarm.Alarm;
import cloud.stock.alarm.domain.alarmHistory.AlarmHistory;
import cloud.stock.alarm.domain.strategy.AlarmStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long> {
    @Query(value = "SELECT ah " +
            "FROM AlarmHistory ah " +
            "WHERE ah.alarmStatus= :alarmStatus " +
            "AND ah.itemCode NOT IN (" +
            "   SELECT a.itemCode " +
            "   FROM Alarm a" +
            ")"
    )
    List<AlarmHistory> findAllByAlarmStatusOrderByModifiedDateDesc(
            @Param("alarmStatus") AlarmStatus alarmStatus
    );

    Optional<AlarmHistory> findByAlarmIdAndAlarmStatus(Long alarmId, AlarmStatus alarmStatus);
}
