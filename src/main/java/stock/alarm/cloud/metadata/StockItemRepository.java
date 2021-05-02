package stock.alarm.cloud.metadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StockItemRepository  extends JpaRepository<StockItem, Integer> {

}
