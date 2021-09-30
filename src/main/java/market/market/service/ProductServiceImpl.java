package market.market.service;


import lombok.extern.slf4j.Slf4j;
import market.market.exception.MyNotAcceptableException;
import market.market.exception.MyNotFoundException;
import market.market.model.Prodotto;
import market.market.repository.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductsRepo productsRepo;

    private Integer counter = 0;

    @Autowired
    public ProductServiceImpl(ProductsRepo productsRepo) {
        this.productsRepo = productsRepo;
    }

    @Override
    public Prodotto addProdotto(Prodotto prodotto) {
        if (productsRepo.findByNome(prodotto.getNome()) != null) {
            throw new MyNotAcceptableException("Il prodotto " + prodotto.getNome() + " è già presente all'interno del db");
        }
        this.counter++;
        Prodotto temp = prodotto;
        temp.set_id(counter);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/market/products/" + counter).toUriString());
        temp.setSelfUrl(uri.toString());

        productsRepo.save(temp);
        return temp;
    }

    @Override
    public List<Prodotto> getProdotti() {
        return productsRepo.findAll();
    }

    @Override
    public Prodotto getProdottoById(Integer id) {
        Prodotto temp = productsRepo.findById(id).get();
        if (temp == null) {
            throw new MyNotFoundException("Il prodotto con l'id indicato non è presente all'interno del sistema");
        }
        return temp;
    }


    public Prodotto getProdottoByName(String nome) {
        return productsRepo.findByNome(nome);
    }

    @Override
    public List<Prodotto> getProdottiNomeSimile(String nome) {
        return productsRepo.findByRegexpName(nome);
    }

    @Override
    public void updateProdotto(Prodotto prodotto) {
        if (!productsRepo.findById(prodotto.get_id()).isPresent())
            throw new MyNotFoundException("Prodotto da aggiornare non trovato");
        else
            productsRepo.save(prodotto);

    }

    @Override
    public void deleteProdottoById(Integer id) {
        productsRepo.deleteById(id);
    }

    @Override
    public List<Prodotto> getProdottoInRange(Double minimo, Double massimo) {
        return productsRepo.findByPrezzoBetween(minimo, massimo);
    }
}
