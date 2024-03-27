package org.ait.project.guideline.example.modules.user.service.adapter.query;

import java.util.List;

import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.User;

public interface UserQueryAdapter {

    List<User> findUser(String searchBy, String sortBy, String sortField, Integer pageNumber, Integer pageSize, UserReq userReq);


}
