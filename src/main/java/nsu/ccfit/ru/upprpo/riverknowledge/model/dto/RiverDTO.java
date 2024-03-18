package nsu.ccfit.ru.upprpo.riverknowledge.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RiverDTO {
    String label;
    Set<String> administrativeTerritorial;
    Integer length;
}
