package com.example.demo.controller;

import com.example.demo.dispatcher.StaffDispatcher;
import com.example.demo.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffDispatcher dispatcher;

    @Autowired
    public StaffController(StaffDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Staff> findById(@PathVariable Long id) {
        Optional<Staff> staff = dispatcher.findById(id);
        return staff.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Staff>> findAll() {
        List<Staff> staffList = dispatcher.findAll();
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Staff> save(@RequestBody Staff staff) {
        Staff savedStaff = dispatcher.saveOrUpdate(staff);
        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        dispatcher.deleteById(id);
    }

    @GetMapping(value = "/role/{roleId}", produces = "application/json")
    public ResponseEntity<List<Staff>> findByRoleId(@PathVariable Long roleId) {
        List<Staff> staffList = dispatcher.findByRoleId(roleId);
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    @GetMapping(value = "/email/{email}", produces = "application/json")
    public ResponseEntity<Staff> findByEmail(@PathVariable String email) {
        Optional<Staff> staff = dispatcher.findByEmail(email);
        return staff.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
