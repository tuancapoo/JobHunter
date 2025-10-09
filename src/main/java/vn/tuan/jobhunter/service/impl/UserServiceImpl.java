package vn.tuan.jobhunter.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.repository.UserRepository;
import vn.tuan.jobhunter.service.UserService;

import java.util.NoSuchElementException;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updatedUser.getUsername()!=null)
                        user.setUsername(updatedUser.getUsername());
                    if (updatedUser.getPassword()!=null)
                        user.setPassword(updatedUser.getPassword());
                    if (updatedUser.getEmail()!=null)
                        user.setEmail(updatedUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
