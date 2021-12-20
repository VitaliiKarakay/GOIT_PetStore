package ua.goit.model.store;

public class Inventory {

    private String statusCode;
    private Long quantity;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Store{" +
                "statusCode='" + statusCode + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
