package cloud.stock.analyze.ui;

import cloud.stock.alarm.ui.dataholder.AlarmDataHolder;
import cloud.stock.analyze.app.VolumeService;
import cloud.stock.analyze.ui.dataholder.VolumeDataHolder;
import cloud.stock.analyze.ui.dto.VolumeBySharesCreationRequestDto;
import cloud.stock.common.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Tag(
        name = "analyze",
        description = "분석 API"
)
@RestController
@RequestMapping(
        value = "/api/v1/platform",
        produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"
)
public class AnalyzeRestController {

    private final VolumeService volumeService;

    public AnalyzeRestController(final VolumeService volumeService) {
        this.volumeService = volumeService;
    }

    @PostMapping(value = "/analyze/volumeByShares")
    @Operation(
            summary = "유통주식수 대비 거래량비율 등록",
            description = "특정 종목의 유통주식수 대비 거래량비율을 등록 합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "유통주식수 대비 거래량비율등록 성공",
                    content = @Content(
                            schema = @Schema(
                                    implementation = VolumeDataHolder.class
                            )
                    )
            ),
    })
    public ResponseEntity createVolumeByShares(
            @RequestBody VolumeBySharesCreationRequestDto volumeBySharesCreationRequestDto
            ) throws URISyntaxException {
        final VolumeDataHolder createdVolume = volumeService.create(
                volumeBySharesCreationRequestDto.getItemName(),
                volumeBySharesCreationRequestDto.getItemCode(),
                volumeBySharesCreationRequestDto.getClosingPrice(),
                volumeBySharesCreationRequestDto.getFluctuationRate(),
                volumeBySharesCreationRequestDto.getVolume(),
                volumeBySharesCreationRequestDto.getNumberOfOutstandingShares(),
                volumeBySharesCreationRequestDto.getMarketCap(),
                volumeBySharesCreationRequestDto.getMarketType()
        );

        return ResponseEntity.created(
                new URI("/analyze/volumeByShares" + createdVolume.getItemCode()))
                .body(new ResponseDto<>(createdVolume.getId()));

    }

    @GetMapping(value = "/analyze/volume/datelist")
    @Operation(
            summary = "유통주식수 대비 거래량비율 등록 날짜 리스트 조회",
            description = "특정 종목의 유통주식수 대비 거래량비율을 등록한 날짜를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "유통주식수 대비 거래량비율등록 날짜 리스트 조회 성공",
                    content = @Content(
                            schema = @Schema(
                                    implementation = List.class
                            )
                    )
            ),
    })
    public ResponseEntity selectDateListVolumeByShares() {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(volumeService.dateList()));
    }

    @GetMapping(value = "/analyze/volume")
    @Operation(
            summary = "날짜별 유통주식수 대비 거래량비율 리스트 조회",
            description = "특정 날짜의 유통주식수 대비 거래량비율을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "특정 날짜의 유통주식수 대비 거래량비율을 조회 성공",
                    content = @Content(
                            schema = @Schema(
                                    implementation = List.class
                            )
                    )
            ),
    })
    public ResponseEntity selectDetailVolumeByShares(
            @RequestParam(value = "date") String date
    ) {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(volumeService.detailList(date)));
    }

    @GetMapping(value = "/analyze/volume/search")
    @Operation(
            summary = "날짜별 유통주식수 대비 거래량비율 리스트 조회(with search option)",
            description = "특정 날짜의 유통주식수 대비 거래량비율을 조회합니다.\n 종목명 또는 테마의 특정 키워드로 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "날짜별 유통주식수 대비 거래량비율 리스트 조회(with search option) 성공",
                    content = @Content(
                            schema = @Schema(
                                    implementation = List.class
                            )
                    )
            ),
    })
    public ResponseEntity selectAnalyzeVolumeBy(
            @RequestParam(value = "by") String by,
            @RequestParam(value = "filter") String filter
    ) {
        if (by.equals("1"))
            return ResponseEntity.ok()
                .body(new ResponseDto<>(volumeService.getAnalyzeVolueByItemName(filter)));

        return ResponseEntity.ok()
                .body(new ResponseDto<>(volumeService.getAnalyzeVolueByTheme(filter)));
    }

    @GetMapping("/analyze/volume/category")
    public ResponseEntity selectAnalyzedVolumeByCategory(
            @RequestParam(value = "categoryName") String categoryName,
            @RequestParam(value = "dateStr") String dateStr
    ) {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(volumeService.getAnalzeVolumeByCategoryName(dateStr, categoryName)));
    }
}
