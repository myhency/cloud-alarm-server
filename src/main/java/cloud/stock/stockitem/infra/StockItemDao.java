package cloud.stock.stockitem.infra;

import cloud.stock.stockitem.domain.StockItem;

import java.util.List;
import java.util.Optional;

public interface StockItemDao {

    StockItem create(StockItem entity);

    List<StockItem> findAll();

    Optional<StockItem> findById(Long id);
}
