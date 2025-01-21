package cz.crypto.Crypto.controller;

import cz.crypto.Crypto.model.Crypto;
import cz.crypto.Crypto.service.CryptoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    // Přidání nové kryptoměny
    @PostMapping
    public ResponseEntity<Object> addCrypto(@Valid @RequestBody Crypto crypto) {
        try {
            cryptoService.addCrypto(crypto);
            Crypto savedCrypto = cryptoService.getCryptoById(crypto.getId());
            return new ResponseEntity<>(savedCrypto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    // Získání všech kryptoměn s volitelným řazením
    @GetMapping
    public ResponseEntity<List<Crypto>> getAllCryptos(@RequestParam(required = false) String sort) {
        return new ResponseEntity<>(cryptoService.getAllCryptos(sort), HttpStatus.OK);
    }

    // Získání detailu kryptoměny podle ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCryptoById(@PathVariable Integer id) {
        try {
            Crypto crypto = cryptoService.getCryptoById(id);
            return new ResponseEntity<>(crypto, HttpStatus.OK);
        } catch (CryptoService.ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Aktualizace kryptoměny podle ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCrypto(@PathVariable Integer id, @Valid @RequestBody Crypto cryptoDetails) {
        try {
            cryptoService.updateCrypto(id, cryptoDetails);
            Crypto updatedCrypto = cryptoService.getCryptoById(id);
            return new ResponseEntity<>(updatedCrypto, HttpStatus.OK);
        } catch (CryptoService.ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Výpočet celkové hodnoty portfolia
    @GetMapping("/portfolio-value")
    public ResponseEntity<BigDecimal> getPortfolioValue() {
        return new ResponseEntity<>(cryptoService.calculatePortfolioValue(), HttpStatus.OK);
    }
}
