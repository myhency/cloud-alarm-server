package cloud.stock.sevenbread.app;

import cloud.stock.sevenbread.domain.sevenbread.SevenBreadDeletedItem;
import cloud.stock.sevenbread.infra.SevenBreadDeletedRepository;
import cloud.stock.sevenbread.ui.dataholder.SevenBreadDeletedItemDataHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class SevenBreadDeletedService {

    private final SevenBreadDeletedRepository sevenBreadDeletedRepository;

    @Transactional
    public SevenBreadDeletedItemDataHolder create(String itemName,
                                                  String itemCode,
                                                  Integer capturedPrice,
                                                  String capturedDate,
                                                  String majorHandler,
                                                  String reason
    ) {
        SevenBreadDeletedItem newSevenBreadDeletedItem = SevenBreadDeletedItem.createSevenBreadDeletedCreationRequest(
                itemName, itemCode, capturedPrice, capturedDate, majorHandler, reason
        );

        newSevenBreadDeletedItem = sevenBreadDeletedRepository.save(newSevenBreadDeletedItem);

        return SevenBreadDeletedItemDataHolder.builder()
                .id(newSevenBreadDeletedItem.getId())
                .build();
    }

    public List<SevenBreadDeletedItem> getSevenBreadDeletedWinItems() {
        return sevenBreadDeletedRepository.findAllByReason();
    }
}
