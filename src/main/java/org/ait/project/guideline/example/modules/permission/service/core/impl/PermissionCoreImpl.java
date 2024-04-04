package org.ait.project.guideline.example.modules.permission.service.core.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.config.properties.ApplicationProperties;
import org.ait.project.guideline.example.modules.permission.dto.request.LoginReq;
import org.ait.project.guideline.example.modules.permission.dto.response.LoginRes;
import org.ait.project.guideline.example.modules.permission.service.core.PermissionCore;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;
import org.ait.project.guideline.example.modules.user.service.adapter.query.UserQueryAdapter;
import org.ait.project.guideline.example.modules.user.transform.UserTransform;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.constant.enums.TokenTypeEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.exception.InvalidUserPassException;
import org.ait.project.guideline.example.shared.exception.UserNotFoundException;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.ait.project.guideline.example.shared.utils.response.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionCoreImpl implements PermissionCore {

    private final UserQueryAdapter userQueryAdapter;
    private final UserTransform userTransform;
    private final ResponseHelper responseHelper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ApplicationProperties properties;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseDetail<LoginRes>>> login(LoginReq request) {
        UserAit user = validateUser(request.getUsername());
        validatePassword(user, request.getPassword());
        String accessToken = jwtUtils.createAccessToken(user.getId(), null, null, null);
        String refreshToken = jwtUtils.createRefreshToken(user.getId(), null, null);
        return responseHelper.createResponseDetail(ResponseEnum.SUCCESS,
                userTransform.createLoginResponse(
                        user, accessToken, refreshToken, TokenTypeEnum.ACCESS_TOKEN.name(),
                        properties.getJwtProperties().getAccessTokenExpirationTime()));
    }

    private UserAit validateUser(String username) {
        return userQueryAdapter.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    private void validatePassword(UserAit user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidUserPassException();
        }
    }
}
