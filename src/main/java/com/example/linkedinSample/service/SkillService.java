package com.example.linkedinSample.service;

import com.example.linkedinSample.exception.type.DuplicateFieldException;
import com.example.linkedinSample.exception.type.EmptyFieldException;
import com.example.linkedinSample.exception.type.NotFoundException;
import com.example.linkedinSample.exception.type.SkillWasDeletedException;
import com.example.linkedinSample.model.Skill;
import com.example.linkedinSample.model.Subject;
import com.example.linkedinSample.repository.SkillRepository;
import com.example.linkedinSample.repository.SubjectRepository;
import com.example.linkedinSample.request.UserSkillRequest;
import com.example.linkedinSample.response.Response;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SkillService {
    private final SkillRepository skillRepository;
    private final SubjectRepository subjectRepository;
    private final MessageSource messageSource;

    public SkillService(SkillRepository skillRepository,
                        SubjectRepository subjectRepository,
                        MessageSource messageSource) {
        this.skillRepository = skillRepository;
        this.subjectRepository = subjectRepository;
        this.messageSource = messageSource;
    }

    public Response getUsers(int page) {
        Page<Skill> pages = skillRepository.findAll(PageRequest.of(page - 1, 10));
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.skill.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                pages.get(),
                pages.getTotalPages());
    }

    public Response postUsers(Skill skill) throws DuplicateFieldException, EmptyFieldException {
        checkEmptyFields(skill);
        isSkillUnique(skill);
        skillRepository.save(skill);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("add.skill.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                skill,
                1);
    }

    public Response postSkillByUser(UserSkillRequest userSkillRequest)
            throws EmptyFieldException, DuplicateFieldException {
        Skill skill = new Skill(userSkillRequest.getName(),
                userSkillRequest.getDescription(),
                userSkillRequest.getSubject());
        checkEmptyFields(skill);
        isSkillUnique(skill);
        skillRepository.save(skill);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("add.skill.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                userSkillRequest,
                1);
    }

    public Response deleteSkill(long id) throws NotFoundException, SkillWasDeletedException {
        Skill findedskill = skillRepository.findById(id).orElse(null);
        isSkillAvailable(findedskill);
        findedskill.setDelete(true);
        findedskill.setVerify(false);
        findedskill.setDateOfDelete(new Date());
        skillRepository.save(findedskill);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("delete.skill.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                findedskill,
                1);
    }

    public Response putSkill(long id, Skill skill)
            throws SkillWasDeletedException, NotFoundException, DuplicateFieldException {
        Skill searchedSkill = skillRepository.findById(id).orElse(null);
        isSkillAvailable(searchedSkill);
        if (skill.getName() != null) {
            searchedSkill.setName(skill.getName());
        }
        if (skill.getDescription() != null) {
            searchedSkill.setDescription(skill.getDescription());
        }
        if (skill.isVerifyChange(searchedSkill.getVerify())) {
            searchedSkill.setVerify(true);
        }
        if (skill.getSubject() != null) {
            searchedSkill.setSubject(skill.getSubject());
        }
        isSkillUnique(searchedSkill);
        skillRepository.save(searchedSkill);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("update.skill.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                searchedSkill,
                1);
    }

    public Response getSkillByName(String skillName) throws NotFoundException {
        List<Skill> containSkills = new ArrayList<>();
        List<Skill> skills = skillRepository.findAll();
        if (skills == null){
            throw new NotFoundException("skill contain ths name not founded!");
        }
        for (Skill skill:skills){
            if(skill.getName().contains(skillName)){
                containSkills.add(skill);
            }
        }
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.skill.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                containSkills,
                1);
    }

    public Response getSkillById(long id) throws NotFoundException {
        Skill skill = skillRepository.findById(id).orElse(null);
        if (skill==null){
            throw new NotFoundException("Skill not found!");
        }
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.skill.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                skill,
                1);
    }

    public Response getSkillByVerify(int page, boolean verify) {
        Page<Skill> pages = skillRepository.findByVerify(verify, PageRequest.of(page - 1, 10));
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.skill.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                pages.get(),
                pages.getTotalPages());
    }

    private void checkEmptyFields(Skill skill) throws EmptyFieldException {
        if (skill.getName() == null) {
            throw new EmptyFieldException("name is Empty!");
        }
        if (skill.getSubject().getType() == null) {
            throw new EmptyFieldException("type is Empty!");
        }
    }

    private void isSkillUnique(Skill skill) throws DuplicateFieldException {
        List<Subject> searchedSubject = subjectRepository.
                findByTypeAndCompanyAndLanguageAndVersion(skill.getSubject().getType(),
                        skill.getSubject().getCompany(),
                        skill.getSubject().getLanguage(),
                        skill.getSubject().getVersion())
                .orElse(null);
        Skill isSkillDuplicate = skillRepository.findByNameAndSubject(skill.getName(), searchedSubject.get(0))
                .orElse(null);
        if (isSkillDuplicate != null && skill.getId() != isSkillDuplicate.getId()) {
            throw new DuplicateFieldException("skill is Duplicate!");
        }
    }

    private void isSkillAvailable(Skill skill) throws NotFoundException, SkillWasDeletedException {
        if (skill == null || skill.isDelete()) {
            throw new NotFoundException("skill not Found!");
        }
        isSkillDeleted(skill);
    }

    private void isSkillDeleted(Skill skill) throws SkillWasDeletedException {
        if (skill.isDelete()) {
            throw new SkillWasDeletedException("Skill is deleted");
        }
    }
}
