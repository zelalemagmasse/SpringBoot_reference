package com.zol.fsite.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.zol.fsite.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>{
    Role findByRole(String rolename);
}
