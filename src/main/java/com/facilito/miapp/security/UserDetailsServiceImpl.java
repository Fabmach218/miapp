package com.facilito.miapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.facilito.miapp.model.Usuario;
import com.facilito.miapp.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepository.findByUsername(username);
        UserBuilder builder = null;
        
        if(usuario != null){
            builder = User.withUsername(username);
            builder.disabled(false);
            builder.password(usuario.getPassword());
            String authority = "";
            if(usuario.getTipoUsuario().equals("C")){
                authority = "ROLE_USER";
            }else if (usuario.getTipoUsuario().equals("A")){
                authority = "ROLE_ADMIN";
            }
            builder.authorities(new SimpleGrantedAuthority(authority));
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return builder.build();

    }
    
}
