package registerationlogin.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import registerationlogin.dto.UserDto;
import registerationlogin.entity.Role;
import registerationlogin.entity.User;
import registerationlogin.repository.RoleRepository;
import registerationlogin.repository.UserRepository;
import registerationlogin.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setNomorhp(userDto.getNomorhp());
        user.setTanggalLahir(userDto.getTanggalLahir());
        user.setJenisKelamin(userDto.getJenisKelamin());
        user.setKtp(userDto.getKtp());

        Role role = roleRepository.findByName("ROLE_ADMIN");

        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> user_dto = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setKtp(user.getKtp());
            user_dto.add(userDto);
        }
        return user_dto;
    }

    @Override
    public UserDto findById(Long id) {
        UserDto userDto = new UserDto();
        User user = userRepository.findById(id).get();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setJenisKelamin(user.getJenisKelamin());
        userDto.setKtp(user.getKtp());
        userDto.setName(user.getName());
        userDto.setNomorhp(user.getNomorhp());
        userDto.setTanggalLahir(user.getTanggalLahir());
        return userDto;
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
    }

    @Override
    public void saveUpdateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        user.setName(userDto.getName());
        user.setNomorhp(userDto.getNomorhp());
        user.setTanggalLahir(userDto.getTanggalLahir());
        user.setJenisKelamin(userDto.getJenisKelamin());
        user.setKtp(userDto.getKtp());

        userRepository.save(user);
    }
}
