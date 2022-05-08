package cryptoanalyzer;

public class Cryptographer {
    private static String encryptedText = "";

    public static String getEncryptedText(String textForEncryption, int keyOfCrypt) {
        encryptedText = "";
        encryptText(textForEncryption, keyOfCrypt);
        return encryptedText;
    }

    private static void encryptText(String textForEncryption, int keyOfCrypt) {
        char[] charForEncryption = textForEncryption.toLowerCase().toCharArray();
        encryptedText = String.valueOf(encryptCharArray(charForEncryption, keyOfCrypt));
    }

    private static char[] encryptCharArray(char[] charArrayForEncryption, int keyOfCrypt) {
        String oldAlphabet = Alphabet.getAlphabetStr();
        String newAlphabet = Alphabet.getNewAlphabetStr(keyOfCrypt);

        char[] encryptedCharArray = new char[charArrayForEncryption.length];

        for (int i = 0; i < charArrayForEncryption.length; i++) {
            int index = oldAlphabet.indexOf(charArrayForEncryption[i]);
            if (index == -1) {
                encryptedCharArray[i] = charArrayForEncryption[i];
            } else {
                encryptedCharArray[i] = newAlphabet.charAt(index);
            }
        }
        return encryptedCharArray;
    }
}
