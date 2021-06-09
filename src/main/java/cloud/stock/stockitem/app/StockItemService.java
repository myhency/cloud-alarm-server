package cloud.stock.stockitem.app;

import cloud.stock.stockitem.domain.StockItem;
import cloud.stock.stockitem.infra.StockItemDao;
import cloud.stock.stockitem.infra.StockItemRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class StockItemService {

    private final StockItemDao stockItemDao;
    private final StockItemRepository stockItemRepository;

    public StockItemService(final StockItemDao stockItemDao, StockItemRepository stockItemRepository) {
        this.stockItemDao = stockItemDao;
        this.stockItemRepository = stockItemRepository;
    }

    @Transactional
    public StockItem create(final StockItem stockItem) {

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
        return stockItemRepository.findAll();
    }

    public StockItem selectThemeByItemCode(String itemCode) {
        return stockItemDao.findThemeByItemCode(itemCode);
    }
}
