package cloud.stock.alarm.ui;

import cloud.stock.alarm.app.AlarmService;
import cloud.stock.common.CustomException;
import cloud.stock.common.EmptyResultDataAccessException;
import cloud.stock.common.ErrorCode;
import cloud.stock.common.InvalidParameterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.support.ScopeNotActiveException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import cloud.stock.alarm.domain.Alarm;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Tag(name = "alarm", description = "알람 API")
@RestController
@RequestMapping(value = "/api/v1/platform", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
public class AlarmRestController {
    private final AlarmService alarmService;

    public AlarmRestController(final AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PostMapping(value = "/alarm/stockItem")
    @Operation(summary = "알람등록", description = "특정 종목의 알람설정을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "알람등록 성공",
                    content = @Content(schema = @Schema(implementation = Alarm.class))),
            @ApiResponse(responseCode = "400", description = "알람등록 실패",
                    content = @Content(schema = @Schema(implementation = InvalidParameterException.class)))
    })
    public ResponseEntity<Alarm> createAlarm(@RequestBody @Valid final Alarm alarm) {
        final Alarm created = alarmService.create(alarm);
        final URI uri = URI.create("/alarm/stockItem/"+created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/alarm/stockItem/{id}")
    @Operation(summary = "알람수정", description = "특정 종목의 알람설정을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알람수정 성공",
                    content = @Content(schema = @Schema(implementation = Alarm.class))),
            @ApiResponse(responseCode = "204", description = "알람수정 실패",
                    content = @Content(schema = @Schema(implementation = EmptyResultDataAccessException.class)))
    })
    public ResponseEntity<Alarm> updateAlarm(@PathVariable final Long id, @RequestBody final Alarm alarm) {
        return ResponseEntity.ok(alarmService.changeAlarm(id, alarm));
    }

    @GetMapping(value = "/alarm/stockItem")
    @Operation(summary = "전체알람조회", description = "전체 알람을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체알람조회 성공",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "전체알람조회 실패",
                    content = @Content(schema = @Schema(implementation = InvalidParameterException.class)))
    })
    public ResponseEntity<List<Alarm>> selectAlarms() {
        return ResponseEntity.ok().body(alarmService.list());
    }

    @GetMapping(value = "/alarm/stockItem/{id}")
    @Operation(summary = "알람상세조회", description = "특정 알람의 상세내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알람상세내용 조회 성공",
                    content = @Content(schema = @Schema(implementation = Alarm.class))),
            @ApiResponse(responseCode = "400", description = "알람상세내용 조회 실패",
                    content = @Content(schema = @Schema(implementation = InvalidParameterException.class)))
    })
    public ResponseEntity<Alarm> selectAlarm(@PathVariable Long id) {
        return ResponseEntity.ok().body(alarmService.getAlarmDetail(id));
    }

    @GetMapping(value = "/alarm/stockItem/filter")
    @Operation(summary = "알람상세조회", description = "종목명 또는 종목코드로 특정 알람의 상세내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알람상세내용 조회 성공",
                    content = @Content(schema = @Schema(implementation = Alarm.class))),
            @ApiResponse(responseCode = "204", description = "알람상세내용 조회 실패",
                    content = @Content(schema = @Schema(implementation = EmptyResultDataAccessException.class)))
    })
    public ResponseEntity<Alarm> selectAlarmByFilter(
            @RequestParam(value = "itemCode") String itemCode,
            @RequestParam(value = "itemName", required = false) String itemName
    ) {
        try {
            return ResponseEntity.ok().body(alarmService.getAlarmDetailByFilter(itemCode,itemName));
        } catch (Exception e) {
            throw new EmptyResultDataAccessException(ErrorCode.EMPTY_RESULT);
        }
    }

    @GetMapping(value = "/alarm/analyzedItem/{filterString}")
    @Operation(summary = "알람조회", description = "특정 종목의 알람설정을 조회합니다.")
    public ResponseEntity selectAlarm(@PathVariable String filterString) {
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(value = "/alarm/analyzedItem/{filterString}")
    @Operation(summary = "알람삭제", description = "특정 종목의 알람설정을 삭제합니다.")
    public ResponseEntity deleteAlarm(@PathVariable String filterString) {
        return ResponseEntity.ok().body(null);
    }
}
