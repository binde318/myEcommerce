package com.commerce.ecommerce.service.UserServiceImpl;

import com.commerce.ecommerce.dto.request.UserRequestDto;
import com.commerce.ecommerce.dto.response.UserResponseDto;
import com.commerce.ecommerce.entity.User;
import com.commerce.ecommerce.exceptions.CustomException;
import com.commerce.ecommerce.repository.UserRepository;
import com.commerce.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserRequestDto request) {
        log.info("service:: about setting");
        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setAge(request.getAge());

        log.info("about saving");
        User saveUser = userRepository.save(user);
        log.info("saved user");
        return new UserResponseDto(saveUser.getName(), saveUser.getRole());
    }

    @Override
    public UserResponseDto fetchUserById(Long userId) {
        User saveUser = userRepository.findById(userId).get();
        return new UserResponseDto(saveUser.getName(), saveUser.getRole());
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto request) {
        // Get and Set, Save

        User userToUpdated = userRepository.findById(userId).get();

        userToUpdated.setPassword(request.getPassword());
        userToUpdated.setName(request.getName());

        User saveUser = userRepository.save(userToUpdated);
        return new UserResponseDto(saveUser.getName(), saveUser.getRole());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("userService loadUserByUserName - email :: [{}] ::", email);
        User user = userRepository.getUserByName(email)
                .orElseThrow(
                        () -> {
                            throw new CustomException("User not found");
                        }
                );
        Collection<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

}
