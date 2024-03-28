package nsu.ccfit.ru.upprpo.riverknowledge.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class RiverPairKey {
    private URI link;
    private String label;
}
