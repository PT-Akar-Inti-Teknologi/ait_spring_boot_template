package org.ait.project.guideline.example.modules.user.transform;

import org.ait.project.guideline.example.modules.permission.dto.response.LoginRes;
import org.ait.project.guideline.example.modules.role.transform.RoleTransform;
import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;
import org.ait.project.guideline.example.shared.feignclient.reqres.dto.response.DataItem;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoleTransform.class)
public interface UserTransform {

    @Named("createLoginResponse")
    LoginRes createLoginResponse(String accessToken, String refreshToken, String tokenType, Long expiresIn);

    @Named("createUserResponse")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "createRoleResList")
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
