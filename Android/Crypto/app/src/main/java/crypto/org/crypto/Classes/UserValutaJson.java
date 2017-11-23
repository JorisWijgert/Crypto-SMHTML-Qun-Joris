package crypto.org.crypto.Classes;

/**
 * Created by Joris on 23-11-2017.
 */

public class UserValutaJson {
    private double amount;
    private double purchasePrice;
    private int valutaId;
    private int userId;

    public UserValutaJson(double amount, double purchasePrice, int valutaId, int userId) {
        this.amount = amount;
        this.purchasePrice = purchasePrice;
        this.valutaId = valutaId;
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getValutaId() {
        return valutaId;
    }

    public void setValutaId(int valutaId) {
        this.valutaId = valutaId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
