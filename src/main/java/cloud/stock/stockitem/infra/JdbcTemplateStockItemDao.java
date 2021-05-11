package cloud.stock.stockitem.infra;

import cloud.stock.alarm.domain.Alarm;
import cloud.stock.stockitem.domain.StockItem;
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
public class JdbcTemplateStockItemDao implements StockItemDao {

    private static final String TABLE_NAME = "stockitems";
    private static final String KEY_COLUMN_NAME = "id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateStockItemDao(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN_NAME);
    }

    @Override
    public StockItem create(StockItem entity) {
        if(Objects.isNull(entity.getId())) {
            final SqlParameterSource parameters = new BeanPropertySqlParameterSource(entity);
            final Number key = jdbcInsert.executeAndReturnKey(parameters);
            return select(key.longValue());
        }

        update(entity);
        return entity;
    }

    @Override
    public List<StockItem> findAll() {
        final String sql = "SELECT " +
                "id, item_name, item_code, theme, created_at, last_updated_at " +
                "FROM stockitems";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    @Override
    public Optional<StockItem> findById(Long id) {
        try {
            return Optional.of(select(id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private StockItem select(final Long id) {
        final String sql = "SELECT " +
                "id, item_name, item_code, theme, " +
                "created_at, last_updated_at " +
                "FROM stockitems " +
                "WHERE id = (:id)";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameterSource, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    private void update(final StockItem entity) {
        final String sql = "UPDATE stockitems " +
                "SET theme = (:theme) " +
                "WHERE id = (:id)";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("theme", entity.getTheme())
                .addValue("id", entity.getId());
        jdbcTemplate.update(sql, sqlParameterSource);
    }

    private StockItem toEntity(final ResultSet resultSet) throws SQLException {
        final StockItem entity = new StockItem();
        entity.setId(resultSet.getLong(KEY_COLUMN_NAME));
        entity.setItemName(resultSet.getString("item_name"));
        entity.setItemCode(resultSet.getString("item_code"));
        entity.setTheme(resultSet.getString("theme"));
        entity.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
        entity.setLastUpdatedAt(resultSet.getObject("last_updated_at", LocalDateTime.class));

        return entity;
    }
}
