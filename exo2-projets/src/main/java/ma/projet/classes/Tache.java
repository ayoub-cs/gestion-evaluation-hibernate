package ma.projet.classes;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeTacheId implements Serializable {
    private Integer employeId;
    private Integer tacheId;

    public EmployeTacheId() {}
    public EmployeTacheId(Integer employeId, Integer tacheId) {
        this.employeId = employeId; this.tacheId = tacheId;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeTacheId that)) return false;
        return Objects.equals(employeId, that.employeId) && Objects.equals(tacheId, that.tacheId);
    }
    @Override public int hashCode() { return Objects.hash(employeId, tacheId); }
}
