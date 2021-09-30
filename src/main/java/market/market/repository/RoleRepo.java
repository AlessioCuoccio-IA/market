package market.market.repository;

import market.market.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepo extends MongoRepository<Role, String> {
    Role findByNomeRole(String nomeRole);
}
