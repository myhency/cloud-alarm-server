package cloud.stock.sevenbread.app;

import cloud.stock.sevenbread.domain.sevenbread.SevenBreadItemHistory;
import cloud.stock.sevenbread.infra.SevenBreadHistoryRepository;
import cloud.stock.sevenbread.ui.dataholder.SevenBreadItemHistoryDataHolder;
import cloud.stock.sevenbread.ui.dto.SevenBreadStatisticsHistoryResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
                                                  String capturedDate,
                                                  Integer capturedPrice
    ) {
        SevenBreadItemHistory newSevenBreadItemHistory = SevenBreadItemHistory.createSevenBreadItemHistory(
                itemName,
                itemCode,
                startingPrice,
                highestPrice,
                lowestPrice,
                closingPrice,
                capturedDate,
                capturedPrice
        );

        newSevenBreadItemHistory = sevenBreadHistoryRepository.save(newSevenBreadItemHistory);

        return SevenBreadItemHistoryDataHolder.builder()
                .id(newSevenBreadItemHistory.getId())
                .build();
    }

    public List<SevenBreadStatisticsHistoryResponseDto> getStatistics() {
        List<SevenBreadStatisticsHistoryResponseDto> result = new ArrayList<>();
        List<Object> obj = (List)sevenBreadHistoryRepository.findSevenBreadItemHistoriesWithBenefit();
        obj.stream().forEach((item) -> {
            SevenBreadStatisticsHistoryResponseDto sevenBreadStatisticsHistoryResponseDto = new SevenBreadStatisticsHistoryResponseDto();
            Object[] arrayList = (Object[])item;
            for(int i = 0; i < arrayList.length; i++) {
                switch (i) {
                    case 0:
                        sevenBreadStatisticsHistoryResponseDto.setCapturedDate((String)arrayList[i]);
                        break;
                    case 1:
                        sevenBreadStatisticsHistoryResponseDto.setItemCode((String)arrayList[i]);
                        break;
                    case 2:
                        sevenBreadStatisticsHistoryResponseDto.setItemName((String)arrayList[i]);
                        break;
                    case 3:
                        sevenBreadStatisticsHistoryResponseDto.setCapturedPrice((Integer)arrayList[i]);
                        break;
                    case 4:
                        sevenBreadStatisticsHistoryResponseDto.setHighestDate((String)arrayList[i]);
                        break;
                    case 5:
                        sevenBreadStatisticsHistoryResponseDto.setHighestPrice((Integer)arrayList[i]);
                        break;
                    case 6:
                        sevenBreadStatisticsHistoryResponseDto.setLowestDate((String)arrayList[i]);
                        break;
                    case 7:
                        sevenBreadStatisticsHistoryResponseDto.setLowestPrice((Integer)arrayList[i]);
                        break;
                    case 8:
                        sevenBreadStatisticsHistoryResponseDto.setHighestCapturedDate((String)arrayList[i]);
                        break;
                    default:
                        break;
                }
            }
            result.add(sevenBreadStatisticsHistoryResponseDto);
        });
        return result;
    }
}
