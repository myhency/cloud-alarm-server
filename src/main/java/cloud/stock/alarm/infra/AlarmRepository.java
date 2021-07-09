package cloud.stock.alarm.infra;

import cloud.stock.alarm.domain.alarm.Alarm;
import cloud.stock.alarm.domain.alarmHistory.AlarmHistory;
import cloud.stock.alarm.domain.strategy.AlarmStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByItemCode(String itemCode);
    Alarm findOneByItemCode(String itemCode);

    @Query(value = "SELECT a " +
            "FROM Alarm a" +
            "WHERE a.alarmStatus= :alarmStatus " +
            "ORDER BY a.modifiedDate DESC"
    )
    List<Alarm> findAllByAlarmStatusOrderByModifiedDateDesc(
            @Param("alarmStatus") AlarmStatus alarmStatus
    );
}
