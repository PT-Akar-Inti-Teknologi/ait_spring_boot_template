package org.ait.project.guideline.example.modules.user.service.adapter.query.impl;

import java.util.List;
import java.util.Optional;

import org.ait.project.guideline.example.config.exception.ModuleException;
import org.ait.project.guideline.example.modules.role.model.jpa.entity.Role;
import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.User;
import org.ait.project.guideline.example.modules.user.model.jpa.repository.UserRepository;
import org.ait.project.guideline.example.modules.user.service.adapter.query.UserQueryAdapter;
import org.ait.project.guideline.example.shared.constant.statics.StaticConstant;
import org.apache.commons.lang3.ObjectUtils;
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

	@Override
	public List<User> findUser(String searchBy, String sortBy, String sortField, Integer pageNumber, Integer pageSize, UserReq userReq) throws ModuleException {

		Sort sort = sortBy.equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

		if (ObjectUtils.isNotEmpty(searchBy)) {

			Page<User> data = repository.findAll(Specification.where(this.hasUsername(searchBy))
					.or(this.hasEmail(searchBy)).or(this.hasPhoneNumber(searchBy))
					.or(this.firstameLike(searchBy)).or(this.lastNameLike(searchBy))
					.or(this.hasRole(searchBy)), pageable);
			return data.getContent();

		} else {
			Page<User> data = repository.findAll(
					Specification.where(this.hasUsername(userReq.getUsername())).and(this.hasEmail(userReq.getEmail()))
							.and(this.hasPhoneNumber(userReq.getPhoneNumber()))
							.and(this.firstameLike(userReq.getFirstName()))
							.and(this.lastNameLike(userReq.getLastName())).and(this.hasRole(userReq.getRoleName())),
					pageable);
			return data.getContent();

		}

	}
	
	
	private Specification<User> hasUsername(String username) {
		return (user, cq, cb) -> Optional.ofNullable(username).map(usernameMap -> cb.equal(user.get(StaticConstant.USERNAME), usernameMap))
				.orElse(null);
	}
	
	private Specification<User> hasEmail(String email) {
		return (user, cq, cb) -> Optional.ofNullable(email).map(emailMap -> cb.equal(user.get(StaticConstant.EMAIL), emailMap))
				.orElse(null);
	}
	
	private Specification<User> hasPhoneNumber(String phoneNumber) {
		return (user, cq, cb) -> Optional.ofNullable(phoneNumber).map(phoneNumberMap -> cb.equal(user.get(StaticConstant.PHONE_NUMBER), phoneNumberMap))
				.orElse(null);
	}

	private Specification<User> firstameLike(String firstName) {
		return (user, cq, cb) -> Optional.ofNullable(firstName).map(firstNameMap -> cb.like(user.get(StaticConstant.FIRSTNAME), StaticConstant.PERCENT + firstNameMap + StaticConstant.PERCENT))
				.orElse(null);
	}

	private Specification<User> lastNameLike(String lastName) {
		return (user, cq, cb) -> Optional.ofNullable(lastName).map(lastNameMap -> cb.like(user.get(StaticConstant.LASTNAME), StaticConstant.PERCENT + lastNameMap + StaticConstant.PERCENT))
				.orElse(null);
	}
			
	private Specification<User> hasRole(String role) {
		return (user, cq, cb)  -> {
	        Join<Role, User> userJoin = user.join(StaticConstant.ROLE_ID); 
	        return cb.equal(userJoin.get("name"), role);
	        
		};

	}
	

}
