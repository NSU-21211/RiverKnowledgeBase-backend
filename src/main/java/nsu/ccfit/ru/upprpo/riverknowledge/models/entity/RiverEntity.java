package nsu.ccfit.ru.upprpo.riverknowledge.models.entity;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class RiverEntity {
    URI river;
    String label;
    String coordinateLocation;
    Integer length;
    URI tributary;
    URI image;
}
