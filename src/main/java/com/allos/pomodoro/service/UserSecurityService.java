package com.allos.pomodoro.service;

import com.allos.pomodoro.security.UserSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserSecurityService {

    private UserSecurityService() { throw new IllegalStateException("Error ao atenticar usuario");}

    public static UserSecurity authenticate(){
        try {
            return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e){
            return null;
        }
    }
}
