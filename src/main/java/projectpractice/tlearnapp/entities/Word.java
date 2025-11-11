package projectpractice.tlearnapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "words")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Word extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String transcription;

    @Column(nullable = false)
    private String translation;

    @Column(nullable = false)
    private String partOfSpeech;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
