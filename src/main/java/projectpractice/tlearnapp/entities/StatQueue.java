package projectpractice.tlearnapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import projectpractice.tlearnapp.enums.StatQueueState;
import projectpractice.tlearnapp.enums.StatQueueStatus;

import java.io.Serializable;

@Entity
@Table(name = "stat_queue")
@Getter
public class StatQueue extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @Column(nullable = false)
    private StatQueueState state;

    @Column(nullable = false)
    private StatQueueStatus status;

    @Column
    private String error;
}
