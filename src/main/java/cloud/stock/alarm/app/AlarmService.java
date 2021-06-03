package cloud.stock.alarm.app;

import cloud.stock.alarm.domain.alarm.Alarm;
import cloud.stock.alarm.infra.AlarmRepository;
import cloud.stock.alarm.domain.exceptions.AlreadyExistAlarmException;
import cloud.stock.alarm.domain.exceptions.InvalidAlarmModificationDataException;
import cloud.stock.alarm.domain.exceptions.NotExistAlarmException;
import cloud.stock.alarm.domain.strategy.AlarmStatus;
import cloud.stock.alarm.ui.dataholder.AlarmDataHolder;
import cloud.stock.stockitem.domain.StockItemRepository;
import cloud.stock.stockitem.domain.exceptions.NotExistStockItemException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final StockItemRepository stockItemRepository;

    @Transactional
    public AlarmDataHolder create(final String itemName,
                                  final String itemCode,
                                  final Integer recommendPrice,
                                  final Integer losscutPrice,
                                  final String comment,
                                  final String theme
    ) {
            stockItemRepository.findByItemCode(itemCode).orElseThrow(NotExistStockItemException::new);
            List<Alarm> alarm = alarmRepository.findByItemCode(itemCode);
            if(!alarm.isEmpty()) {
                throw new AlreadyExistAlarmException();
            }

            Alarm newAlarm = Alarm.createAlarmCreationRequest(
                    itemName,
                    itemCode,
                    recommendPrice,
                    losscutPrice,
                    comment,
                    theme
            );

            newAlarm = alarmRepository.save(newAlarm);

            return AlarmDataHolder.builder()
                    .alarmId(newAlarm.getAlarmId())
                    .itemName(newAlarm.getItemName())
                    .itemCode(newAlarm.getItemCode())
                    .recommendPrice(newAlarm.getRecommendPrice())
                    .losscutPrice(newAlarm.getLosscutPrice())
                    .comment(newAlarm.getComment())
                    .theme(newAlarm.getTheme())
                    .alarmStatus(newAlarm.getAlarmStatus().name())
                    .createdDate(newAlarm.getCreatedDate())
                    .build();
    }

    @Transactional
    public AlarmDataHolder changeAlarm(final Long alarmId,
                                       final String itemName,
                                       final String itemCode,
                                       final Integer recommendPrice,
                                       final Integer losscutPrice,
                                       final String comment,
                                       final String theme
    ) {
        //돌파가격은 0보다 작을 수 없다.
        if(recommendPrice < 0) {
            throw new InvalidAlarmModificationDataException();
        }

        //손절가격은 0보다 작을 수 없다.
        if(losscutPrice < 0) {
            throw new InvalidAlarmModificationDataException();
        }

        Alarm toBeModifiedAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(NotExistAlarmException::new);

        //종목명과 종목코드는 수정할 수 없다.
        if(toBeModifiedAlarm.getItemName() == itemName || toBeModifiedAlarm.getItemCode() == itemCode) {
            throw new InvalidAlarmModificationDataException();
        }

        toBeModifiedAlarm.setRecommendPrice(recommendPrice);
        toBeModifiedAlarm.setLosscutPrice(losscutPrice);
        toBeModifiedAlarm.setComment(comment);
        toBeModifiedAlarm.setTheme(theme);

        toBeModifiedAlarm = alarmRepository.save(toBeModifiedAlarm);

        return AlarmDataHolder.builder()
                .alarmId(toBeModifiedAlarm.getAlarmId())
                .itemName(toBeModifiedAlarm.getItemName())
                .itemCode(toBeModifiedAlarm.getItemCode())
                .recommendPrice(toBeModifiedAlarm.getRecommendPrice())
                .losscutPrice(toBeModifiedAlarm.getLosscutPrice())
                .comment(toBeModifiedAlarm.getComment())
                .theme(toBeModifiedAlarm.getTheme())
                .modifiedDate(toBeModifiedAlarm.getModifiedDate())
                .build();
    }

    public List<Alarm> list() {
        //TODO. 페이징처리 해야함.
        return alarmRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedDate"));
    }

    public AlarmDataHolder getAlarmDetail(final Long alarmId) {
        Alarm alarmDetail = alarmRepository.findById(alarmId)
                .orElseThrow(NotExistAlarmException::new);

        return AlarmDataHolder.builder()
                .alarmId(alarmDetail.getAlarmId())
                .itemName(alarmDetail.getItemName())
                .itemCode(alarmDetail.getItemCode())
                .recommendPrice(alarmDetail.getRecommendPrice())
                .losscutPrice(alarmDetail.getLosscutPrice())
                .alarmStatus(alarmDetail.getAlarmStatus().name())
                .comment(alarmDetail.getComment())
                .theme(alarmDetail.getTheme())
                .createdDate(alarmDetail.getCreatedDate())
                .modifiedDate(alarmDetail.getModifiedDate())
                .build();
    }

    public Alarm getAlarmDetailByFilter(String itemCode, String itemName) {
        return alarmRepository.findOneByItemCode(itemCode);
    }

    public AlarmDataHolder updateBuyAlarm(Long alarmId) {
        Alarm toBeDeletedAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(NotExistAlarmException::new);

//        toBeDeletedAlarm.setAlarmStatus(AlarmStatus.ALARMED);

        alarmRepository.delete(toBeDeletedAlarm);

        return AlarmDataHolder.builder()
                .alarmId(toBeDeletedAlarm.getAlarmId())
                .itemName(toBeDeletedAlarm.getItemName())
                .itemCode(toBeDeletedAlarm.getItemCode())
                .recommendPrice(toBeDeletedAlarm.getRecommendPrice())
                .losscutPrice(toBeDeletedAlarm.getLosscutPrice())
                .alarmStatus(toBeDeletedAlarm.getAlarmStatus().toString())
                .comment(toBeDeletedAlarm.getComment())
                .theme(toBeDeletedAlarm.getTheme())
                .createdDate(toBeDeletedAlarm.getCreatedDate())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    public AlarmDataHolder updateLosscutAlarm(Long alarmId) {
        Alarm toBeDeletedAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(NotExistAlarmException::new);

        toBeDeletedAlarm.setAlarmStatus(AlarmStatus.LOSSCUT);

        alarmRepository.delete(toBeDeletedAlarm);

        return AlarmDataHolder.builder()
                .alarmId(toBeDeletedAlarm.getAlarmId())
                .itemName(toBeDeletedAlarm.getItemName())
                .itemCode(toBeDeletedAlarm.getItemCode())
                .recommendPrice(toBeDeletedAlarm.getRecommendPrice())
                .losscutPrice(toBeDeletedAlarm.getLosscutPrice())
                .alarmStatus(toBeDeletedAlarm.getAlarmStatus().toString())
                .comment(toBeDeletedAlarm.getComment())
                .theme(toBeDeletedAlarm.getTheme())
                .createdDate(toBeDeletedAlarm.getCreatedDate())
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
