package isapsw.team55.ClinicalCenter.controller;

import isapsw.team55.ClinicalCenter.domain.Korisnik;
import isapsw.team55.ClinicalCenter.domain.Pacijent;
import isapsw.team55.ClinicalCenter.domain.Pregled;
import isapsw.team55.ClinicalCenter.dto.PacijentDTO;
import isapsw.team55.ClinicalCenter.service.PacijentService;
import isapsw.team55.ClinicalCenter.service.PregledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/pacijent")
public class PacijentController {

    @Autowired
    private PacijentService pacijentService;

    @Autowired
    PregledService pregledService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<PacijentDTO>> getAllPacijenti() {
        List<Pacijent> pacijenti = pacijentService.findAll();
        List<PacijentDTO> pacijentDTO = new ArrayList<>();
        for (Pacijent s : pacijenti) {
            pacijentDTO.add(new PacijentDTO(s));
        }

        return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacijentDTO> getPacijent(@PathVariable Long id) {
        Pacijent p = pacijentService.findOne(id);

        if(p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return  new ResponseEntity<>(new PacijentDTO(p), HttpStatus.OK);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pacijent> updatePacijent(@RequestBody Pacijent pacijent) throws Exception{
        Pacijent p = pacijentService.update(pacijent);
        if(p != null) {
            return new ResponseEntity<Pacijent>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<Pacijent>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/ulogovanKorisnik", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pacijent> getKorisnik(@Context HttpServletRequest request) {
        Korisnik korisnik = (Korisnik) request.getSession().getAttribute("ulogovanKorisnik");
        System.out.println("ID KORISNIKA JE: " + korisnik.getId());
        Pacijent p = pacijentService.findOne(korisnik.getId());

        if(p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity(p, HttpStatus.OK);
    }

    @GetMapping(value = "/allFromKlinika/{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PacijentDTO>> getAllFromKlinika(@PathVariable Long idKlinike) {
        List<Pregled> pregledList = pregledService.findAllByKlinikaId(idKlinike);

        List<PacijentDTO> pacijentDTOList = new ArrayList<>();

        for (Pregled pregled :
                pregledList) {
            if(pregled.isRezervisan()) {
                pacijentDTOList.add(new PacijentDTO(pregled.getPacijent()));
            }
        }

        return new ResponseEntity<List<PacijentDTO>>(pacijentDTOList, HttpStatus.OK);
    }
}
