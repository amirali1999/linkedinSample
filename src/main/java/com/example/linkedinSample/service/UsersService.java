package com.example.linkedinSample.service;

import com.example.linkedinSample.FeignClientInterceptor;
import com.example.linkedinSample.UserToolBox;
import com.example.linkedinSample.config.security.jwt.JwtUtils;
import com.example.linkedinSample.exception.type.*;
import com.example.linkedinSample.model.Skill;
import com.example.linkedinSample.model.Users;
import com.example.linkedinSample.repository.SkillRepository;
import com.example.linkedinSample.repository.UsersRepository;
import com.example.linkedinSample.request.UserUpdateRequest;
import com.example.linkedinSample.response.Response;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final UserToolBox userToolBox;
    private final JwtUtils jwtUtils;
    private final SkillRepository skillRepository;
    private final FeignClientInterceptor feignClientInterceptor;
    private final MessageSource messageSource;

    public UsersService(UsersRepository usersRepository,
                        UserToolBox userToolBox,
                        JwtUtils jwtUtils,
                        SkillRepository skillRepository,
                        FeignClientInterceptor feignClientInterceptor,
                        MessageSource messageSource) {
        this.usersRepository = usersRepository;
        this.userToolBox = userToolBox;
        this.jwtUtils = jwtUtils;
        this.skillRepository = skillRepository;
        this.feignClientInterceptor = feignClientInterceptor;
        this.messageSource = messageSource;
    }

    public Response getUsers(int page) {
        Page<Users> pages = usersRepository.findAll(PageRequest.of(page - 1, 10));
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.Users.successfully", null,LocaleContextHolder.getLocale()),
                pages.get(),
                pages.getTotalPages());
    }

    public Response postUsers(Users users)
            throws InvalidCharacterException, InvalidLengthException, InvalidPasswordException, EmptyFieldException,
            DuplicateFieldException, InvalidEmailException, InvalidRolesException {
        users = userToolBox.toolBox(users);
        usersRepository.save(users);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("add.user.successfully", null, LocaleContextHolder.getLocale()),
                users,
                1);
    }

    public Response deleteUsers(Users users) throws NotFoundException {
        users = usersRepository.findByUsername(users.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        usersRepository.delete(users);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("delete.users.successfully", null, LocaleContextHolder.getLocale()),
                users,
                1);
    }

    public Response putUsers(Users users, String usersName)
            throws NotFoundException, DuplicateFieldException, InvalidCharacterException, InvalidLengthException,
            InvalidPasswordException, InvalidEmailException, InvalidGenderException {
        Users previousUsers = usersRepository.findByUsername(usersName)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (users.getName() != null) {
            previousUsers.setName(users.getName());
        }
        if (users.getUsername() != null) {
            userToolBox.checkDuplicateUsername(users.getUsername());
            userToolBox.checkUsername(users.getUsername());
            previousUsers.setUsername(users.getUsername());
        }
        if (users.getPassword() != null) {
            userToolBox.checkPassword(users.getPassword());
            previousUsers.setPassword(userToolBox.encodePassword(users.getPassword()));
        }
        if (users.getEmail() != null) {
            userToolBox.checkDuplicateEmail(users.getEmail());
            userToolBox.checkEmail(users.getEmail());
            previousUsers.setEmail(users.getEmail());
        }
        if (users.getGender() != null) {
            previousUsers.setGender(users.getGender());
        }
        usersRepository.save(previousUsers);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("delete.users.successfully", null, LocaleContextHolder.getLocale()),
                previousUsers,
                1);
    }

    public Response userUpdateRequest(UserUpdateRequest userUpdateRequest) throws InvalidPasswordException {
        String accessToken = feignClientInterceptor.getBearerTokenHeader().replace("Bearer ", "");
        String username = jwtUtils.getUserNameFromJwtToken(accessToken);
        Users users = usersRepository.findByUsername(username).orElse(null);
        if (userUpdateRequest.getName() != null) {
            users.setName(userUpdateRequest.getName());
        }
        if (userUpdateRequest.getPassword() != null) {
            userToolBox.checkPassword(userUpdateRequest.getPassword());
            users.setPassword(userToolBox.encodePassword(userUpdateRequest.getPassword()));
        }
        if (userUpdateRequest.getAboutMe() != null) {
            users.setAboutMe(userUpdateRequest.getAboutMe());
        }
        usersRepository.save(users);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("update.users.successfully", null, LocaleContextHolder.getLocale()),
                users,
                1);

    }

    public Response addSkillToUser(long skillId) throws DuplicateFieldException, NotFoundException {
        String accessToken = feignClientInterceptor.getBearerTokenHeader().replace("Bearer ", "");
        String username = jwtUtils.getUserNameFromJwtToken(accessToken);
        Users users = usersRepository.findByUsername(username).orElse(null);
        Skill wantedSkill = skillRepository.findById(skillId).orElse(null);
        if(wantedSkill == null){
            throw new NotFoundException("skill not found!");
        }
        List<Skill> userSkills = users.getSkills();
        for (Skill skill : userSkills) {
            if (skill.getId() == skillId) {
                throw new DuplicateFieldException("skill is founded in user's skills");
            }
        }
        userSkills.add(wantedSkill);
        users.setSkills(userSkills);
        usersRepository.save(users);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("update.users.successfully", null, LocaleContextHolder.getLocale()),
                users,
                1);
    }

    public Response removeSkillToUser(long skillId) throws NotFoundException {
        String accessToken = feignClientInterceptor.getBearerTokenHeader().replace("Bearer ", "");
        String username = jwtUtils.getUserNameFromJwtToken(accessToken);
        Users users = usersRepository.findByUsername(username).orElse(null);
        Skill wantedSkill = skillRepository.findById(skillId).orElse(null);
        if(wantedSkill == null){
            throw new NotFoundException("skill not found!");
        }
        List<Skill> userSkills = users.getSkills();
        for (Skill skill : userSkills) {
            if (skill.getId() == skillId) {
                userSkills.remove(wantedSkill);
                users.setSkills(userSkills);
                usersRepository.save(users);
                return new Response(HttpStatus.OK,
                        messageSource.getMessage("update.users.successfully",
                                null,
                                LocaleContextHolder.getLocale()
                        ),
                        users,
                        1);
            }
        }
        throw new NotFoundException("skill not found!");
    }
}
