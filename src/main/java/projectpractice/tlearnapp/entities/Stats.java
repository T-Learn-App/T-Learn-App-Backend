package projectpractice.tlearnapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectpractice.tlearnapp.enums.StatsStatus;

import java.io.Serializable;

@Entity
@Table(name = "stats")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stats extends AuditableBaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word_id;

    @Column(nullable = false)
    private Long attempts;

    @Column(nullable = false)
    private StatsStatus status;
}
