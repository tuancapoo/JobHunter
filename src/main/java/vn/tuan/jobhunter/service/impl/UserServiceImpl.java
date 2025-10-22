package vn.tuan.jobhunter.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.User;
import vn.tuan.jobhunter.domain.dto.responseDTO.userDTO.ResCreateUserDTO;
import vn.tuan.jobhunter.domain.dto.responseDTO.userDTO.ResUserDTO;
import vn.tuan.jobhunter.domain.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.dto.criterial.UserCriteriaDTO;
import vn.tuan.jobhunter.repository.UserRepository;
import vn.tuan.jobhunter.service.specification.UserSpecification;
import vn.tuan.jobhunter.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;


import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResCreateUserDTO createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return convertUserToResCreateUserDTO(user);
    }

    @Override
    public ResultPaginationDTO getAllUsers(UserCriteriaDTO userCriteriaDTO, Pageable pageable) {
        Page<User> page=userRepository.findAll(UserSpecification.userSpecification(userCriteriaDTO),pageable);

        List<ResUserDTO> users=page.getContent()
                .stream().map(item -> new ResUserDTO(
                        item.getId(),
                        item.getEmail(),
                        item.getUsername(),
                        item.getAge(),
                        item.getUpdatedAt(),
                        item.getCreatedAt(),
                        item.getGender(),
                        item.getAddress()))
                .collect(Collectors.toList());

        ResultPaginationDTO resultPaginationDTO=new ResultPaginationDTO();

        ResultPaginationDTO.Meta mt=new ResultPaginationDTO.Meta();
        mt.setPage(page.getNumber()+1);
        mt.setPageSize(page.getSize());

        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getNumberOfElements());

        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(users);
        return resultPaginationDTO;

    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User updatedUser) {
        return userRepository.findById(Long.valueOf(updatedUser.getId()))
                .map(user -> {
                    if (updatedUser.getUsername()!=null)
                        user.setUsername(updatedUser.getUsername());
                    if (updatedUser.getPassword()!=null)
                        user.setPassword(updatedUser.getPassword());
                    if (updatedUser.getAddress()!=null)
                        user.setAddress(updatedUser.getAddress());
                    if (updatedUser.getAge()!=null)
                        user.setAge(updatedUser.getAge());
                    if (updatedUser.getGender()!=null)
                        user.setGender(updatedUser.getGender());
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

    public ResCreateUserDTO convertUserToResCreateUserDTO(User user) {
        ResCreateUserDTO resCreateUserDTO=new ResCreateUserDTO();
        resCreateUserDTO.setId(user.getId());
        resCreateUserDTO.setUsername(user.getUsername());
        resCreateUserDTO.setEmail(user.getEmail());
        resCreateUserDTO.setAge(user.getAge());
        resCreateUserDTO.setGender(user.getGender());
        resCreateUserDTO.setCreatedAt(user.getCreatedAt());
        resCreateUserDTO.setAddress(user.getAddress());
        return resCreateUserDTO;
    }
    public  ResUserDTO convertUserToResUserDTO(User user) {
        ResUserDTO resUserDTO=new ResUserDTO();
        resUserDTO.setId(user.getId());
        resUserDTO.setUsername(user.getUsername());
        resUserDTO.setEmail(user.getEmail());
        resUserDTO.setAge(user.getAge());
        resUserDTO.setGender(user.getGender());
        resUserDTO.setCreatedAt(user.getCreatedAt());
        resUserDTO.setUpdatedAt(user.getUpdatedAt());
        resUserDTO.setAddress(user.getAddress());
        return resUserDTO;
    }

    public void updateUserToken(String token, String email) {
        User currentUser=userRepository.findByEmail(email);
        if (currentUser!=null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    @Override
    public User getUserByEmailAndRefreshToken(String email, String refreshToken) {
        return userRepository.findByEmailAndRefreshToken(email,refreshToken);
    }
    @Override
    public ResultPaginationDTO getAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> page=userRepository.findAll(spec,pageable);

        List<ResUserDTO> users=page.getContent()
                .stream().map(item -> new ResUserDTO(
                        item.getId(),
                        item.getEmail(),
                        item.getUsername(),
                        item.getAge(),
                        item.getUpdatedAt(),
                        item.getCreatedAt(),
                        item.getGender(),
                        item.getAddress()))
                .collect(Collectors.toList());

        ResultPaginationDTO resultPaginationDTO=new ResultPaginationDTO();

        ResultPaginationDTO.Meta mt=new ResultPaginationDTO.Meta();
        mt.setPage(page.getNumber()+1);
        mt.setPageSize(page.getSize());

        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getNumberOfElements());

        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(users);
        return resultPaginationDTO;

    }

}
