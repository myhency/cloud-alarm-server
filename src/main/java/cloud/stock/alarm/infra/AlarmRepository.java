package cloud.stock.alarm.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import cloud.stock.alarm.domain.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Alarm a SET a.comment = :comment, a.recommendPrice = :recommendPrice, a.losscutPrice = :losscutPrice WHERE a.id = :id")
    int updateAnalyzedItem(Integer id, int recommendPrice, int losscutPrice, String comment);

}
