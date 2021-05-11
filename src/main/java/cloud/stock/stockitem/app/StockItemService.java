package cloud.stock.stockitem.app;

import cloud.stock.stockitem.domain.StockItem;
import cloud.stock.stockitem.infra.StockItemDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StockItemService {

    private final StockItemDao stockItemDao;

    public StockItemService(final StockItemDao stockItemDao) {
        this.stockItemDao = stockItemDao;
    }

    @Transactional
    public StockItem create(final StockItem stockItem) {
        stockItem.setCreatedAt(LocalDateTime.now());
        stockItem.setLastUpdatedAt(LocalDateTime.now());

        return stockItemDao.create(stockItem);
    }

    @Transactional
    public StockItem changeTheme(final Long id, final StockItem stockItem) {
        final StockItem savedStockItem = stockItemDao.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        savedStockItem.setTheme(stockItem.getTheme());

        stockItemDao.create(savedStockItem);

        return savedStockItem;
    }

    public List<StockItem> list() {
        return stockItemDao.findAll();
    }
}
