package market.market.service;

import market.market.model.Prodotto;

import java.util.List;

public interface ProductService {

    public Prodotto addProdotto(Prodotto prodotto);

    public Prodotto getProdottoById(Integer id);

    public Prodotto getProdottoByName(String nome);

    public List<Prodotto> getProdotti();

    public List<Prodotto> getProdottiNomeSimile(String nome);

    public void updateProdotto(Prodotto prodotto);

    public void deleteProdottoById(Integer id);

    public List<Prodotto> getProdottoInRange(Double minimo, Double massimo);


}
