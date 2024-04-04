package org.ait.project.guideline.example.modules.user.service.adapter.query.impl;

import java.util.List;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.modules.role.model.jpa.entity.Role;
import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.User;
import org.ait.project.guideline.example.modules.user.model.jpa.repository.UserRepository;
import org.ait.project.guideline.example.modules.user.service.adapter.query.UserQueryAdapter;
import org.ait.project.guideline.example.shared.constant.statics.StaticConstant;
import org.ait.project.guideline.example.utils.specification.MainSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueryAdapterImpl implements UserQueryAdapter{
	
	private final UserRepository repository;
	private final MainSearch mainSearch;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUser(String searchBy, String sortBy, Integer pageNumber, Integer pageSize, UserReq userReq) throws ModuleException {

		Sort sort = Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
		
		Page<User> data  = repository.findAll(Specification.where(mainSearch.build(searchBy, "user")).or(this.hasRole(searchBy)), pageable);
		
						
		return data.getContent();
	}
	
	private Specification<User> hasRole(String role) {
		return (user, cq, cb)  -> {
	        Join<Role, User> userJoin = user.join(StaticConstant.ROLE_ID); 
	        return cb.equal(userJoin.get("name"), role);
	        
		};

	}


}
