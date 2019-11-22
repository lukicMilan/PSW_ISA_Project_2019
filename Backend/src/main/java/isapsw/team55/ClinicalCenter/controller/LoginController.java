package isapsw.team55.ClinicalCenter.controller;

import isapsw.team55.ClinicalCenter.domain.Pacijent;
import isapsw.team55.ClinicalCenter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pacijent> loginPacijent(@RequestBody Pacijent pacijent, @Context HttpServletRequest request) throws Exception {
        Pacijent ulogovanKorisnik = loginService.proveraKorisnika(pacijent.getEmail(), pacijent.getLozinka());
        if (ulogovanKorisnik != null) {
            request.getSession().setAttribute("ulogovanKorisnik", ulogovanKorisnik);
            return new ResponseEntity<Pacijent>(ulogovanKorisnik, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<Pacijent>(HttpStatus.NOT_FOUND);
        }
    }
}