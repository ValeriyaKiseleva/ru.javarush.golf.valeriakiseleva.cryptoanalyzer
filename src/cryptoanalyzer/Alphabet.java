package cryptoanalyzer;

public class Alphabet {
    private static final String alphabetStr = "абвгдежзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";
    public static int sizeOfAlphabet = alphabetStr.length();

    public static String getAlphabetStr() {
        return alphabetStr;
    }

    public static String getNewAlphabetStr(int keyOfCrypt) {
        return makeNewAlphabetStr(keyOfCrypt);
    }

    private static String makeNewAlphabetStr(int keyOfCrypt) {
        char[] alphabetArray = alphabetStr.toCharArray();
        char[] newAlphabetArray = new char[alphabetArray.length];

        if (keyOfCrypt > 0) {
            System.arraycopy(alphabetArray, keyOfCrypt, newAlphabetArray, 0, sizeOfAlphabet - keyOfCrypt);
            System.arraycopy(alphabetArray, 0, newAlphabetArray, sizeOfAlphabet - keyOfCrypt, keyOfCrypt);

        } else {
            keyOfCrypt = Math.abs(keyOfCrypt);
            System.arraycopy(alphabetArray, sizeOfAlphabet - keyOfCrypt, newAlphabetArray, 0, keyOfCrypt);
            System.arraycopy(alphabetArray, 0, newAlphabetArray, keyOfCrypt, sizeOfAlphabet - keyOfCrypt);
        }


        return String.valueOf(newAlphabetArray);
    }



}
