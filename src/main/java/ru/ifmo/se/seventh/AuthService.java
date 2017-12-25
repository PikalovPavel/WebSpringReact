package ru.ifmo.se.seventh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Service
public class AuthService implements UserDetailsService {
    private final StudentRepository studentRepository;

    @Autowired
    public AuthService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username);
        System.out.println(username);
        System.out.println(student.getPassword());
        System.out.println(Arrays.toString(student.getRoles()));
        if (student == null) throw new UsernameNotFoundException(username + " was not found!");
        return new User(username, student.getPassword(), AuthorityUtils.createAuthorityList(student.getRoles()));
    }
}
