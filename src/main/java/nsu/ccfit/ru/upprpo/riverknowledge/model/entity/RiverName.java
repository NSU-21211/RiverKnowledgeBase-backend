package nsu.ccfit.ru.upprpo.riverknowledge.model.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Deprecated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiverName {
    @JsonAlias(value = {"river", "name", "riverName"})
    private String name;
}
