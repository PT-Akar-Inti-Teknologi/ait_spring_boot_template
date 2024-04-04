package org.ait.project.guideline.example.modules.user.service.core.impl;

import lombok.RequiredArgsConstructor;


import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.service.adapter.query.UserQueryAdapter;
import org.ait.project.guideline.example.modules.user.service.core.UserCore;
import org.ait.project.guideline.example.modules.user.transform.UserTransform;
import org.ait.project.guideline.example.shared.constant.enums.ResponseEnum;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.utils.response.ResponseHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCoreImpl implements UserCore {


    private final ResponseHelper responseHelper;
    
    private final UserTransform userTransform;
    
    private final UserQueryAdapter userQueryAdapter;

    @Override
    public ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers(String searchBy, String sortBy, Integer pageNumber, Integer pageSize, UserReq userReq) {
    	Sort sort = Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return responseHelper.createResponseCollection(ResponseEnum.SUCCESS,Page.empty(pageable),
                userTransform.createUserResponseList( userQueryAdapter.findUser(searchBy,  sortBy,  pageNumber,  pageSize, userReq)));
    }

}
