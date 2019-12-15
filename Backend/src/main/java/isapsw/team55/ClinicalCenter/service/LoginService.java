package isapsw.team55.ClinicalCenter.service;

import isapsw.team55.ClinicalCenter.domain.Korisnik;
import isapsw.team55.ClinicalCenter.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    public Korisnik proveraKorisnika(String email, String lozinka) {
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        if (korisnik.getLozinka().equals(lozinka)) {
            return korisnik;
        } else {
            return null;
        }
    }
}
