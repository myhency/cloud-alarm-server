package cloud.stock.stockitem.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockItemRepository extends JpaRepository<StockItem, String> {

    Optional<StockItem> findByItemCode(String itemCode);
}
