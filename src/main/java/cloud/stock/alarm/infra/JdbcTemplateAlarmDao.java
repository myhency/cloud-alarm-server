package cloud.stock.alarm.infra;

import cloud.stock.alarm.domain.Alarm;
import cloud.stock.common.EmptyResultDataAccessException;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        if(Objects.isNull(entity.getAlarmId())) {
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
                "ORDER BY last_updated_at DESC";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    @Override
    public Optional<Alarm> findById(final Long id) {
        try {
            return Optional.of(select(id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Alarm> findByFilter(String itemCode, String itemName) {
        try {
            return Optional.of(selectByFilter(itemCode,itemName));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Alarm selectByFilter(String itemCode, String itemName) {
        String sql;
        SqlParameterSource parameterSource;
        if(itemName == null) {
            sql = "SELECT " +
                    "id, item_name, item_code, recommend_price, " +
                    "losscut_price, comment, theme, alarm_status, " +
                    "created_at, last_updated_at, alarmed_at, losscut_at " +
                    "FROM alarms " +
                    "WHERE item_code = (:itemCode) " +
                    "LIMIT 1";
            parameterSource = new MapSqlParameterSource()
                    .addValue("itemCode", itemCode);
        } else {
            sql = "SELECT " +
                    "id, item_name, item_code, recommend_price, " +
                    "losscut_price, comment, theme, alarm_status, " +
                    "created_at, last_updated_at, alarmed_at, losscut_at " +
                    "FROM alarms " +
                    "WHERE item_code = (:itemCode) " +
                    "AND item_name = (:itemName) " +
                    "LIMIT 1";
            parameterSource = new MapSqlParameterSource()
                    .addValue("itemCode", itemCode)
                    .addValue("itemName", itemName);
        }

        return jdbcTemplate.queryForObject(sql, parameterSource, (resultSet, rowNumber) -> toEntity(resultSet));
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
                "comment = (:comment), " +
                "theme = (:theme)," +
                "last_updated_at = (:lastUpdatedAt) " +
                "WHERE id = (:id)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("recommendPrice", entity.getRecommendPrice())
                .addValue("losscutPrice", entity.getLosscutPrice())
                .addValue("comment", entity.getComment())
                .addValue("theme", entity.getTheme())
                .addValue("lastUpdatedAt", LocalDateTime.now().toString())
                .addValue("id", entity.getAlarmId());
        jdbcTemplate.update(sql, sqlParameterSource);
    }

    private Alarm toEntity(final ResultSet resultSet) throws SQLException {
        final Alarm entity = new Alarm();
        entity.setAlarmId(resultSet.getLong(KEY_COLUMN_NAME));
        entity.setItemName(resultSet.getString("item_name"));
        entity.setItemCode(resultSet.getString("item_code"));
        entity.setRecommendPrice(resultSet.getInt("recommend_price"));
        entity.setLosscutPrice(resultSet.getInt("losscut_price"));
        entity.setComment(resultSet.getString("comment"));
        entity.setTheme(resultSet.getString("theme"));

        return entity;
    }
}
