package cz.crypto.Crypto.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class Crypto {
    private Integer id;

    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    @NotEmpty(message = "Symbol cannot be empty.")
    private String symbol;

    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price must be positive.")
    private Double price;

    @NotNull(message = "Quantity cannot be null.")
    @Positive(message = "Quantity must be positive.")
    private Double quantity;

    public Crypto() {}

    public Crypto(Integer id, String name, String symbol, Double price, Double quantity) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    // Gettery a settery
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Crypto{id=%d, name='%s', symbol='%s', price=%.2f, quantity=%.2f}",
                id, name, symbol, price, quantity);
    }
}
