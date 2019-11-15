package isapsw.team55.ClinicalCenter.service;

import isapsw.team55.ClinicalCenter.domain.Pacijent;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import isapsw.team55.ClinicalCenter.repository.PacijentRepository;

import java.util.List;

@Service
public class PacijentService {

    @Autowired
    private PacijentRepository pacijentRepository;

    public Pacijent findOne(Long id) {
        return pacijentRepository.findById(id).orElseGet(null);
    }

    public List<Pacijent> findAll() {
        return pacijentRepository.findAll();
    }

    public Page<Pacijent> findAll(Pageable page) {
        return pacijentRepository.findAll(page);
    }

    public Pacijent save(Pacijent pacijent) {
        return pacijentRepository.save(pacijent);
    }

    public void remove(Long id) {
        pacijentRepository.deleteById(id);
    }

    public Pacijent findByEmail(String email) {
        return pacijentRepository.findByEmail(email);
    }

    public List<Pacijent> findByPrezime(String prezime) {
        return pacijentRepository.findAllByPrezime(prezime);
    }

    public List<Pacijent> findByImeAndPrezime(String ime, String prezime) {
        return pacijentRepository.findByImeAndPrezimeAllIgnoringCase(ime, prezime);
    }


}
