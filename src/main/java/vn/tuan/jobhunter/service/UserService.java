package vn.tuan.jobhunter.service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.domain.dto.responseDTO.userDTO.ResCreateUserDTO;
import vn.tuan.jobhunter.domain.dto.responseDTO.userDTO.ResUserDTO;
import vn.tuan.jobhunter.domain.dto.responseDTO.ResultPaginationDTO;
import vn.tuan.jobhunter.domain.dto.criterial.UserCriteriaDTO;
import vn.tuan.jobhunter.domain.User;

import java.util.Optional;

@Service
public interface UserService {

    public ResCreateUserDTO createUser(User user);

    public ResultPaginationDTO getAllUsers(UserCriteriaDTO userCriteriaDTO, Pageable pageable);

    public Optional<User> getUserById(Long id);

    public User updateUser(User updatedUser);

    public void deleteUser(Long id);

    public User getUserByEmail(String email);

    public ResUserDTO convertUserToResUserDTO(User user);
    public  ResCreateUserDTO convertUserToResCreateUserDTO(User user);
    public void updateUserToken(String token, String email);
    public User getUserByEmailAndRefreshToken(String email, String refreshToken);
    public ResultPaginationDTO getAllUsers(Specification<User> spec, Pageable pageable);

}
