package market.market.service;

import market.market.model.Role;
import market.market.model.Utente;

import java.util.List;

public interface UtenteService {
    Utente saveUtente(Utente utente);

    Role saveRole(Role role);

    void addRoleToUtente(String username, String roleName);

    Utente getUtente(String username);

    List<Utente> getUtenti();

    void resetAll();
}
