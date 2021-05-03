package cloud.stock.alarm.app;

import cloud.stock.alarm.domain.Alarm;
import cloud.stock.alarm.infra.AlarmDao;

import java.util.*;

public class InMemoryAlarmDao implements AlarmDao {
    private final Map<Long, Alarm> entities = new HashMap<>();

    @Override
    public Alarm create(final Alarm entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Alarm> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public Optional<Alarm> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }
}
