package com.strichka.service;

import com.strichka.entity.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);
    Role findById(long id);
    List<Role> findAll();
}
