package pl.sgorski.Tagger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.model.User;

public interface ItemDescriptionRepository extends JpaRepository<ItemDescription, Long> {
    Page<ItemDescription> findAllByCreatedByOrderByIdDesc(User user, Pageable pageable);
}
