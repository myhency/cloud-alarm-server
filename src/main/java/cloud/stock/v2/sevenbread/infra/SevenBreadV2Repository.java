package cloud.stock.v2.sevenbread.infra;

import cloud.stock.v2.sevenbread.domain.SevenBread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SevenBreadV2Repository extends JpaRepository<SevenBread, Long> {
    Optional<SevenBread> findByItemCode(String itemCode);

    @Query(value = "select sb.created_date,\n" +
            "       si.item_name,\n" +
            "       sb.item_code,\n" +
            "       sb.captured_date,\n" +
            "       sb.captured_price,\n" +
            "       sb.lowest_price,\n" +
            "       sb.major_handler\n" +
            "       from seven_bread sb left outer join stock_item si\n" +
            "           on sb.item_code = si.item_code\n" +
            "order by sb.captured_date desc, sb.created_date desc", nativeQuery = true)
    Collection<Object> findAllItems();


}
