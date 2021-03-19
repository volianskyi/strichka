package com.strichka.repository;

import com.strichka.entity.Role;

import java.util.List;

public interface RoleRepository {
    void save(Role role);
    Role findById(long id);
    List<Role> findAll();
}
