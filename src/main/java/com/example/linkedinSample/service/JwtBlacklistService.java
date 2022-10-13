package com.example.linkedinSample.service;

import com.example.linkedinSample.entity.JwtBlacklist;
import com.example.linkedinSample.exception.type.TokenExpireException;
import com.example.linkedinSample.repository.JwtBlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtBlacklistService {
    private final JwtBlacklistRepository jwtBlacklistRepository;

    public JwtBlacklistService(JwtBlacklistRepository jwtBlacklistRepository) {
        this.jwtBlacklistRepository = jwtBlacklistRepository;
    }

    public void checkAccessTokenExpire(String accessToken) throws TokenExpireException {
        if(jwtBlacklistRepository.findByAccessToken(accessToken).isPresent()){
            throw new TokenExpireException("token is Expired");
        }
    }

    public void saveTokenToBlackList(String accessToken,Date date){
        jwtBlacklistRepository.save(new JwtBlacklist(accessToken, date));
    }
}
