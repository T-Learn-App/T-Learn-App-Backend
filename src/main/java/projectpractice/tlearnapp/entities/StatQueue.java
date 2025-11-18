package projectpractice.tlearnapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import projectpractice.tlearnapp.enums.StatQueueStatus;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "stat_queue")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatQueue extends AuditableBaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @Column(nullable = false)
    private StatQueueStatus status;

    @Column
    private String error;
}


