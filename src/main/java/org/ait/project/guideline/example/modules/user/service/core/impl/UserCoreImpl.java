package org.ait.project.guideline.example.modules.user.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.role.service.adapter.query.RoleQueryAdapter;
import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.model.entity.UserAit;
import org.ait.project.guideline.example.modules.user.service.adapter.command.UserCommandAdapter;
import org.ait.project.guideline.example.modules.user.service.adapter.query.UserQueryAdapter;
import org.ait.project.guideline.example.modules.user.service.core.UserCore;
import org.ait.project.guideline.example.modules.user.transform.UserTransform;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.feignclient.reqres.response.ReqresListUserResponse;
import org.ait.project.guideline.example.shared.feignclient.reqres.service.ReqresService;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCoreImpl implements UserCore {

    private final UserCommandAdapter userCommandAdapter;

    private final UserQueryAdapter userQueryAdapter;

    private final ReqresService reqresService;

    private final UserTransform userTransform;

    private final RoleQueryAdapter roleQueryAdapter;

    private final ResponseHelper responseHelper;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> addUser(UserReq userReq) {
        UserAit userAit = userTransform.mappingUserAitFromUserReq(userReq);
        userCommandAdapter.addUserRoles(userAit, userReq.getRolesId());
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
                userTransform.createUserResponse(userCommandAdapter.saveUser(userAit)));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers() {
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS, Page.empty(),
                userTransform.createUserResponseList(userQueryAdapter.findAllUser()));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> fetchUser() {
        ReqresListUserResponse reqresResponse = reqresService.getListUser("1");
        reqresResponse.getData().forEach(data ->
                userCommandAdapter.saveUser(userTransform.mappingUserAitFromReqres(data)));
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, "success");
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> updateUser(Integer id, UserReq userReq) {
        UserAit userAit = userTransform.mappingUserAitFromUserReq(userReq);
        userAit.setId(id);
        userCommandAdapter.addUserRoles(userAit, userReq.getRolesId());
        UserAit updatedUser = userCommandAdapter.updateUser(userAit);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, userTransform.createUserResponse(updatedUser));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> addBalance(Integer id, UserReq userReq) {
        UserAit addBalance = userCommandAdapter.addBalance(id, userReq.getAmount());
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, userTransform.createUserResponse(addBalance));
    }

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> deleteUser(Integer id) {
        userCommandAdapter.deleteUser(id);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS, "Success");
    }
}
