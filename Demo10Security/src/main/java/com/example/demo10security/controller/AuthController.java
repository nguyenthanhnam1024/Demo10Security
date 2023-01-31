//package com.example.demo10security.controller;
//
//import com.example.demo10security.configure.UserDetailsImpl;
//import com.example.demo10security.entity.Role;
//import com.example.demo10security.entity.User;
//import com.example.demo10security.jwt.JwtUtils;
//import com.example.demo10security.repository.RoleRepo;
//import com.example.demo10security.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    UserRepo userRepo;
//
//    @Autowired
//    RoleRepo roleRepo;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @PostMapping("/signin")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        String jwt = jwtUtils.generateJwt(userDetails);
//
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(new JwtResponse(jwt,
//                userDetails.getId(),
//                userDetails.getUsername(),
//                userDetails.getEmail(),
//                roles));
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> asignRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        // Nếu không truyền thì set role mặc định là ROLE_USER
//        if (asignRoles == null) {
//            Role userRole = roleRepo.findByName(RoleEnum.ROLE_USER.getRole())
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            asignRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepo.findByName(RoleEnum.ROLE_ADMIN.getRole())
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR.getRole())
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepo.findByName(RoleEnum.ROLE_USER.getRole())
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepo.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
//}