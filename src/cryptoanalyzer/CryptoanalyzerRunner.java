package cryptoanalyzer;

import java.io.IOException;

public class CryptoanalyzerRunner {

    public static void startCryptoAnalyzer() throws IOException {
        System.out.println("Программа запущена");
        interactStartMenu();
    }

    private static void interactStartMenu() throws IOException {
        boolean isWorks = true;
        while (isWorks) {
            printInstruction();
            switch (InputScannerCheck.getDigitInput()) {
                case 1:
                    EncryptionDecryption.startEncryption();
                    break;
                case 2:
                    EncryptionDecryption.startDecryption();
                    break;
                case 3:
                    BrutForce.startBrutForce();
                    break;
                case 0:
                    isWorks = false;
                    break;
                default:
                    System.err.println("Нет такого пункта.");
            }
        }
    }

    private static void printInstruction() {
        System.out.println("Выберите режим работы:");
        System.out.println("1. Шифрование");
        System.out.println("2. Расшифровка");
        System.out.println("3. Взлом");
        System.out.println("0. Выход из программы");
    }

}
