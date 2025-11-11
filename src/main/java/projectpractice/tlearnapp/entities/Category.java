package projectpractice.tlearnapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectpractice.tlearnapp.enums.WordsCategory;

import java.io.Serializable;

@Entity
@Table(name = "categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private WordsCategory name;
}
