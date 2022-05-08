package cryptoanalyzer;

import java.math.BigInteger;
import java.util.Scanner;

public class InputScannerCheck {
    private static final Scanner scanner = new Scanner(System.in);

    private static boolean isDigit(String inputString) throws NumberFormatException {
        try {
            Integer.parseInt(inputString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getLineInput() {
        return scanner.nextLine();
    }

    public static int getDigitInput() {
        String inputString = scanner.nextLine();
        if (inputString != null && isDigit(inputString)) {
            System.out.println(inputString);
            return Integer.parseInt(inputString);
        } else {
            return -1;
        }
    }

    public static String getString(String massage) {
        boolean isNotEmptyString = false;
        String textForCryptoAnalyzer = null;
        while (!isNotEmptyString) {
            System.out.println(massage);
            textForCryptoAnalyzer = scanner.nextLine();
            if (textForCryptoAnalyzer != null) {
                isNotEmptyString = true;
            }
        }
        return textForCryptoAnalyzer;
    }

    public static int getKeyOfEncryption() {
        boolean isTrueDigit = false;
        int keyOfCrypt = 0;
        while (!isTrueDigit) {
            System.out.println("Введите ключ шифрования (целое число)");
            String inputString = scanner.nextLine();
            if (inputString != null && isDigit(inputString)){
                keyOfCrypt = countKeyInt(inputString);
                isTrueDigit = true;
            } else if (inputString != null && isBigInteger(inputString)) {
                keyOfCrypt = countKeyBigint(inputString);
                isTrueDigit = true;
            } else {
                System.err.println("Неверный формат ключа");
            }

            if (keyOfCrypt == 0) {
                keyOfCrypt = Alphabet.sizeOfAlphabet - 1;
            }
        }
        return keyOfCrypt;
    }

    private static int countKeyInt(String inputString) {
        int inputKey = Math.abs(Integer.parseInt(inputString));
        return inputKey % (Alphabet.sizeOfAlphabet - 1);
    }

    private static int countKeyBigint(String inputString) {
        BigInteger inputKey = new BigInteger(inputString).abs();
        inputKey = inputKey.mod(BigInteger.valueOf(Alphabet.sizeOfAlphabet - 1));
        return inputKey.intValue();
    }

    private static boolean isBigInteger (String inputString) {
        char[] inputStringChars = inputString.toCharArray();
        for (char stringChar : inputStringChars) {
            if (stringChar < 48 || stringChar > 57) {
                return false;
            }
        }
        return true;
    }

}
