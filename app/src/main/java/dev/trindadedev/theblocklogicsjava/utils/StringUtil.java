package dev.trindadedev.theblocklogicsjava.utils;

/** Decompiled from Sketchware 1.1.13 */
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class StringUtil {
  public static String getComma(int number) {
    NumberFormat nf = NumberFormat.getInstance();
    return nf.format(number);
  }

  public static String getComma(String number) {
    return getComma(Integer.parseInt(number));
  }

  public static int removeComma(String number) {
    NumberFormat nf = NumberFormat.getInstance();
    try {
      Number num = nf.parse(number);
      int cvInt = num.intValue();
      return cvInt;
    } catch (ParseException e) {
      e.printStackTrace();
      return 0;
    }
  }

  public static int parseCurrency(String price) {
    try {
      Number nfPrice = NumberFormat.getCurrencyInstance(Locale.KOREA).parse(price);
      return nfPrice.intValue();
    } catch (ParseException e) {
      return -1;
    }
  }

  public static String formatCurrency(int price) {
    NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);
    numFormat.setMinimumFractionDigits(0);
    numFormat.setMaximumFractionDigits(0);
    String nfPrice = numFormat.format(price);
    return nfPrice;
  }

  public static String clipCurrencyCode(String price) {
    char[] temp = price.toCharArray();
    for (char c : temp) {
      if (c >= '0' && c <= '9') {
        String curCode = "" + c;
        return curCode;
      }
    }
    return "";
  }

  public String printStackTraceToString(Throwable e) {
    StringBuffer sb = new StringBuffer();
    sb.append(e.getMessage()).append(e.toString()).append("\n");
    try {
      StackTraceElement[] element = e.getStackTrace();
      for (StackTraceElement stackTraceElement : element) {
        sb.append("\tat ").append(stackTraceElement.toString()).append("\n");
      }
      return sb.toString();
    } catch (Exception e2) {
      return e.toString();
    }
  }

  public static String escape(String s) {
    return s.replaceAll("/[\\%@]/g", "\\$&");
  }

  public static String shortenNumber(int num) {
    if (num >= 1000.0f && num < 1000000.0f) {
      float value = num / 1000.0f;
      DecimalFormat formatter = new DecimalFormat("#.#K");
      String shorten = formatter.format(value);
      return shorten;
    } else if (num >= 1000000.0f && num < 1.0E9f) {
      float value2 = num / 1000000.0f;
      DecimalFormat formatter2 = new DecimalFormat("#.#M");
      String shorten2 = formatter2.format(value2);
      return shorten2;
    } else if (num >= 1.0E9f && num < 1.0E12f) {
      float value3 = num / 1.0E9f;
      DecimalFormat formatter3 = new DecimalFormat("#.#G");
      String shorten3 = formatter3.format(value3);
      return shorten3;
    } else {
      String shorten4 = String.valueOf(num);
      return shorten4;
    }
  }

  public static void copyToClipboard(Context context, String label, String text) {
    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
    ClipData data = ClipData.newPlainText(label, text);
    clipboardManager.setPrimaryClip(data);
  }

  public static String encryptSha256(String str) {
    try {
      MessageDigest sh = MessageDigest.getInstance("SHA-256");
      sh.update(str.getBytes());
      byte[] byteData = sh.digest();
      StringBuffer sb = new StringBuffer();
      for (byte b : byteData) {
        sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
      }
      String result = sb.toString();
      return result;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String unescape(String s) {
    String result = "";
    int i = 0;
    while (i < s.length()) {
      char ch = s.charAt(i);
      if (ch == '\\') {
        result = result + s.charAt(i + 1);
        i++;
      } else {
        result = result + ch;
      }
      i++;
    }
    return result;
  }

  public static ArrayList<String> tokenize(String s) {
    ArrayList<String> result = new ArrayList<>();
    TokenParser tp = new TokenParser(s);
    while (!tp.atEnd()) {
      String token = tp.nextToken();
      if (token.length() > 0) {
        result.add(token);
      }
    }
    return result;
  }
}
