package de.waschnick.happy.stars.business.universe.entity;

import de.waschnick.happy.stars.business.star.entity.StarEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = "stars")
@Entity
@Table(name = "universe")
public class UniverseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min = 1, max = 255)
    @NotNull
    private String name;

    @Min(1)
    @Max(255)
    @NotNull
    @Column(name = "max_size")
    private long maxSize;

    //    @OneToMany(cascade= CascadeType.ALL, mappedBy="customer")
    @OneToMany(mappedBy = "universe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<StarEntity> stars = new HashSet<StarEntity>();

    public UniverseEntity() {
        // default Constructor
    }

    public UniverseEntity(long id) {
        this.id = id;
    }
}
