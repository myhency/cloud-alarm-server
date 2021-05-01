package stock.alarm.cloud.metadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "stock-item", description = "종목 API")
@RestController
@RequestMapping(value = "/api/v1/platform", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
public class StockItemsController {

    private final StockItemRepository stockItemRepository;
    private final ModelMapper modelMapper;

    public StockItemsController(StockItemRepository stockItemRepository, ModelMapper modelMapper) {
        this.stockItemRepository = stockItemRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/metadata/stockItem")
    @Operation(summary = "종목추가", description = "종목명, 종목코드, 테마를 추가합니다.")
    public ResponseEntity createStockItem(@RequestBody @Valid StockItemCreateRequest stockItemCreateRequest, BindingResult bindingResult) {
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(value = "/metadata/stockItem/{filterString}")
    @Operation(summary = "종목수정", description = "특정 종목의 종목명 또는 종목코드 또는 테마를 수정합니다.")
    public ResponseEntity modifyStockItem(@PathVariable String filterString, @RequestBody @Valid StockItemCreateRequest stockItemCreateRequest, BindingResult bindingResult) {
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(value = "/metadata/stockItem/{filterString}")
    @Operation(summary = "종목삭제", description = "특정 종목을 삭제합니다.")
    public ResponseEntity removeStockItem(@PathVariable String filterString, @RequestBody @Valid StockItemCreateRequest stockItemCreateRequest, BindingResult bindingResult) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/metadata/stockItem/{filterString}")
    @Operation(summary = "종목조회", description = "특정 종목의 정보를 조회합니다.")
    public ResponseEntity selectStockItem(@PathVariable String filterString) {
        return ResponseEntity.ok().body(null);
    }
}
