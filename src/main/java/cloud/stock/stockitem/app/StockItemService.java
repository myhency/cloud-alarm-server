package cloud.stock.stockitem.app;

import cloud.stock.alarm.domain.alarm.Alarm;
import cloud.stock.alarm.infra.AlarmRepository;
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
    private final AlarmRepository alarmRepository;

    public StockItemService(final StockItemDao stockItemDao, StockItemRepository stockItemRepository, AlarmRepository alarmRepository) {
        this.stockItemDao = stockItemDao;
        this.stockItemRepository = stockItemRepository;
        this.alarmRepository = alarmRepository;
    }

    @Transactional
    public StockItem create(final StockItem stockItem) {

        return stockItemRepository.save(stockItem);
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

    @Transactional
    public StockItem changeName(StockItem stockItem) {
        Alarm toBeChangedAlarm = alarmRepository.findOneByItemCode(stockItem.getItemCode());
        if (toBeChangedAlarm != null) {
            toBeChangedAlarm.setItemName(stockItem.getItemName());
            alarmRepository.save(toBeChangedAlarm);
        }

        StockItem toBeChanged = stockItemRepository.findOneByItemCode(stockItem.getItemCode());
        toBeChanged.setItemName(stockItem.getItemName());

        return stockItemRepository.save(toBeChanged);
    }
}
