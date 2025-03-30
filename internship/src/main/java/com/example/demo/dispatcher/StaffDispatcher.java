package com.example.demo.dispatcher;

import com.example.demo.model.Staff;
import java.util.List;
import java.util.Optional;

public interface StaffDispatcher {

    Staff saveOrUpdate(Staff staff);

    Optional<Staff> findById(Long id);

    List<Staff> findAll();

    void deleteById(Long id);

    List<Staff> findByRoleId(Long roleId);

    Optional<Staff> findByEmail(String email);

}
