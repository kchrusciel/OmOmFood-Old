package pl.codecouple.omomfood.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.codecouple.omomfood.account.roles.RoleEnum;
import pl.codecouple.omomfood.account.roles.RoleRepository;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.account.users.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by krzysztof.chrusciel on 2016-09-09.
 */
@Slf4j
@Service
public class AccountServiceImpl implements UserDetailsService, AccountService{


    public static final String ADMIN_EMAIL_ADDRESS = "admin@omomfood.pl";

    /** {@link UserRepository} user repository instance. */
    private final UserRepository userRepository;
    /** {@link RoleRepository} role repository instance. */
    private final RoleRepository roleRepository;

    @Autowired
    public AccountServiceImpl(final UserRepository userRepository,
                              final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByConfirmationId(final String confirmId) {
        return userRepository.findByConfirmationId(confirmId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleEnum> getAllRoles() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(final User user) {
        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUser(final User user) {
        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByResetPasswordToken(final String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getAdminUser() {
        return userRepository.findByEmail(ADMIN_EMAIL_ADDRESS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByProviderUserID(final String providerUserID) {
        return userRepository.findByProviderUserID(providerUserID);
    }
}
