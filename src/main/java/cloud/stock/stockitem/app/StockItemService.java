package cloud.stock.stockitem.app;

import cloud.stock.alarm.domain.alarm.Alarm;
import cloud.stock.alarm.infra.AlarmRepository;
import cloud.stock.stockitem.domain.StockItem;
import cloud.stock.stockitem.domain.exceptions.AlreadyExistStockItemException;
import cloud.stock.stockitem.domain.exceptions.NotExistStockItemException;
import cloud.stock.stockitem.infra.StockItemDao;
import cloud.stock.stockitem.infra.StockItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public StockItem create(final StockItem newStockItem) {
        stockItemRepository.findByItemCode(newStockItem.getItemCode())
                .ifPresent(it -> {
                    throw new AlreadyExistStockItemException();
                });

        return stockItemRepository.save(newStockItem);
    }

    @Transactional
    public StockItem changeTheme(final Long id, final StockItem stockItem) {
        final StockItem savedStockItem = stockItemDao.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        savedStockItem.setTheme(stockItem.getTheme());

        stockItemDao.create(savedStockItem);

        return savedStockItem;
    }

    public Page<StockItem> list(final Pageable pageable) {
        return stockItemRepository.findAll(pageable);
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

    public StockItem getStockItemByItemCode(String itemCode) {
        return stockItemRepository.findByItemCode(itemCode)
                .orElseThrow(NotExistStockItemException::new);
    }

    @Transactional
    public Long modify(StockItem stockItem) {
        StockItem toBeChanged = stockItemRepository.findByItemCode(stockItem.getItemCode())
                .orElseGet(() -> stockItemRepository.save(stockItem));

        toBeChanged.setTheme(stockItem.getTheme());

        return stockItemRepository.save(toBeChanged).getId();
    }
}
