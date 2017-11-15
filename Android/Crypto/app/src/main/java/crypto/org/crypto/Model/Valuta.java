package crypto.org.crypto.Model;

/**
 * Created by Qunfo on 15-Nov-17.
 */

/**
 * Id : 1
 * Name : BitCoin
 * ShortName : BTC
 * CurrentPrice : 12.5
 */
public class Valuta {
        private int Id;
        private String Name;
        private String ShortName;
        private double CurrentPrice;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getShortName() {
            return ShortName;
        }

        public void setShortName(String ShortName) {
            this.ShortName = ShortName;
        }

        public double getCurrentPrice() {
            return CurrentPrice;
        }

        public void setCurrentPrice(double CurrentPrice) {
            this.CurrentPrice = CurrentPrice;
        }
}



