package com.example.demo.domain;

import com.example.demo.dispatcher.StaffDispatcher;
import com.example.demo.model.Staff;
import com.example.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StaffDomain implements StaffDispatcher {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    @Transactional
    public Staff saveOrUpdate(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> findByRoleId(Long roleId) {
        return staffRepository.findByRoleId(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Staff> findByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        staffRepository.deleteById(id);
    }
}
