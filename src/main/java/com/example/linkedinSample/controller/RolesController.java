package com.example.linkedinSample.controller;

import com.example.linkedinSample.Response;
import com.example.linkedinSample.entity.Roles;
import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.exception.type.NotFoundException;
import com.example.linkedinSample.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "getallroles")
    public ResponseEntity<?> getRoles() {
        Response response = new Response(HttpStatus.OK,
                "Get roles successfully!",
                rolesService.getRoles(),
                1);
        return response.createResponseEntity();
    }

    @PostMapping(path = "addrole")
    public Roles postRoles(@Valid @RequestBody Roles roles) throws DuplicateFieldException {
        return rolesService.postRoles(roles);
    }

    @DeleteMapping(path = "deleterole")
    public Roles deleteRoles(@Valid @RequestBody Roles roles) throws NotFoundException {
        System.out.println(roles);
        return rolesService.deleteRoles(roles);
    }

    @PutMapping(path = "updaterole/{rolename}")
    public Roles putRoles(@PathVariable("rolename") String roleName,@Valid @RequestBody Roles roles)
            throws DuplicateFieldException, NotFoundException {
        return rolesService.putRoles(roles,roleName);
    }
}
