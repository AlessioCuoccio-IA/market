package market.market.repository;

import market.market.model.Prodotto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends MongoRepository<Prodotto, Integer> {

    public Prodotto findByNome(String nome);

    @Query("{ 'nome' : { $regex: ?0 } }")
    List<Prodotto> findByRegexpName(String regexp); //.latte. dovrebbe funzionare, da testare->test regex https://regex101.com/

    public List<Prodotto> findByPrezzoBetween(Double prezzomin, Double prezzomax);
}
