package market.market.repository;

import market.market.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepo extends MongoRepository<Cliente, String> {
    Cliente findClienteByEmail(String email);
}
