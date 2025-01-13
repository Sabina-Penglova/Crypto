package cz.crypto.Crypto.controller;

import cz.crypto.Crypto.model.Crypto;
import cz.crypto.Crypto.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @PostMapping
    public ResponseEntity<Crypto> addCrypto(@RequestBody Crypto crypto) {
        cryptoService.addCrypto(crypto);
        return new ResponseEntity<>(crypto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Crypto>> getAllCryptos(@RequestParam(required = false) String sort) {
        return new ResponseEntity<>(cryptoService.getAllCryptos(sort), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crypto> getCryptoById(@PathVariable Integer id) {
        return new ResponseEntity<>(cryptoService.getCryptoById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crypto> updateCrypto(@PathVariable Integer id, @RequestBody Crypto cryptoDetails) {
        cryptoService.updateCrypto(id, cryptoDetails);
        Crypto updatedCrypto = cryptoService.getCryptoById(id);
        return new ResponseEntity<>(updatedCrypto, HttpStatus.OK);
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<Double> getPortfolioValue() {
        return new ResponseEntity<>(cryptoService.calculatePortfolioValue(), HttpStatus.OK);
    }
}