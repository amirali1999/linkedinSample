package com.example.linkedinSample.service;

import com.example.linkedinSample.response.Response;
import com.example.linkedinSample.model.Roles;
import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.exception.type.NotFoundException;
import com.example.linkedinSample.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public Response getRoles(int page) {
        Page<Roles> pages = rolesRepository.findAll(PageRequest.of(page-1,10));
        return new Response(HttpStatus.OK,
                "Get Roles successfully!",
                pages.get(),
                pages.getTotalPages());
    }

    public Response postRoles(Roles roles) throws DuplicateFieldException {
        checkDuplicateRoles(roles.getName());
        rolesRepository.save(roles);
        return new Response(HttpStatus.OK, "Add role successfully!", roles, 1);
    }

    public Response deleteRoles(Roles roles) throws NotFoundException {
        roles = rolesRepository.findByName(roles.getName())
                .orElseThrow(()-> new NotFoundException("Role not found!"));
        rolesRepository.deleteById(roles.getId());
        return new Response(HttpStatus.OK, "Delete role successfully!", roles, 1);
    }

    public Response putRoles(Roles roles,String roleName) throws DuplicateFieldException, NotFoundException {
        Roles previousRole = rolesRepository.findByName(roleName)
                .orElseThrow(()->new NotFoundException("Role not found!"));
        checkDuplicateRoles(roles.getName());
        if(roles.getName() != null){
            checkDuplicateRoles(roles.getName());
            previousRole.setName(roles.getName());
        }
        if(roles.getDescription() != null){
            previousRole.setDescription(roles.getDescription());
        }
        rolesRepository.save(previousRole);
        return new Response(HttpStatus.OK, "Update role successfully!", roles, 1);
    }

    private void checkDuplicateRoles(String roleName) throws DuplicateFieldException {
        if (rolesRepository.findByName(roleName).isPresent()) {
            throw new DuplicateFieldException("Role is duplicate!");
        }
    }
}
