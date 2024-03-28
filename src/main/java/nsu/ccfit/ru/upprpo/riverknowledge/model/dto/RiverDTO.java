package nsu.ccfit.ru.upprpo.riverknowledge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RiverDTO {
    @Schema(description = "Название реки")
    private String label;

    @Schema(description = "Административно-территориальные единицы на территории которых расположен данный элемент.")
    private Set<String> administrativeTerritorial;

    @Schema(description = "Длина реки")
    private Integer length;
}
