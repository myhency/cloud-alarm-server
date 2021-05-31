package cloud.stock.alarm.infra;

import cloud.stock.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByItemCode(String itemCode);
    Alarm findOneByItemCode(String itemCode);
}
