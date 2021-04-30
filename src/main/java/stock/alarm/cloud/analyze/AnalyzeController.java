package stock.alarm.cloud.analyze;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import stock.alarm.cloud.common.InvalidParameterException;

import javax.validation.Valid;


@Tag(name = "alarm", description = "알람 API")
@RestController
@RequestMapping(value = "/api/v1/platform", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
public class AnalyzeController {

    private final AnalyzedItemRepository analyzedItemRepository;
    private final ModelMapper modelMapper;

    public AnalyzeController(AnalyzedItemRepository analyzedItemRepository, ModelMapper modelMapper) {
        this.analyzedItemRepository = analyzedItemRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/alarm/analyzedItem")
    @Operation(summary = "알람등록", description = "특정 종목의 알람설정을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알람등록 성공",
                    content = @Content(schema = @Schema(implementation = AnalyzedItemSaveResponse.class))),
            @ApiResponse(responseCode = "400", description = "알람등록 실패",
                    content = @Content(schema = @Schema(implementation = InvalidParameterException.class)))
    })
    public ResponseEntity createAlarm(@RequestBody @Valid AnalyzedItemSaveRequest analyzedItemSaveRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidParameterException(bindingResult);
        }

        //TODO. custom validator 로 alarmDto 검사하기

        AnalyzedItem analyzedItem = modelMapper.map(analyzedItemSaveRequest, AnalyzedItem.class);
        AnalyzedItem newAnalyzedItem = this.analyzedItemRepository.save(analyzedItem);

        AnalyzedItemSaveResponse analyzedItemSaveResponse = new AnalyzedItemSaveResponse(
                true,
                "OK",
                newAnalyzedItem.getId(),
                newAnalyzedItem.getItemName(),
                newAnalyzedItem.getItemCode()
        );

        return ResponseEntity.ok().body(analyzedItemSaveResponse);
    }

    @PutMapping(value = "/alarm/analyzedItem")
    public ResponseEntity updateAlarm(@RequestBody @Valid AnalyzedItemSaveRequest analyzedItemSaveRequest, BindingResult bindingResult) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/alarm/analyzedItem/{filterString}")
    public ResponseEntity selectAlarm(@PathVariable String filterString) {
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(value = "/alarm/analyzedItem/{filterString}")
    public ResponseEntity deleteAlarm(@PathVariable String filterString) {
        return ResponseEntity.ok().body(null);
    }
}
