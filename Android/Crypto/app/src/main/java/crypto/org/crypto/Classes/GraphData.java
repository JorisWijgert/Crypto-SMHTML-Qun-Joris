package crypto.org.crypto.Classes;

/**
 * Created by Qunfo on 15-Nov-17.
 * Id : 0
 * Low : 6562.85009765625
 * High : 6622.93994140625
 * Open : 6564.6201171875
 * Close : 6588.6201171875
 * TimeStamp : 1510639200
 */
public class GraphData {
        private int Id;
        private double Low;
        private double High;
        private double Open;
        private double Close;
        private int TimeStamp;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public double getLow() {
            return Low;
        }

        public void setLow(double Low) {
            this.Low = Low;
        }

        public double getHigh() {
            return High;
        }

        public void setHigh(double High) {
            this.High = High;
        }

        public double getOpen() {
            return Open;
        }

        public void setOpen(double Open) {
            this.Open = Open;
        }

        public double getClose() {
            return Close;
        }

        public void setClose(double Close) {
            this.Close = Close;
        }

        public int getTimeStamp() {
            return TimeStamp;
        }

        public void setTimeStamp(int TimeStamp) {
            this.TimeStamp = TimeStamp;
        }
}
