package pl.sgorski.Tagger.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.model.Tag;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ItemDescriptionMapper {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "stringsToTags")
    ItemDescription toDescription(ItemDescriptionResponse response);

    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagsToStrings")
    ItemDescriptionResponse toResponse(ItemDescription itemDescription);

    @Named("stringsToTags")
    static Set<Tag> stringsToTags(String[] tagNames) {
        return Arrays.stream(tagNames)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    @Named("tagsToStrings")
    static String[] tagsToStrings(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .toArray(String[]::new);
    }
}
