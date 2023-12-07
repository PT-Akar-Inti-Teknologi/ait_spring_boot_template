package org.ait.project.guideline.example.modules.role.service.adapter.query.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.role.exception.RoleNotFoundException;
import org.ait.project.guideline.example.modules.role.model.entity.Role;
import org.ait.project.guideline.example.modules.role.model.repository.RoleRepository;
import org.ait.project.guideline.example.modules.role.service.adapter.query.RoleQueryAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleQueryAdapterImpl implements RoleQueryAdapter {

    private final RoleRepository repository;

    @Override
    public List<Role> findByInIds(List<Integer> roleId) {
        return repository.findAllById(roleId);
    }

    @Override
    public Page<Role> getRoleByStartDateAndEndDate(String startDate, String endDate, Pageable pageable) {
        LocalDateTime startDateLocal = LocalDateTime.parse(startDate);
        LocalDateTime endDateLocal = LocalDateTime.parse(endDate);
        return repository.findByCreatedAtBetween(startDateLocal, endDateLocal, pageable);
    }

    @Override
    public Role findById(Integer id) {
        return repository.findById(id).orElseThrow(RoleNotFoundException::new);
    }
}
