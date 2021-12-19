package cloud.stock.v2.sevenbread.app;

import cloud.stock.sevenbread.domain.exceptions.AlreadyExistSevenBreadItemException;

import cloud.stock.stockitem.domain.StockItem;
import cloud.stock.stockitem.domain.exceptions.NotExistStockItemException;
import cloud.stock.stockitem.infra.StockItemRepository;
import cloud.stock.v2.sevenbread.domain.SevenBreadArchive;
import cloud.stock.v2.sevenbread.domain.SevenBreadDailyTrace;
import cloud.stock.v2.sevenbread.domain.SevenBreadWin;
import cloud.stock.v2.sevenbread.infra.SevenBreadArchiveRepository;
import cloud.stock.v2.sevenbread.infra.SevenBreadDailyTraceRepository;
import cloud.stock.v2.sevenbread.infra.SevenBreadWinRepository;
import cloud.stock.v2.sevenbread.ui.dto.*;
import cloud.stock.v2.sevenbread.domain.SevenBread;
import cloud.stock.v2.sevenbread.infra.SevenBreadV2Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class SevenBreadV2Service {

    private final SevenBreadV2Repository sevenBreadV2Repository;
    private final SevenBreadDailyTraceRepository sevenBreadDailyTraceRepository;
    private final SevenBreadArchiveRepository sevenBreadArchiveRepository;
    private final StockItemRepository stockItemRepository;
    private final SevenBreadWinRepository sevenBreadWinRepository;
    private static final String TOPIC = "seven-bread-v2";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public Long create(final SevenBreadItemCreationRequestDto sevenBreadItemCreationRequestDto) {
        String itemCode = sevenBreadItemCreationRequestDto.getItemCode();
        String capturedDate = sevenBreadItemCreationRequestDto.getCapturedDate();
        String majorHandler = sevenBreadItemCreationRequestDto.getMajorHandler();

        if(sevenBreadV2Repository.findByItemCode(itemCode).isPresent())
            throw new AlreadyExistSevenBreadItemException();

        SevenBread newSevenBreadItem = SevenBread.createSevenBreadCreationRequest(
                itemCode,
                capturedDate,
                majorHandler
        );

        newSevenBreadItem = sevenBreadV2Repository.save(newSevenBreadItem);

        produceKafkaMessage(sevenBreadItemCreationRequestDto);

        return newSevenBreadItem.getId();
    }

    @Transactional
    public Long createDailyTrace(final SevenBreadDailyTraceCreationRequestDto sevenBreadDailyTraceCreationRequestDto) {
        String itemCode = sevenBreadDailyTraceCreationRequestDto.getItemCode();
        String closingDate = sevenBreadDailyTraceCreationRequestDto.getClosingDate();

        if(sevenBreadDailyTraceRepository.findByItemCodeAndClosingDate(itemCode, closingDate).isPresent())
            throw new AlreadyExistSevenBreadItemException();

        SevenBreadDailyTrace sevenBreadDailyTrace = SevenBreadDailyTrace.builder()
                .itemCode(itemCode)
                .build();

//        newSevenBreadItem = sevenBreadV2Repository.save(newSevenBreadItem);

        return sevenBreadDailyTrace.getId();
    }

    public List<SevenBreadListResponseDto> list() {
        List<SevenBreadListResponseDto> result = new ArrayList<>();
        List<Object> obj = (List)sevenBreadV2Repository.findAllItems();
        obj.stream().forEach((item) -> {
            SevenBreadListResponseDto sevenBreadListResponseDto = new SevenBreadListResponseDto();
            Object[] arrayList = (Object[])item;
            for(int i = 0; i < arrayList.length; i++) {
                switch (i) {
                    case 0:
                        sevenBreadListResponseDto.setCreatedDate(((Timestamp)arrayList[i]).toLocalDateTime());
                        break;
                    case 1:
                        sevenBreadListResponseDto.setItemName((String)arrayList[i]);
                        break;
                    case 2:
                        sevenBreadListResponseDto.setItemCode((String)arrayList[i]);
                        break;
                    case 3:
                        sevenBreadListResponseDto.setCapturedDate((String)arrayList[i]);
                        break;
                    case 4:
                        sevenBreadListResponseDto.setCapturedPrice((Integer)arrayList[i]);
                        break;
                    case 5:
                        sevenBreadListResponseDto.setLowestPrice((Integer)arrayList[i]);
                        break;
                    case 6:
                        sevenBreadListResponseDto.setMajorHandler((String)arrayList[i]);
                        break;
                    default:
                        break;
                }
            }
            result.add(sevenBreadListResponseDto);
        });
        return result;
    }

    @Transactional
    public Long update(String itemCode, Integer capturedPrice, Integer lowestPrice) {
        SevenBread sevenBread = sevenBreadV2Repository.findByItemCode(itemCode)
                .orElseThrow(NotExistStockItemException::new);

        sevenBread.setCapturedPrice(capturedPrice);
        sevenBread.setLowestPrice(lowestPrice);

        return sevenBreadV2Repository.save(sevenBread).getId();
    }

    @Transactional
    public Long upsertSevenBreadDailyChartTrace(
            SevenBreadDailyChartTraceRequestDto sevenBreadDailyChartTraceRequestDto
    ) {
        Optional<SevenBreadDailyTrace> item = sevenBreadDailyTraceRepository
                .findByItemCodeAndClosingDate(sevenBreadDailyChartTraceRequestDto.getItemCode(),
                        sevenBreadDailyChartTraceRequestDto.getClosingDate());

        if (item.isPresent()
//                && !item.get().getClosingPrice().toString().isEmpty()
//                && !item.get().getHighestPrice().toString().isEmpty()
//                && !item.get().getLowestPrice().toString().isEmpty()
                && item.get().getClosingPrice() != null
                && item.get().getHighestPrice() != null
                && item.get().getLowestPrice() != null
        ) {
            return item.get().getId();
        }

        if (!item.isPresent()) {
            SevenBreadDailyTrace newSevenBreadDailyTrace = SevenBreadDailyTrace.builder()
                    .closingDate(sevenBreadDailyChartTraceRequestDto.getClosingDate())
                    .itemCode(sevenBreadDailyChartTraceRequestDto.getItemCode())
                    .highestPrice(sevenBreadDailyChartTraceRequestDto.getHighestPrice())
                    .lowestPrice(sevenBreadDailyChartTraceRequestDto.getLowestPrice())
                    .closingPrice(sevenBreadDailyChartTraceRequestDto.getClosingPrice())
                    .modifyPriceType(sevenBreadDailyChartTraceRequestDto.getModifyPriceType())
                    .modifyRate(sevenBreadDailyChartTraceRequestDto.getModifyRate())
                    .modifyEvent(sevenBreadDailyChartTraceRequestDto.getModifyEvent())
                    .build();

            return sevenBreadDailyTraceRepository.save(newSevenBreadDailyTrace).getId();
        }

        item.get().setClosingDate(sevenBreadDailyChartTraceRequestDto.getClosingDate());
        item.get().setItemCode(sevenBreadDailyChartTraceRequestDto.getItemCode());
        item.get().setHighestPrice(sevenBreadDailyChartTraceRequestDto.getHighestPrice());
        item.get().setLowestPrice(sevenBreadDailyChartTraceRequestDto.getLowestPrice());
        item.get().setClosingPrice(sevenBreadDailyChartTraceRequestDto.getClosingPrice());
        item.get().setModifyPriceType(sevenBreadDailyChartTraceRequestDto.getModifyPriceType());
        item.get().setModifyRate(sevenBreadDailyChartTraceRequestDto.getModifyRate());
        item.get().setModifyEvent(sevenBreadDailyChartTraceRequestDto.getModifyEvent());

        return sevenBreadDailyTraceRepository.save(item.get()).getId();
    }

    @Transactional
    public Long upsertSevenBreadDailyBuyingTrace(
            SevenBreadDailyBuyingTraceRequestDto sevenBreadDailyBuyingTraceRequestDto
    ) {
        Optional<SevenBreadDailyTrace> item = sevenBreadDailyTraceRepository
                .findByItemCodeAndClosingDate(sevenBreadDailyBuyingTraceRequestDto.getItemCode(),
                        sevenBreadDailyBuyingTraceRequestDto.getClosingDate());

        if (item.isPresent()
//                && !item.get().getWBuyAmount().toString().isEmpty()
//                && !item.get().getPBuyAmount().toString().isEmpty()
//                && !item.get().getGBuyAmount().toString().isEmpty()
                && item.get().getWBuyAmount() != null
                && item.get().getPBuyAmount() != null
                && item.get().getGBuyAmount() != null
        ) {
            log.info("return it is exist");
            return item.get().getId();
        }

        if (!item.isPresent()) {
            log.info("return it is not exist");
            SevenBreadDailyTrace newSevenBreadDailyTrace = SevenBreadDailyTrace.builder()
                .closingDate(sevenBreadDailyBuyingTraceRequestDto.getClosingDate())
                .itemCode(sevenBreadDailyBuyingTraceRequestDto.getItemCode())
                .gBuyAmount(sevenBreadDailyBuyingTraceRequestDto.getGBuyAmount())
                .wBuyAmount(sevenBreadDailyBuyingTraceRequestDto.getWBuyAmount())
                .pBuyAmount(sevenBreadDailyBuyingTraceRequestDto.getPBuyAmount())
                .build();

            return sevenBreadDailyTraceRepository.save(newSevenBreadDailyTrace).getId();
        }

        log.info("return it is update");

        item.get().setItemCode(sevenBreadDailyBuyingTraceRequestDto.getItemCode());
        item.get().setClosingDate(sevenBreadDailyBuyingTraceRequestDto.getClosingDate());
        item.get().setGBuyAmount(sevenBreadDailyBuyingTraceRequestDto.getGBuyAmount());
        item.get().setWBuyAmount(sevenBreadDailyBuyingTraceRequestDto.getWBuyAmount());
        item.get().setPBuyAmount(sevenBreadDailyBuyingTraceRequestDto.getPBuyAmount());

        return sevenBreadDailyTraceRepository.save(item.get()).getId();
    }

    public SevenBreadItemByItemCodeResponseDto getSevenBreadItemByItemCode(String itemCode) {
        SevenBread sevenBread = sevenBreadV2Repository.findByItemCode(itemCode)
                .orElseThrow(NotExistStockItemException::new);

        List<SevenBreadDailyTrace> sevenBreadDailyTrace = sevenBreadDailyTraceRepository.findByItemCodeAndIsReoccur(itemCode
        , true);

        List<String> dateList = new ArrayList<>();
        if (sevenBreadDailyTrace.size() > 0) {
            sevenBreadDailyTrace.forEach(item -> dateList.add(item.getClosingDate()));
        }

        StockItem stockItem = stockItemRepository.findOneByItemCode(itemCode);

        return SevenBreadItemByItemCodeResponseDto
                .builder()
                .itemName(stockItem.getItemName())
                .itemCode(sevenBread.getItemCode())
                .capturedDate(sevenBread.getCapturedDate())
                .majorHandler(sevenBread.getMajorHandler())
                .reOccurDateList(dateList)
                .build();
    }

    @Transactional
    public List<Long> updateReoccurDateList(SevenBreadItemTraceUpdateRequestDto sevenBreadItemTraceUpdateRequestDto) {
        List<SevenBreadDailyTrace> sevenBreadDailyTraces = sevenBreadDailyTraceRepository
                .findAllByItemCode(sevenBreadItemTraceUpdateRequestDto.getItemCode());

        if (sevenBreadDailyTraces.size() < 1) {
            throw new NotExistStockItemException();
        }

        List<Long> result = new ArrayList<>();

        for (SevenBreadDailyTrace sevenBreadDailyTrace : sevenBreadDailyTraces) {
            sevenBreadDailyTrace.setIsReoccur(null);
            sevenBreadDailyTraceRepository.save(sevenBreadDailyTrace);
        }

        for (String date : sevenBreadItemTraceUpdateRequestDto.getReoccurDateList()) {
            for (SevenBreadDailyTrace sevenBreadDailyTrace : sevenBreadDailyTraces) {
                if (sevenBreadDailyTrace.getClosingDate().equals(date.replace("-", ""))) {
                    sevenBreadDailyTrace.setIsReoccur(true);
                    result.add(sevenBreadDailyTraceRepository.save(sevenBreadDailyTrace).getId());
                }
            }
        }

        return result;
    }

    @Transactional
    public Long handleDeleteSold(SevenBreadItemDeleteRequestDto sevenBreadItemDeleteRequestDto) {
        SevenBread sevenBread = sevenBreadV2Repository.findByItemCode(sevenBreadItemDeleteRequestDto.getItemCode())
                .orElseThrow(NotExistStockItemException::new);

        List<SevenBreadDailyTrace> list = sevenBreadDailyTraceRepository
                .findAllByItemCode(sevenBreadItemDeleteRequestDto
                        .getItemCode());

        for (SevenBreadDailyTrace sevenBreadDailyTrace : list) {
            SevenBreadArchive sevenBreadArchive = SevenBreadArchive.builder()
                    .closingDate(sevenBreadDailyTrace.getClosingDate())
                    .closingPrice(sevenBreadDailyTrace.getClosingPrice())
                    .gBuyAmount(sevenBreadDailyTrace.getGBuyAmount())
                    .highestPrice(sevenBreadDailyTrace.getHighestPrice())
                    .isReoccur(sevenBreadDailyTrace.getIsReoccur())
                    .itemCode(sevenBreadDailyTrace.getItemCode())
                    .lowestPrice(sevenBreadDailyTrace.getLowestPrice())
                    .pBuyAmount(sevenBreadDailyTrace.getPBuyAmount())
                    .wBuyAmount(sevenBreadDailyTrace.getWBuyAmount())
                    .tradingState(sevenBreadItemDeleteRequestDto.getType())
                    .build();

            sevenBreadArchiveRepository.save(sevenBreadArchive);
            sevenBreadDailyTraceRepository.delete(sevenBreadDailyTrace);
        }

        sevenBreadV2Repository.delete(sevenBread);

        return sevenBread.getId();
    }

    @Transactional
    public Long handleDeleteWin(SevenBreadItemDeleteRequestDto sevenBreadItemDeleteRequestDto) {
        SevenBread sevenBread = sevenBreadV2Repository.findByItemCode(sevenBreadItemDeleteRequestDto.getItemCode())
                .orElseThrow(NotExistStockItemException::new);

        List<SevenBreadDailyTrace> list = sevenBreadDailyTraceRepository
                .findAllByItemCode(sevenBreadItemDeleteRequestDto
                        .getItemCode());

        for (SevenBreadDailyTrace sevenBreadDailyTrace : list) {
            SevenBreadWin sevenBreadWin = SevenBreadWin.builder()
                    .closingDate(sevenBreadDailyTrace.getClosingDate())
                    .closingPrice(sevenBreadDailyTrace.getClosingPrice())
                    .gBuyAmount(sevenBreadDailyTrace.getGBuyAmount())
                    .highestPrice(sevenBreadDailyTrace.getHighestPrice())
                    .isReoccur(sevenBreadDailyTrace.getIsReoccur())
                    .itemCode(sevenBreadDailyTrace.getItemCode())
                    .lowestPrice(sevenBreadDailyTrace.getLowestPrice())
                    .pBuyAmount(sevenBreadDailyTrace.getPBuyAmount())
                    .wBuyAmount(sevenBreadDailyTrace.getWBuyAmount())
                    .tradingState(sevenBreadItemDeleteRequestDto.getType())
                    .winDate(sevenBreadItemDeleteRequestDto.getDeletedDate())
                    .build();

            sevenBreadWinRepository.save(sevenBreadWin);
            sevenBreadDailyTraceRepository.delete(sevenBreadDailyTrace);
        }

        sevenBreadV2Repository.delete(sevenBread);

        return sevenBread.getId();
    }

    private void produceKafkaMessage(SevenBreadItemCreationRequestDto sevenBreadItemCreationRequestDto) {
        try {
            kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(sevenBreadItemCreationRequestDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
