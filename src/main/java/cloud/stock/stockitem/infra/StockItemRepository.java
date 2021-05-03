package cloud.stock.stockitem.infra;

import cloud.stock.stockitem.domain.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, Integer> {

}
