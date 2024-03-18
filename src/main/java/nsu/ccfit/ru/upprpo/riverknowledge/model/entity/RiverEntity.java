package nsu.ccfit.ru.upprpo.riverknowledge.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class RiverEntity {
    URI riverLink;
    String label;
    Integer length;
    Integer watershedArea;
    URI image;
    Set<String> origin;
    Set<String> administrativeTerritorial;
    Set<String> tributaries;
    Set<String> mouth;
    String country;

    public RiverEntity() {
        this.origin = new HashSet<>();
        this.administrativeTerritorial = new HashSet<>();
        this.tributaries = new HashSet<>();
        this.mouth = new HashSet<>();
    }
}
