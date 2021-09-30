package market.market.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.market.model.*;
import market.market.repository.CarrelloRepo;
import market.market.repository.ProductsRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CarrelloServiceImpl implements CarrelloService {
    private final CarrelloRepo carrelloRepo;
    private final ProductsRepo productsRepo;

    private Integer counter = 0;

    @Override
    public Carrello addCarrello(NotaSpesa notaSpesa) {
        this.counter++;
        Carrello temp = new Carrello();
        temp.set_id(counter);
        List<ProdottoSpesa> listaspesa = notaSpesa.getListaspesa();
        listaspesa.forEach(prodottoinlista -> {
            Double prezzo = productsRepo.findByNome(prodottoinlista.getNome()).getPrezzo();
            VoceScontrino voceScontrino = new VoceScontrino(prodottoinlista.getNome(), prodottoinlista.getQuantity(),
                    Double.valueOf(prodottoinlista.getQuantity().toString()) * prezzo);
            temp.getListaspesa().add(voceScontrino);
        });
        Double temptot = 0.00;
        temptot = temp.getListaspesa().stream().map(x -> x.getSubtot()).reduce(0.00, Double::sum);
        log.info("Il totale ottenuto è {}", temptot.toString());
        temp.setTotale(temptot);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/market/carrelli/" + counter).toUriString());
        temp.setSelfUrl(uri.toString());
        carrelloRepo.save(temp);

        return temp;
    }

    @Override
    public Carrello getCarrellobyId(Integer id) {
        return carrelloRepo.findById(id).get();
    }

    @Override
    public List<Carrello> getAllCarrelli() {
        return carrelloRepo.findAll();
    }

    @Override
    public RicercaCarrello findCarrelliByAnno(Integer anno) {

        List<Carrello> temp = carrelloRepo.findByDatascontrino(anno.toString() + "$");
        Double tot = temp.stream().map(x -> x.getTotale()).reduce(0.00, Double::sum);
        RicercaCarrello tempresult = new RicercaCarrello(anno, temp, tot);
        return tempresult;

    }

    @Override
    public RicercaCarrello findCarrelliByDataAndTotaleRange(Integer anno, Double min, Double max) {
        List<Carrello> temp = carrelloRepo.findByDataAndTotaleRange(anno.toString() + "$", min, max);
        Double tot = temp.stream().map(x -> x.getTotale()).reduce(0.00, Double::sum);
        RicercaCarrello tempresult = new RicercaCarrello(anno, temp, tot);
        return tempresult;
    }

    @Override
    public boolean controllaBudget(Carrello carrello, Cliente cliente) {

        if (carrello.getTotale() <= cliente.getBudget()) {
            return true;
        } else {
            Double differenza = cliente.getBudget() - carrello.getTotale();
            log.error("Il cliente non può permettersi la spesa" + differenza);
            return false;
        }

    }

    @Override
    public void eliminaCarrello(Carrello carrello) {
        this.carrelloRepo.deleteById(carrello.get_id());
    }
}
