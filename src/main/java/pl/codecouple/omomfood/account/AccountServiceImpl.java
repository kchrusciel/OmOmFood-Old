package pl.codecouple.omomfood.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.codecouple.omomfood.account.roles.Role;
import pl.codecouple.omomfood.account.roles.RoleEnum;
import pl.codecouple.omomfood.account.roles.RoleRepository;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.account.users.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
@Slf4j
@Service
public class AccountServiceImpl implements UserDetailsService, AccountService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        log.debug("Load user by email");

        User user = userRepository.findByEmail(email);
        log.debug("Found user:" + user);

        if(user == null){
            log.debug("UsernameNotFoundException for email: " + email);
            throw new UsernameNotFoundException(email);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        log.debug("Grant authorities");
        for (RoleEnum role : user.getRoles()){
            log.debug("Authorities:" + role.getRoleName());
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        user.setAuthorities(grantedAuthorities);

        return user;
    }

    @Override
    public User getUserByConfirmationId(final String confirmId) {
        return userRepository.findByConfirmationId(confirmId);
    }

    @Override
    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<RoleEnum> getAllRoles() {
        return null;
    }

    @Override
    public void addUser(final User user) {
        userRepository.save(user);
    }
}
