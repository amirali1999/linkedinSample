package com.example.linkedinSample.service;

import com.example.linkedinSample.entity.Roles;
import com.example.linkedinSample.entity.Users;
import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.exception.type.NotFoundException;
import com.example.linkedinSample.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<Roles> getRoles() {
        return rolesRepository.findAll();
    }

    public Roles postRoles(Roles roles) throws DuplicateFieldException {
        checkDuplicateRoles(roles.getName());
        rolesRepository.save(roles);
        return roles;
    }

    public Roles deleteRoles(Roles roles) throws NotFoundException {
        roles = rolesRepository.findByName(roles.getName())
                .orElseThrow(()-> new NotFoundException("Role not found!"));
        rolesRepository.deleteById(roles.getId());
        return roles;
    }

    public Roles putRoles(Roles roles,String roleName) throws DuplicateFieldException, NotFoundException {
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
        return roles;
    }

    private void checkDuplicateRoles(String roleName) throws DuplicateFieldException {
        if (rolesRepository.findByName(roleName).isPresent()) {
            throw new DuplicateFieldException("Role is duplicate!");
        }
    }
}
