package registerationlogin.service;

import registerationlogin.dto.UserDto;
import registerationlogin.entity.User;
import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    void saveUpdateUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto>findAllUsers();

    UserDto findById(Long id);

    void deleteById(Long id);
}
