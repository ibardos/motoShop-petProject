package com.ibardos.motoShop.security.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Model class, representing a Role in DB, which then should be connected to an ApplicationUser.
 * Roles are determine the level of authorization, by its permissions stored inside, for an individual ApplicationUser.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    // Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;


    /**
     * Returns the authorities that a Role actually has.
     * @return List<SimpleGrantedAuthority> containing a Set of permissions, and also the name of the Role itself.
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority("PERMISSION_" + permission.getName()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name));

        return authorities;
    }
}
