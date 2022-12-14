package com.example.linkedinSample.controller;

import com.example.linkedinSample.model.Roles;
import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.exception.type.NotFoundException;
import com.example.linkedinSample.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "roles")
public class RolesController {
    @Autowired
    private final RolesService rolesService;


    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(path = "getallroles/{page}")
    public ResponseEntity<?> getRoles(@PathVariable("page") int page) {
        return rolesService.getRoles(page).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping(path = "addrole")
    public ResponseEntity<?> postRoles(@Valid @RequestBody Roles roles) throws DuplicateFieldException {
        return rolesService.postRoles(roles).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping(path = "deleterole")
    public ResponseEntity<?> deleteRoles(@Valid @RequestBody Roles roles) throws NotFoundException {
        return rolesService.deleteRoles(roles).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping(path = "updaterole/{rolename}")
    public ResponseEntity<?> putRoles(@PathVariable("rolename") String roleName,@Valid @RequestBody Roles roles)
            throws DuplicateFieldException, NotFoundException {
        return rolesService.putRoles(roles,roleName).createResponseEntity();
    }
}
