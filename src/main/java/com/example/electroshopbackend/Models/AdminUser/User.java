package com.example.electroshopbackend.Models.AdminUser;

import com.example.electroshopbackend.Models.AdminUserToken.AdminUserToken;
import com.example.electroshopbackend.Models.Cart.Cart;
import com.example.electroshopbackend.Models.Role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, length = 64)
    private String email;

    @Column(name = "passwordHash", nullable = false, length = 64)
    private String passwordHash;

    @Column(name = "forename", nullable = false, length = 64)
    private String forename;

    @Column(name = "surname", nullable = false, length = 64)
    private String  surname;

    @Column(name = "phoneNumber", nullable = false, length = 16, unique = true)
    private String phoneNumber;

    @Column(name = "postalAddress", nullable = false, columnDefinition = "TEXT")
    private String postalAddress;


    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private Set<Cart> carts;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private Set<AdminUserToken> adminUserTokens;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "roleId")}
    )
    private Set<Role> authorities;;



    public User(){
        super();
        this.authorities = new HashSet<Role>();
    }

    public User( String email, String password_hash, String forename, String surname, String phone_number, String postal_address, Set<Role> authorities) {
        super();
        this.email = email;
        this.passwordHash = password_hash;
        this.forename = forename;
        this.surname = surname;
        this.phoneNumber = phone_number;
        this.postalAddress = postal_address;
        this.authorities = authorities;
    }

    public User(String email, String password, Set<Role> authority, String forename, String surname, String phone_number, String postal_address) {
        super();
        this.email = email;
        this.passwordHash = password;
        this.authorities =authority;
        this.forename =forename;
        this.surname =surname;
        this.phoneNumber =phone_number;
        this.postalAddress = postal_address;
    }


    public Long getUserId() {
        return userId;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
