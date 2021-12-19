package cloud.stock.v2.sevenbread.ui.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadItemDeleteRequestDto {
    @NotEmpty
    private String itemCode;
    private String deletedDate;
    private String type;
}
