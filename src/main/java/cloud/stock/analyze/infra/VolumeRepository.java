package cloud.stock.analyze.infra;

import cloud.stock.analyze.domain.volume.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Long> {

    @Query(value = "SELECT distinct date_format(created_date, '%Y-%m-%d') FROM volume ORDER BY 1 DESC", nativeQuery = true)
    Collection<String> findAllDates();

    @Query(value = "SELECT v.id, " +
            "v.modified_date, " +
            "v.created_date, " +
            "v.closing_price, " +
            "v.fluctuation_rate, " +
            "v.item_code, " +
            "v.item_name, " +
            "v.market_cap, " +
            "v.number_of_outstanding_shares, " +
            "v.volume, " +
            "v.market_type, " +
            "si.theme as theme\n" +
            "FROM volume v left outer join stock_item si " +
            "on v.item_code = si.item_code\n" +
            "WHERE v.created_date " +
            "between str_to_date(:from, '%Y-%m-%d') " +
            "and str_to_date(:to, '%Y-%m-%d')", nativeQuery = true)
    List<Volume> findAllByCreatedDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query(value = "SELECT v.id, " +
            "date_format(v.created_date, '%Y-%m-%d') as created_date, " +
            "v.modified_date, " +
            "v.closing_price, " +
            "v.fluctuation_rate, " +
            "v.item_code, " +
            "v.item_name, " +
            "v.market_cap, " +
            "v.number_of_outstanding_shares, " +
            "v.volume, " +
            "v.market_type, " +
            "si.theme as theme\n" +
            "FROM volume v left outer join stock_item si " +
            "on v.item_code = si.item_code\n" +
            "WHERE v.item_name = :itemName ", nativeQuery = true)
    List<Volume> findAllByItemName(@Param("itemName") String itemName);

    @Query(value = "SELECT v.id, " +
            "date_format(v.created_date, '%Y-%m-%d') as created_date, " +
            "v.modified_date, " +
            "v.closing_price, " +
            "v.fluctuation_rate, " +
            "v.item_code, " +
            "v.item_name, " +
            "v.market_cap, " +
            "v.number_of_outstanding_shares, " +
            "v.volume, " +
            "v.market_type, " +
            "si.theme as theme\n" +
            "FROM volume v left outer join stock_item si " +
            "on v.item_code = si.item_code\n" +
            "WHERE si.theme like %:theme%", nativeQuery = true)
    List<Volume> findAllByTheme(@Param("theme") String theme);
}
