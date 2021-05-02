package cloud.stock.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository  extends JpaRepository<StockItem, Integer> {

}
