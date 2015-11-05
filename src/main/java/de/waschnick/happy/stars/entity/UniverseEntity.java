package de.waschnick.happy.stars.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Table(name = "universe")
public class UniverseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Size(min = 1, max = 255)
    private long maxSize;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="customer")
    public Set<StarEntity> stars;

    public UniverseEntity() {
        // default Constructor
    }

    public UniverseEntity(long id) {
        this.id = id;
    }

}
