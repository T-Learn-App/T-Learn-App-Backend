package projectpractice.tlearnapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leader_board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoard extends AuditableBaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_id")
    )
    private User user;

    @Column(nullable = false)
    private Long seasonId;

    @Column(nullable = false)
    private Long totalScore;
}
