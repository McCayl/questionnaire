package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.dto.TestScoreDTO;
import com.mccayl.questionnaire.model.Role;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.model.UserTest;
import com.mccayl.questionnaire.repo.RoleRepository;
import com.mccayl.questionnaire.repo.UserRepository;
import com.mccayl.questionnaire.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User save(User user) {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        roleRepository.save(role);
        user.addRole(roleRepository.findByName("ROLE_ADMIN"));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Page<TestScoreDTO> getUserScore(Long uid, Pageable pageable) {
        return userRepository.getUserScore(uid, pageable);
    }

    @Override
    public Page<UserTest> getUserInfoById(Long uid, Pageable pageable) {
        return userRepository.getUserInfoById(uid, pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user " + username));
    }
}
