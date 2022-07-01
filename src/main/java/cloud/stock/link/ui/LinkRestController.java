package cloud.stock.link.ui;

import cloud.stock.common.ResponseDto;
import cloud.stock.link.app.LinkService;
import cloud.stock.link.ui.dto.LinkUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(
        value = "/api/v1/platform/admin",
        produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"
)
public class LinkRestController {
    private final LinkService linkService;

    public LinkRestController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping(value = "/link/{linkName}")
    public ResponseEntity selectTelegramLink(@PathVariable String linkName) {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(linkService.getLink(linkName)));
    }

    @GetMapping(value = "/link/all")
    public ResponseEntity selectAllLinks() {
        return ResponseEntity.ok().body(new ResponseDto<>(linkService.getLinks()));
    }

    @PutMapping(value = "/link")
    public ResponseEntity updateTelegramLink(final @RequestBody LinkUpdateRequestDto linkUpdateRequestDto) {
        return ResponseEntity.ok().body(new ResponseDto<>(linkService.updateLink(linkUpdateRequestDto.getId(), linkUpdateRequestDto.getLinkValue())));
    }
}
