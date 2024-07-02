package org.ait.project.guideline.example.modules.user.transform;

import java.util.List;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserTransform {

  @Named("createUserResponse")
  UserRes createUserResponse(User user);

  @IterableMapping(qualifiedByName = "createUserResponse")
  @Named("createUserResponseList")
  List<UserRes> createUserResponseList(Page<User> page);


}
