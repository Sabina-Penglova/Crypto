package cz.crypto.Crypto.service;

import cz.crypto.Crypto.model.Crypto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CryptoService {

    private final List<Crypto> cryptos = new ArrayList<>();

    // Přidání nové kryptoměny
    public void addCrypto(Crypto crypto) {
        if (crypto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        if (cryptos.stream().anyMatch(c -> c.getId().equals(crypto.getId()))) {
            throw new IllegalArgumentException("Crypto with ID " + crypto.getId() + " already exists.");
        }

        cryptos.add(crypto);
    }

    // Získání všech kryptoměn s možností řazení
    public List<Crypto> getAllCryptos(String sortBy) {
        Comparator<Crypto> comparator = Comparator.comparing(Crypto::getName);
        if ("price".equals(sortBy)) {
            comparator = Comparator.comparing(Crypto::getPrice);
        } else if ("quantity".equals(sortBy)) {
            comparator = Comparator.comparing(Crypto::getQuantity);
        }
        return cryptos.stream().sorted(comparator).collect(Collectors.toList());
    }

    // Získání kryptoměny podle ID
    public Crypto getCryptoById(Integer id) {
        return cryptos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Crypto not found with id: " + id));
    }

    // Aktualizace kryptoměny
    public void updateCrypto(Integer id, Crypto cryptoDetails) {
        Crypto crypto = getCryptoById(id);
        crypto.setName(cryptoDetails.getName());
        crypto.setSymbol(cryptoDetails.getSymbol());
        crypto.setPrice(cryptoDetails.getPrice());
        crypto.setQuantity(cryptoDetails.getQuantity());
    }

    // Výpočet celkové hodnoty portfolia
    public BigDecimal calculatePortfolioValue() {
        return cryptos.stream()
                .map(c -> BigDecimal.valueOf(c.getPrice()).multiply(BigDecimal.valueOf(c.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Výjimka pro nenalezené zdroje
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
