package registerationlogin.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import registerationlogin.dto.UserDto;
import registerationlogin.entity.User;
import registerationlogin.service.UserService;
import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        User existingUser = userService.findByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "there is already an account existed with this email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        System.out.println("ssssss");
        List<UserDto> users = userService.findAllUsers();
        System.out.println(users.size());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/edit/{id}")
    String showUpdateForm(@PathVariable("id") long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, UserDto userDto, BindingResult result, Model model) {            
        userService.saveUpdateUser(userDto);
        return "redirect:/users";
    }
        
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
