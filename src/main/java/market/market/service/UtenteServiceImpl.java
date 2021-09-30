package market.market.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.market.model.Role;
import market.market.model.Utente;
import market.market.repository.RoleRepo;
import market.market.repository.UtenteRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UtenteServiceImpl implements UtenteService, UserDetailsService {

    private final UtenteRepo utenteRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Utente saveUtente(Utente utente) {
        log.info("Salvataggio dell'utente {} all'interno del database", utente.getUsername());
        utente.getRoles().forEach(
                role -> {
                    String nome = role.getNomeRole();
                    role.setIdRole(roleRepo.findByNomeRole(nome).getIdRole());
                }
        );
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return this.utenteRepo.save(utente);
    }

    @Override
    public Role saveRole(Role role) {
        return this.roleRepo.save(role);
    }

    @Override
    public void addRoleToUtente(String username, String roleName) {
        Utente utente = this.utenteRepo.findUtenteByUsername(username);
        Role role = this.roleRepo.findByNomeRole(roleName);
        log.info("Aggiungo il ruolo {} all'utente {}.", role.getNomeRole(), utente.getUsername());
        utente.getRoles().add(role);

        this.utenteRepo.deleteById(utente.getId());
        this.utenteRepo.save(utente);
    }

    @Override
    public Utente getUtente(String username) {
        this.utenteRepo.findUtenteByUsername(username).getRoles().forEach(
                ruolo -> {
                    System.out.println(ruolo.getNomeRole());
                }
        );
        return this.utenteRepo.findUtenteByUsername(username);
    }

    @Override
    public List<Utente> getUtenti() {
        return this.utenteRepo.findAll();
    }

    @Override
    public void resetAll() {
        this.utenteRepo.deleteAll();
        this.roleRepo.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = this.utenteRepo.findUtenteByUsername(username);
        if (utente == null) {
            log.error("Utente non trovato all'interno del db!");
            throw new UsernameNotFoundException("Utente non trovato all'interno del db!");
        } else {
            log.info("Utente richiesto trovato nel db: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            utente.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getNomeRole()));
            });
            return new User(utente.getUsername(), utente.getPassword(), authorities);
        }
    }
}
