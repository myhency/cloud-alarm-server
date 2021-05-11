package cloud.stock.alarm.infra;

import cloud.stock.alarm.domain.Alarm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcTemplateAlarmDao implements AlarmDao {

    private static final String TABLE_NAME = "alarms";
    private static final String KEY_COLUMN_NAME = "id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateAlarmDao(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN_NAME);
    }

    @Override
    public Alarm create(final Alarm entity) {
        if(Objects.isNull(entity.getId())) {
            final SqlParameterSource parameters = new BeanPropertySqlParameterSource(entity);
            final Number key = jdbcInsert.executeAndReturnKey(parameters);
            return select(key.longValue());
        }

        update(entity);
        return entity;
    }

    @Override
    public List<Alarm> findAll() {
        final String sql = "SELECT " +
                "id, item_name, item_code, recommend_price, " +
                "losscut_price, comment, theme, alarm_status, " +
                "created_at, last_updated_at, alarmed_at, losscut_at " +
                "FROM alarms " +
                "ORDER BY id DESC";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    @Override
    public Optional<Alarm> findById(Long id) {
        try {
            return Optional.of(select(id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Alarm select(final Long id) {
        final String sql = "SELECT " +
                "id, item_name, item_code, recommend_price, " +
                "losscut_price, comment, theme, alarm_status, " +
                "created_at, last_updated_at, alarmed_at, losscut_at " +
                "FROM alarms " +
                "WHERE id = (:id)";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameterSource, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    private void update(final Alarm entity) {
        final String sql = "UPDATE alarms " +
                "SET recommend_price = (:recommendPrice), " +
                "losscut_price = (:losscutPrice), " +
                "comment = (:comment) " +
                "WHERE id = (:id)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("recommendPrice", entity.getRecommendPrice())
                .addValue("losscutPrice", entity.getLosscutPrice())
                .addValue("id", entity.getId());
        jdbcTemplate.update(sql, sqlParameterSource);
    }

    private Alarm toEntity(final ResultSet resultSet) throws SQLException {
        final Alarm entity = new Alarm();
        entity.setId(resultSet.getLong(KEY_COLUMN_NAME));
        entity.setItemName(resultSet.getString("item_name"));
        entity.setItemCode(resultSet.getString("item_code"));
        entity.setRecommendPrice(resultSet.getInt("recommend_price"));
        entity.setLosscutPrice(resultSet.getInt("losscut_price"));
        entity.setComment(resultSet.getString("comment"));
        entity.setTheme(resultSet.getString("theme"));
        entity.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
        entity.setAlarmStatus(resultSet.getString("alarm_status"));
        entity.setLastUpdatedAt(resultSet.getObject("last_updated_at", LocalDateTime.class));
        entity.setAlarmedAt(resultSet.getObject("alarmed_at", LocalDateTime.class));

        return entity;
    }
}
