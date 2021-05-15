package cloud.stock.alarm.infra;

import cloud.stock.alarm.domain.Alarm;

import java.util.List;
import java.util.Optional;

public interface AlarmDao {

    Alarm create(Alarm entity);

    List<Alarm> findAll();

    Optional<Alarm> findById(Long id);

    Optional<Alarm> findByFilter(String itemCode, String itemName);
}
