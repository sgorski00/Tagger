package pl.sgorski.Tagger.mapper;

import org.mapstruct.Mapper;
import pl.sgorski.Tagger.dto.ProfileResponse;
import pl.sgorski.Tagger.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  ProfileResponse toResponse(User user);
}
