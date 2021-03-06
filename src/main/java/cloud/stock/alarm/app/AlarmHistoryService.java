package cloud.stock.alarm.app;

import cloud.stock.alarm.domain.alarm.Alarm;
import cloud.stock.alarm.domain.alarmHistory.AlarmHistory;
import cloud.stock.alarm.domain.exceptions.NotExistAlarmException;
import cloud.stock.alarm.domain.strategy.AlarmStatus;
import cloud.stock.alarm.infra.AlarmHistoryRepository;
import cloud.stock.alarm.ui.dataholder.AlarmDataHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class AlarmHistoryService {

    private final AlarmHistoryRepository alarmHistoryRepository;

    @Transactional
    public void saveAlarmHistory(AlarmDataHolder alarmDataHolder) {
        AlarmHistory newAlarmHistory = new AlarmHistory(
                alarmDataHolder.getAlarmId(),
                alarmDataHolder.getItemName(),
                alarmDataHolder.getItemCode(),
                alarmDataHolder.getRecommendPrice(),
                alarmDataHolder.getLosscutPrice(),
                alarmDataHolder.getComment(),
                alarmDataHolder.getTheme(),
                AlarmStatus.valueOf(alarmDataHolder.getAlarmStatus()),
                alarmDataHolder.getCreatedDate(),
                LocalDateTime.now()
        );

        alarmHistoryRepository.save(newAlarmHistory);
    }

    public List<AlarmHistory> getAlarmsByStatus(String alarmStatus) {
        return alarmHistoryRepository
                .findAllByAlarmStatusOrderByModifiedDateDesc(
                        AlarmStatus.valueOf(alarmStatus)
                );
    }

    public AlarmDataHolder getAlarmDetail(Long alarmId) {
        AlarmHistory alarmHistoryDetail = alarmHistoryRepository
                .findByAlarmIdAndAlarmStatus(alarmId, AlarmStatus.LOSSCUT)
                .orElseThrow(NotExistAlarmException::new);

        return AlarmDataHolder.builder()
                .alarmId(alarmHistoryDetail.getAlarmId())
                .itemName(alarmHistoryDetail.getItemName())
                .itemCode(alarmHistoryDetail.getItemCode())
                .recommendPrice(alarmHistoryDetail.getRecommendPrice())
                .losscutPrice(alarmHistoryDetail.getLosscutPrice())
                .alarmStatus(alarmHistoryDetail.getAlarmStatus().name())
                .comment(alarmHistoryDetail.getComment())
                .theme(alarmHistoryDetail.getTheme())
                .createdDate(alarmHistoryDetail.getCreatedDate())
                .modifiedDate(alarmHistoryDetail.getModifiedDate())
                .build();
    }
}
