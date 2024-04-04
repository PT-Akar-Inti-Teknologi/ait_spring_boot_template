package org.ait.project.guideline.example.modules.user.transform;

import org.ait.project.guideline.example.modules.permission.dto.response.LoginRes;
import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;
import org.ait.project.guideline.example.shared.feignclient.reqres.dto.response.DataItem;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTransform {

    @Named("createLoginResponse")
    @Mapping(target = "id", source = "userAit.id")
    @Mapping(target = "name", source = "userAit.name")
    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    LoginRes createLoginResponse(UserAit userAit, String accessToken, String refreshToken, String tokenType, Long expiresIn);

    @Named("createUserResponse")
    UserRes createUserResponse(UserAit userAit);

    @IterableMapping(qualifiedByName = "createUserResponse")
    @Named("createUserResponseList")
    List<UserRes> createUserResponseList(List<UserAit> userAits);

    @Named("mappingUserAit")
    UserAit mappingUserAitFromUserReq(UserReq userReq);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", expression = "java(dataItem.getFirstName() + ' ' +dataItem.getLastName())")
    UserAit mappingUserAitFromReqres(DataItem dataItem);

}
