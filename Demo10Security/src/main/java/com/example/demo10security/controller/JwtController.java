package com.example.demo10security.controller;

import com.example.demo10security.configure.UserDetailsImpl;
import com.example.demo10security.entity.Permission;
import com.example.demo10security.entity.Role;
import com.example.demo10security.entity.User;
import com.example.demo10security.jwt.JwtUtils;
import com.example.demo10security.model.UserModel;
import com.example.demo10security.repository.PermissionRepo;
import com.example.demo10security.repository.RoleRepo;
import com.example.demo10security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class JwtController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PermissionRepo permissionRepo;
    @PostMapping ("/login")
    public String authenticateUser(@RequestBody UserModel userMode) {
//        Permission permission = new Permission(1l, "role_user");
//        Permission permission = permissionRepo.getOne(1l);
//        Set<Permission> permissions = new HashSet<>();
//        permissions.add(permission);
//        Role role = new Role(1l, "role_user", permissions);
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String pass = "123";
//        User user = new User(1l, "nam", "nam@gmail.com", bCryptPasswordEncoder.encode(pass), roles);
//        userRepo.save(user);
////        roleRepo.save(role);
//        return "abc";
        // X??c th???c t??? username v?? password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userMode.getUserName(),
                        userMode.getPassWourd()
                )
        );
        // N???u kh??ng x???y ra exception t???c l?? th??ng tin h???p l???
        // Set th??ng tin authentication v??o Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Tr??? v??? jwt cho ng?????i d??ng.
        String jwt = jwtUtils.generateJwt((UserDetailsImpl) authentication.getPrincipal());
        return jwt;
    }
    @GetMapping("/loginPage")
    public String loginPage(){
        return "loginPage";
    }
    // Api /api/random y??u c???u ph???i x??c th???c m???i c?? th??? request
    @GetMapping("/random")
    public String randomStuff(){
        return "JWT H???p l??? m???i c?? th??? th???y ???????c message n??y";
    }
}
