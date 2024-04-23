package helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathHelper {

    public static double round(double amount, int decimalPlaces) {
        BigDecimal bd = new BigDecimal(Double.toString(amount));
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
