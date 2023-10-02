package org.ait.project.guideline.example.modules.role.service.adapter.query;

import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleQueryAdapter {

    List<Role> findByInIds(List<Integer> roleId);

    Page<Role> getRoleByStartDateAndEndDate(String startDate, String endDate, Pageable pageable);

    Role findById(Integer id);
}
