package org.ait.project.guideline.example.modules.user.service.adapter.query;


import org.ait.project.guideline.example.modules.masterdata.dto.param.UserParam;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryAdapter {

  Page<User> getPage(Pageable pageable, UserParam userParam);


}
