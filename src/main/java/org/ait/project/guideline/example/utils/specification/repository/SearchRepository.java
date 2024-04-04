package org.ait.project.guideline.example.utils.specification.repository;


import org.ait.project.guideline.example.utils.specification.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Search, String>, JpaSpecificationExecutor<Search> {

	Search findByClassName(String className);
}
