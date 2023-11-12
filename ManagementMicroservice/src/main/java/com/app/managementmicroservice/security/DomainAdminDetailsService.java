package com.app.managementmicroservice.security;
import com.app.managementmicroservice.domain.Manager;
import com.app.managementmicroservice.repository.ManagementMongoRepository;
import com.app.managementmicroservice.domain.Authority;
import com.app.managementmicroservice.domain.Manager;
import com.app.managementmicroservice.repository.ManagementMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class DomainAdminDetailsService implements UserDetailsService{
    private final ManagementMongoRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return managerRepository
                .findManagerByEmailIgnoreCase( email )
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el empleado con email " + email ));
    }


    private org.springframework.security.core.userdetails.User createSpringSecurityUser(Manager manager) {
        List<GrantedAuthority> grantedAuthorities = manager
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(manager.getEmail(), manager.getPassword(), grantedAuthorities);
    }

}
