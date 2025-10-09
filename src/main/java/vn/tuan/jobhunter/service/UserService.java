package vn.tuan.jobhunter.service;
import org.springframework.stereotype.Service;
import vn.tuan.jobhunter.repository.UserRepository;
import vn.tuan.jobhunter.domain.User;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    public User createUser(User user);

    public List<User> getAllUsers();

    public Optional<User> getUserById(Long id);

    public User updateUser(Long id, User updatedUser);

    public void deleteUser(Long id);

    public User getUserByEmail(String email);
}
