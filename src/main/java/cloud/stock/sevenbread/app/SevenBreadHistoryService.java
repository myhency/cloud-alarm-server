package cloud.stock.sevenbread.app;

import cloud.stock.sevenbread.domain.sevenbread.SevenBreadItemHistory;
import cloud.stock.sevenbread.infra.SevenBreadHistoryRepository;
import cloud.stock.sevenbread.ui.dataholder.SevenBreadItemHistoryDataHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class SevenBreadHistoryService {

    private final SevenBreadHistoryRepository sevenBreadHistoryRepository;

    @Transactional
    public SevenBreadItemHistoryDataHolder create(String itemName,
                                                  String itemCode,
                                                  Integer startingPrice,
                                                  Integer highestPrice,
                                                  Integer lowestPrice,
                                                  Integer closingPrice,
                                                  String capturedDate
    ) {
        SevenBreadItemHistory newSevenBreadItemHistory = SevenBreadItemHistory.createSevenBreadItemHistory(
                itemName,
                itemCode,
                startingPrice,
                highestPrice,
                lowestPrice,
                closingPrice,
                capturedDate
        );

        newSevenBreadItemHistory = sevenBreadHistoryRepository.save(newSevenBreadItemHistory);

        return SevenBreadItemHistoryDataHolder.builder()
                .id(newSevenBreadItemHistory.getId())
                .build();
    }
}
