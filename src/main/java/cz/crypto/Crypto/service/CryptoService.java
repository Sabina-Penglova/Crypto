package cz.crypto.Crypto.service;

import cz.crypto.Crypto.model.Crypto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private List<Crypto> cryptos = new ArrayList<>();

    public void addCrypto(Crypto crypto) {
        crypto.setId(cryptos.stream().mapToInt(Crypto::getId).max().orElse(0) + 1);
        cryptos.add(crypto);
    }

    public List<Crypto> getAllCryptos(String sortBy) {
        Comparator<Crypto> comparator = Comparator.comparing(Crypto::getName);
        if ("price".equals(sortBy)) {
            comparator = Comparator.comparing(Crypto::getPrice);
        } else if ("quantity".equals(sortBy)) {
            comparator = Comparator.comparing(Crypto::getQuantity);
        }
        return cryptos.stream().sorted(comparator).collect(Collectors.toList());
    }

    public Crypto getCryptoById(Integer id) {
        return cryptos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Crypto not found with id: " + id));
    }

    public void updateCrypto(Integer id, Crypto cryptoDetails) {
        Crypto crypto = getCryptoById(id);
        crypto.setName(cryptoDetails.getName());
        crypto.setSymbol(cryptoDetails.getSymbol());
        crypto.setPrice(cryptoDetails.getPrice());
        crypto.setQuantity(cryptoDetails.getQuantity());
    }

    public double calculatePortfolioValue() {
        return cryptos.stream()
                .mapToDouble(c -> c.getPrice() * c.getQuantity())
                .sum();
    }

    // Custom exception for better error handling
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}