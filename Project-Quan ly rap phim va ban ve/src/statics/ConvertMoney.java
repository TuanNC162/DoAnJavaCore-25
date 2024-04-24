package statics;

import java.text.NumberFormat;
import java.util.Locale;

public class ConvertMoney {
    public static String formatPriceVND(double price) {
        NumberFormat vndFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return vndFormat.format(price)+"₫";
    }
}
