package no.enterprise.exam.frontend.controller;

import no.enterprise.exam.backend.entity.Copy;
import no.enterprise.exam.backend.entity.Users;
import no.enterprise.exam.backend.service.CopyService;
import no.enterprise.exam.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private CopyService copyService;

    @Autowired
    private UserService userService;

    public String getUserName() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public Users getUser() {
        return userService.findUserByUserName(getUserName());
    }

    public List<Copy> listCopies() {
        return copyService.filterPurchaseByUser(getUserName());
    }
}
