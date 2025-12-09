package projectpractice.tlearnapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "refresh_tokens")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken extends AuditableBaseEntity {

    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne
    private User user;

    @Column(name = "expiry_date")
    @CreationTimestamp
    private Date expiryDate;
}
