package nsu.ccfit.ru.upprpo.riverknowledge.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiverEntity {
    @Schema(description = "Ссылка на объект из wikidata")
    private URI riverLink;

    @Schema(description = "Название реки")
    private String label;

    @Schema(description = "Длина реки")
    private Integer length;

    @Schema(description = "Площадь бассейна")
    private Integer watershedArea;

    @Schema(description = "Изображение реки")
    private URI image;

    @Schema(description = "Основные источники реки")
    private Set<String> origin = new HashSet<>();

    @Schema(description = "Административно-территориальные единицы на территории которых расположен данный элемент")
    private Set<String> administrativeTerritorial = new HashSet<>();

    @Schema(description = "Притоки")
    private Set<String> tributaries = new HashSet<>();

    @Schema(description = "Гидрологические объекты, в который впадает река")
    private Set<String> mouth = new HashSet<>();

    @Schema(description = "Страна")
    private String country;

}
