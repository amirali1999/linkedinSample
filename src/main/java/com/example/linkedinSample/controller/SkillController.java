package com.example.linkedinSample.controller;

import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.exception.type.EmptyFieldException;
import com.example.linkedinSample.exception.type.NotFoundException;
import com.example.linkedinSample.exception.type.SkillWasDeletedException;
import com.example.linkedinSample.model.Skill;
import com.example.linkedinSample.request.UserSkillRequest;
import com.example.linkedinSample.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/skill")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(path = "getallskills/{page}")
    public ResponseEntity<?> getSkill(@PathVariable("page") int page) {
        return skillService.getUsers(page).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin','user')")
    @GetMapping(path = "getskillbyname/{skillname}")
    public ResponseEntity<?> getSkillByName(@PathVariable("skillname") String skillName) throws NotFoundException {
        return skillService.getSkillByName(skillName).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin','user')")
    @GetMapping(path = "getskillbyid/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable("id") long id) throws NotFoundException {
        return skillService.getSkillById(id).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(path = "getskillbyverify/{verify}/{page}")
    public ResponseEntity<?> getSkillByVerify(@PathVariable("page") int page,
                                              @PathVariable("verify") boolean verify) {
        return skillService.getSkillByVerify(page, verify).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin','user')")
    @GetMapping(path = "getskillstouser/{page}")
    public ResponseEntity<?> getSkillsToUser(@PathVariable("page") int page) {
        return skillService.getSkillByVerify(page, true).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping(path = "addnewskill")
    public ResponseEntity<?> postSkill(@Valid @RequestBody Skill skill)
            throws DuplicateFieldException, EmptyFieldException {
        return skillService.postUsers(skill).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin','user')")
    @PostMapping(path = "addnewskillbyuser")
    public ResponseEntity<?> postSkillByUser(@Valid @RequestBody UserSkillRequest userSkillRequest)
            throws DuplicateFieldException, EmptyFieldException {
        return skillService.postSkillByUser(userSkillRequest).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping(path = "deleteskill/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable("id") long id)
            throws NotFoundException, SkillWasDeletedException {
        return skillService.deleteSkill(id).createResponseEntity();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping(path = "updateskill/{id}")
    public ResponseEntity<?> putSkill(@PathVariable("id") long id, @Valid @RequestBody Skill skill)
            throws SkillWasDeletedException, NotFoundException, DuplicateFieldException {
        return skillService.putSkill(id, skill).createResponseEntity();
    }


}
