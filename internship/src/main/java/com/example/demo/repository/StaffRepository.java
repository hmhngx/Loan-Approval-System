package com.example.demo.repository;

import com.example.demo.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findById(Long id);

    Optional<Staff> findByEmail(String email);

    @Query("SELECT s FROM Staff s WHERE s.roleId = :roleId")
    List<Staff> findByRoleId(Long roleId);

    @Query("SELECT s FROM Staff s WHERE s.lastName = :lastName")
    List<Staff> findByLastName(String lastName);

    @Query("SELECT s FROM Staff s WHERE s.firstName = :firstName")
    List<Staff> findByFirstName(String firstName);

    void deleteById(Long id);
}
