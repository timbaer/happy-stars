package de.waschnick.happy.stars.entity;

import de.waschnick.happy.stars.api.StarColor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "star")
public class StarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // hibernate_sequence wasnt created
    // HINT Needed by Postgresql: @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="judgements_id_seq")
//    @SequenceGenerator(name="judgements_id_seq", sequenceName="judgements_id_seq", allocationSize=1)
    private long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StarColor color;

    public StarEntity() {
        // default Constructor
    }

    public StarEntity(long id) {
        this.id = id;
    }

}
