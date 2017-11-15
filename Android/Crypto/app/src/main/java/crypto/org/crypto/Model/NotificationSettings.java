package crypto.org.crypto.Model;

/**
 * Created by Qunfo on 15-Nov-17.
 */

public class NotificationSettings {
    private int UserID;
    private int TargetAmount;
    private boolean Push;

    public NotificationSettings(int userID, int targetAmount, boolean push) {
        UserID = userID;
        TargetAmount = targetAmount;
        Push = push;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getTargetAmount() {
        return TargetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        TargetAmount = targetAmount;
    }

    public boolean isPush() {
        return Push;
    }

    public void setPush(boolean push) {
        Push = push;
    }
}
