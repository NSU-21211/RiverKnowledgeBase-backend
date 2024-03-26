package nsu.ccfit.ru.upprpo.riverknowledge.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Ссылка на объект из wikidata")
    URI riverLink;

    @Schema(description = "Название реки")
    String label;

    @Schema(description = "Длина реки")
    Integer length;

    @Schema(description = "Площадь бассейна")
    Integer watershedArea;

    @Schema(description = "Изображение реки")
    URI image;

    @Schema(description = "Основные источники реки")
    Set<String> origin;

    @Schema(description = "Административно-территориальные единицы на территории которых расположен данный элемент")
    Set<String> administrativeTerritorial;

    @Schema(description = "Притоки")
    Set<String> tributaries;

    @Schema(description = "Гидрологические объекты, в который впадает река")
    Set<String> mouth;

    @Schema(description = "Страна")
    String country;

    public RiverEntity() {
        this.origin = new HashSet<>();
        this.administrativeTerritorial = new HashSet<>();
        this.tributaries = new HashSet<>();
        this.mouth = new HashSet<>();
    }
}
