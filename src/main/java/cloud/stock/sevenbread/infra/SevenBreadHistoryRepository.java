package cloud.stock.sevenbread.infra;

import cloud.stock.sevenbread.domain.sevenbread.SevenBreadItemHistory;
import cloud.stock.sevenbread.ui.dto.SevenBreadStatisticsHistoryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.util.Collection;

@Repository
public interface SevenBreadHistoryRepository extends JpaRepository<SevenBreadItemHistory, Long> {

    @Query(value = "SELECT DATE_FORMAT(MAX(lowest_captured_date), '%Y-%m-%d') as capturedDate, KK.item_code as itemCode, KK.item_name as itemName, KK.captured_price as capturedPrice, DATE_FORMAT(MAX(highest_date), '%Y-%m-%d') as highestDate, highest_price as highestPrice, DATE_FORMAT(MAX(lowest_date), '%Y-%m-%d') as lowestDate, lowest_price as lowestPrice, DATE_FORMAT(MAX(highest_captured_date), '%Y-%m-%d') as highestCapturedDate FROM (SELECT AA.item_code, AA.item_name, AA.highest_price, BB.created_date as highest_date, BB.captured_date as highest_captured_date, captured_price\n" +
            "\t\tFROM (SELECT  item_code, \n" +
            "\t\t\t\titem_name, \n" +
            "\t\t\t\tmax(highest_price) as highest_price\n" +
            "\t\tFROM clouddb.seven_bread_item_history \n" +
            "\t\tgroup by item_code, item_name\n" +
            "\t\t) AA INNER JOIN clouddb.seven_bread_item_history BB\n" +
            "\t\t\t\tON AA.item_code = BB.item_code AND AA.highest_price = BB.highest_price\n" +
            ") KK left outer join\n" +
            "(  SELECT AA.item_code, AA.item_name, AA.lowest_price, BB.created_date as lowest_date, BB.captured_date as lowest_captured_date , captured_price\n" +
            "\t\tFROM (SELECT  item_code, \t\t\t\t\t item_name, \n" +
            "\t\t\t\t\t min(lowest_price) as lowest_price,\n" +
            "\t\t\t\t\t created_date as lowest_date,\n" +
            "\t\t\t\t\t captured_date as lowest_captured_date\n" +
            "\t\t\tFROM clouddb.seven_bread_item_history \n" +
            "\t\t\tgroup by item_code, item_name\n" +
            "\t\t) AA INNER JOIN clouddb.seven_bread_item_history BB\n" +
            "\t\t\t\tON AA.item_code = BB.item_code AND AA.lowest_price = BB.lowest_price\n" +
            ") LL\n" +
            "on KK.item_code = LL.item_code\n" +
            "GROUP BY KK.item_code, KK.item_name, highest_price,lowest_price, KK.captured_price\n" +
            "ORDER BY capturedPrice / highestPrice",
            nativeQuery = true)
    Collection<Object> findSevenBreadItemHistoriesWithBenefit();

}
