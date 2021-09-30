package market.market.repository;

import market.market.model.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UtenteRepo extends MongoRepository<Utente, String> {
    Utente findUtenteByUsername(String username);
}
