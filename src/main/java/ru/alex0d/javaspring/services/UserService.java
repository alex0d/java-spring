package ru.alex0d.javaspring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alex0d.javaspring.models.User;
import ru.alex0d.javaspring.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public boolean registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }
}
