package cloud.stock.link.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkUpdateRequestDto {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String linkValue;

}
