package pl.sgorski.Tagger.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Tag {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_description_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private ItemDescription itemDescription;

    @Column(nullable = false)
    @ToString.Include
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
