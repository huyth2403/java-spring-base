package com.services;

import com.config.jwt.JwtProvider;
import com.dto.UserDto;
import com.entities.db1.User;
import com.entities.db2.Profile;
import com.repositories.db1.StudentRepository;
import com.repositories.db1.UserRepository;
import com.repositories.db2.ProfileRepository;
import com.response.BaseResponse;
import com.response.ResponseCode;
import com.services.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserServiceImpl userService;

    public BaseResponse login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDto userDto = modelMapper.map(principal, UserDto.class);
            String token = jwtProvider.generateToken(userDto);
            return BaseResponse.success(token);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new BaseResponse(ResponseCode.ERROR_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(ResponseCode.ERROR);
        }
    }

    @Transactional
    public BaseResponse register(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = userService.saveOrUpdate(user);
            Profile profile = new Profile();
            profile.setUserId(user.getUserId());
            profileRepository.save(profile);
            return BaseResponse.success(user);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: " + ResponseCode.ERROR_DUPLICATE_ENTRY.getMessage());
            return new BaseResponse(ResponseCode.ERROR_DUPLICATE_ENTRY);
        }
    }
}
