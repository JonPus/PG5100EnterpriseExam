package no.enterprise.exam.frontend.controller;

import no.enterprise.exam.backend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class UserInfoController {

    /*@Autowired
    private PurchaseService purchaseService;*/

    private UserService userService;

    public String getUserName() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /*public Users getUser() {
        return userService.findUserByUserName(getUserName());
    }*/


}
