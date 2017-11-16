package crypto.org.crypto.Classes;

/**
 * Created by Qunfo on 15-Nov-17.
 */

/**
 * Valuta : {"Id":1,"Name":"BitCoin","ShortName":"BTC","CurrentPrice":12.5}
 * Id : 1
 * Amount : 18
 * PurchasePrice : 19.99
 */
public class UserValuta {
        private Valuta Valuta;
        private int Id;
        private double Amount;
        private double PurchasePrice;

        public Valuta getValuta() {
            return Valuta;
        }

        public void setValuta(Valuta Valuta) {
            this.Valuta = Valuta;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public double getPurchasePrice() {
            return PurchasePrice;
        }

        public void setPurchasePrice(double PurchasePrice) {
            this.PurchasePrice = PurchasePrice;
        }
}
