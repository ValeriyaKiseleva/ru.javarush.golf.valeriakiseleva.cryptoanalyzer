package cryptoanalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class BrutForce {
    private static String inputFileName;
    private static final String frequentLetters = " оеаинтсрвлкм";

    public static void startBrutForce() {
        System.out.println("------------------------------");
        System.out.println("Включен режим взлома методом BrutForce");
        runAnalyzer();
    }

    private static void runAnalyzer() {

        int[] countOfChar = readForCountText();
        int[] indexOfMaxChar = findMaxCountChar(countOfChar);

        String hackedFileName = "";
        boolean isRight = false;
        for (int index : indexOfMaxChar) {
            char[] frequentLettersArrayChars = frequentLetters.toCharArray();
            for (char letter : frequentLettersArrayChars) {
                int keyOfCrypt = findKey(index, letter);
                hackedFileName = EncryptionDecryption.giveValuesOfBrutforce(keyOfCrypt, inputFileName);
                isRight = analyzeFindingKey(hackedFileName);
                if (isRight) {
                    break;
                }
            }
            if (isRight) {
                break;
            }
        }
        if (!hackedFileName.isEmpty()) {
            System.out.println("Файл расшифрован! " + hackedFileName);
        }
    }

    //1
    private static int[] readForCountText() {
        System.out.println("Введите путь к файлу");
        inputFileName = InputScannerCheck.getLineInput();

        try (FileReader reader = new FileReader(inputFileName);
             BufferedReader buffer = new BufferedReader(reader)) {
            char[] charBuffer = new char[200];
            int count = buffer.read(charBuffer);
            int[] countOfChar = new int[Alphabet.sizeOfAlphabet];
            while (count > 0) {
                char[] charArrayForCount = Arrays.copyOf(charBuffer, count);
                countCharOfText(charArrayForCount, countOfChar);
                count = buffer.read(charBuffer);
            }
            return countOfChar;
        } catch (IOException e) {
            System.out.println("Неверное имя файла");
            return new int[1];
        }
    }

    private static void countCharOfText(char[] charArrayForCount, int[] countOfChar) {
        for (char charOfText : charArrayForCount) {
            int index = Alphabet.getAlphabetStr().indexOf(charOfText);
            if (index > 0) {
                countOfChar[index] += 1;
            }
        }
    }

    //2
    private static int[] findMaxCountChar (int[] countedChar) {
        int[] indexOfMaxChar = new int[2];
        int[] copyCountedChar = Arrays.copyOf(countedChar, countedChar.length);
        Arrays.sort(copyCountedChar);
        int countOfArrayLength = 0;

        for (int n = copyCountedChar.length - 1; n > 0; n--) {
            if (copyCountedChar[n] == copyCountedChar[n - 1]) {
                continue;
            }
            for (int i = 0; i < countedChar.length && countOfArrayLength < indexOfMaxChar.length; i++) {
                if (countedChar[i] == copyCountedChar[n]) {
                    indexOfMaxChar[countOfArrayLength] = i;
                    countOfArrayLength++;
                }
            }
        }
        return indexOfMaxChar;
    }

    //3
    private static int findKey(int indexOfChar, char letter) {
        int keyOfCrypt = indexOfChar - Alphabet.getAlphabetStr().indexOf(letter);
        if (keyOfCrypt < 0) {
            keyOfCrypt += Alphabet.sizeOfAlphabet;
        }
        return keyOfCrypt;
    }

    //4
    private static boolean analyzeFindingKey(String hackedFileName) {
        try (FileReader reader = new FileReader(hackedFileName);
             BufferedReader buffer = new BufferedReader(reader)) {
            char[] charBuffer = new char[200];
            int count = buffer.read(charBuffer);

            int[] countOfChar = new int[Alphabet.sizeOfAlphabet];
            int errors = 0;

            while (count > 0) {
                char[] charArrayForAnalyze = Arrays.copyOf(charBuffer, count);

                for (int i = 0; i < charArrayForAnalyze.length; i++) {
                    if (charArrayForAnalyze[i] == ',') {
                        if (i != charArrayForAnalyze.length - 1 && charArrayForAnalyze[i + 1] != ' ') {
                            errors++;
                        }
                    }
                }
                countCharOfText(charArrayForAnalyze, countOfChar);
                count = buffer.read(charBuffer);
            }

            int errorsOfFrequency = getErrorsOfFrequency(countOfChar);

            if (errors < 30 && errorsOfFrequency < 5) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Неверное имя файла");
            return false;
        }
    }

    private static int getErrorsOfFrequency(int[] countOfChar) {
        int errorsOfFrequency = 0;
        int[] countofFrequentLetters = new int[frequentLetters.length()];

        for (int i = 0; i < frequentLetters.length(); i++) {
            int indexLetter = Alphabet.getAlphabetStr().indexOf(frequentLetters.charAt(i));
            countofFrequentLetters[i] = countOfChar[indexLetter];
        }

        for (int i = 0; i < frequentLetters.length(); i++) {
            int tempError = 0;
            for (int j = i + 1; j < frequentLetters.length(); j++) {
                if (i != frequentLetters.length() - 1 && countofFrequentLetters[i] < countofFrequentLetters[j]) {
                    tempError++;
                }
            }
            if (tempError > 0) {
                errorsOfFrequency++;
            }
        }
        return errorsOfFrequency;
    }
}
