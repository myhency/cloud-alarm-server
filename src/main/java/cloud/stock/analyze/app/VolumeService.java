package cloud.stock.analyze.app;

import cloud.stock.analyze.domain.volume.Volume;
import cloud.stock.analyze.infra.VolumeRepository;
import cloud.stock.analyze.ui.dataholder.VolumeDataHolder;
import cloud.stock.common.EmptyResultDataAccessException;
import cloud.stock.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class VolumeService {
    private final VolumeRepository volumeRepository;

    @Transactional
    public VolumeDataHolder create(final String itemName,
                                   final String itemCode,
                                   final Integer closingPrice,
                                   final Double fluctuationRate,
                                   final Integer volume,
                                   final Integer numberOfOutstandingShares,
                                   final Integer marketCap,
                                   final String marketType
    ) {
        Volume newVolume = Volume.createVolumeCreationRequest(
                itemName,
                itemCode,
                closingPrice,
                fluctuationRate,
                volume,
                numberOfOutstandingShares,
                marketCap,
                marketType
        );

        newVolume = volumeRepository.save(newVolume);

        return VolumeDataHolder.builder()
                .id(newVolume.getId())
                .itemName(newVolume.getItemName())
                .itemCode(newVolume.getItemCode())
                .closingPrice(newVolume.getClosingPrice())
                .fluctuationRate(newVolume.getFluctuationRate())
                .volume(newVolume.getVolume())
                .numberOfOutstandingShares(newVolume.getNumberOfOutstandingShares())
                .marketCap(newVolume.getMarketCap())
                .marketType(newVolume.getMarketType())
                .build();
    }

    public List<String> dateList() {
        return (List<String>)volumeRepository.findAllDates();
    }

    public List<Volume> detailList(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime from = LocalDate.parse(date, formatter).atStartOfDay();
        LocalDateTime to = from.plusDays(1);
        return volumeRepository.findAllByCreatedDateBetween(from, to);
    }

    public List<Volume> getAnalyzeVolueByItemName(String filter) {
        return volumeRepository.findAllByItemName(filter);
    }

    public List<Volume> getAnalyzeVolueByTheme(String filter) {
        return volumeRepository.findAllByTheme(filter);
    }

    public List<Volume> getAnalzeVolumeByCategoryName(String dateStr, String categoryName) {
        List<Volume> result = volumeRepository.findAllByCategoryName(dateStr, categoryName);
        if (result.size() == 0) {
            throw new EmptyResultDataAccessException(ErrorCode.EMPTY_RESULT);
        }

        return result;
    }
}
