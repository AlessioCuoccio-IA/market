package market.market.service;

import market.market.model.Carrello;
import market.market.model.Cliente;
import market.market.model.NotaSpesa;
import market.market.model.RicercaCarrello;

import java.util.List;

public interface CarrelloService {

    public Carrello addCarrello(NotaSpesa notaSpesa);

    public Carrello getCarrellobyId(Integer id);

    public List<Carrello> getAllCarrelli();

    public RicercaCarrello findCarrelliByAnno(Integer anno);

    public RicercaCarrello findCarrelliByDataAndTotaleRange(Integer anno, Double min, Double max);

    public boolean controllaBudget(Carrello carrello, Cliente cliente);

    public void eliminaCarrello(Carrello carrello);
}