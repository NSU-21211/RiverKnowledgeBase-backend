package nsu.ccfit.ru.upprpo.riverknowledge.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class RiverPairKey {
    private URI link;
    private String label;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiverPairKey key = (RiverPairKey) o;
        return link.equals(key.getLink()) && label.equals(key.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, label);
    }
}
