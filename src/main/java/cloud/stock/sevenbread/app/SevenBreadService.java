package cloud.stock.sevenbread.app;

import cloud.stock.sevenbread.domain.exceptions.AlreadyExistSevenBreadItemException;
import cloud.stock.sevenbread.domain.exceptions.NotExistSevenBreadItemException;
import cloud.stock.sevenbread.domain.sevenbread.SevenBreadItem;
import cloud.stock.sevenbread.infra.SevenBreadDeletedRepository;
import cloud.stock.sevenbread.infra.SevenBreadRepository;
import cloud.stock.sevenbread.ui.dataholder.SevenBreadItemDataHolder;
import cloud.stock.sevenbread.ui.dto.SevenBreadItemCreationRequestDto;
import cloud.stock.stockitem.domain.exceptions.NotExistStockItemException;
import cloud.stock.stockitem.infra.StockItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class SevenBreadService {

    private final SevenBreadRepository sevenBreadRepository;
    private final StockItemRepository stockItemRepository;
    private static final String TOPIC = "seven_bread";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public SevenBreadItemDataHolder create(String itemName,
                                           String itemCode,
                                           String capturedDate,
                                           String majorHandler,
                                           String theme
    ) {
        if(sevenBreadRepository.findByItemCode(itemCode).isPresent())
            throw new AlreadyExistSevenBreadItemException();

        SevenBreadItem newSevenBreadItem = SevenBreadItem.createSevenBreadCreationRequest(
                itemName,
                itemCode,
                capturedDate,
                majorHandler,
                theme
        );

        newSevenBreadItem = sevenBreadRepository.save(newSevenBreadItem);

        produceKafkaMessage(new SevenBreadItemCreationRequestDto(
                newSevenBreadItem.getItemName(),
                newSevenBreadItem.getItemCode(),
                newSevenBreadItem.getMajorHandler(),
                newSevenBreadItem.getCapturedDate(),
                newSevenBreadItem.getTheme()
        ));

        return SevenBreadItemDataHolder.builder()
                .id(newSevenBreadItem.getId())
                .build();
    }

    @Transactional
    public SevenBreadItemDataHolder update(String itemCode,
                                           Integer capturedPrice,
                                           Integer closingPrice,
                                           Double fluctuationRate,
                                           Integer priceByYesterday,
                                           Integer volume
    ) {
        SevenBreadItem toBeModifiedSevenBreadItem = sevenBreadRepository.findByItemCode(itemCode)
                .orElseThrow(NotExistSevenBreadItemException::new);

        toBeModifiedSevenBreadItem.setCapturedPrice(capturedPrice);
        toBeModifiedSevenBreadItem.setClosingPrice(closingPrice);
        toBeModifiedSevenBreadItem.setFluctuationRate(fluctuationRate);
        toBeModifiedSevenBreadItem.setPriceByYesterday(priceByYesterday);
        toBeModifiedSevenBreadItem.setVolume(volume);

        toBeModifiedSevenBreadItem = sevenBreadRepository.save(toBeModifiedSevenBreadItem);

        return SevenBreadItemDataHolder.builder()
                .id(toBeModifiedSevenBreadItem.getId())
                .build();
    }

    @Transactional
    public SevenBreadItemDataHolder updateToday(String itemCode,
                                                Integer closingPrice,
                                                Double fluctuationRate,
                                                Integer priceByYesterday,
                                                Integer volume
    ) {
        SevenBreadItem toBeModifiedSevenBreadItem = sevenBreadRepository.findByItemCode(itemCode)
                .orElseThrow(NotExistSevenBreadItemException::new);

        toBeModifiedSevenBreadItem.setClosingPrice(closingPrice);
        toBeModifiedSevenBreadItem.setFluctuationRate(fluctuationRate);
        toBeModifiedSevenBreadItem.setPriceByYesterday(priceByYesterday);
        toBeModifiedSevenBreadItem.setVolume(volume);

        return SevenBreadItemDataHolder.builder()
                .id(toBeModifiedSevenBreadItem.getId())
                .build();
    }

    @Transactional
    public SevenBreadItemDataHolder delete(Long id) {
        SevenBreadItem toBeDeletedSevenBreadItem = sevenBreadRepository.findById(id)
                .orElseThrow(NotExistSevenBreadItemException::new);

        sevenBreadRepository.delete(toBeDeletedSevenBreadItem);

        return SevenBreadItemDataHolder.builder()
                .id(toBeDeletedSevenBreadItem.getId())
                .itemCode(toBeDeletedSevenBreadItem.getItemCode())
                .itemName(toBeDeletedSevenBreadItem.getItemName())
                .capturedPrice(toBeDeletedSevenBreadItem.getCapturedPrice())
                .majorHandler(toBeDeletedSevenBreadItem.getMajorHandler())
                .capturedDate(toBeDeletedSevenBreadItem.getCapturedDate())
                .build();
    }

    public List<SevenBreadItem> list() {
        return sevenBreadRepository.findAll(Sort.by(Sort.Direction.DESC, "capturedDate"));
    }

    private void produceKafkaMessage(SevenBreadItemCreationRequestDto sevenBreadItemCreationRequestDto) {
        try {
            kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(sevenBreadItemCreationRequestDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public SevenBreadItem getSevenBreadItemDetailByItemCode(String itemCode) {
        return sevenBreadRepository.findOneByItemCode(itemCode);
    }
}
