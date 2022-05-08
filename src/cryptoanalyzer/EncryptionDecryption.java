package cryptoanalyzer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncryptionDecryption {
    private static ItemMoves itemMove;

    public static void startEncryption() {
        System.out.println("------------------------------");
        System.out.println("Включен режим шифрования");
        itemMove = ItemMoves.ENCRYPTION;
        interactEncryptMenu();
    }

    public static void startDecryption() {
        System.out.println("------------------------------");
        System.out.println("Включен режим расшифровки");
        itemMove = ItemMoves.DESCRYPTION;
        interactEncryptMenu();
    }

    private static void interactEncryptMenu() {
        boolean isEncryptionWorks = true;
        while (isEncryptionWorks) {
            encryptionMenu();
            switch (InputScannerCheck.getDigitInput()) {
                case 1:
                    enterText();
                    break;
                case 2:
                    String resultFile = enterFile(0, "");
                    if (!resultFile.isEmpty()) {
                        System.out.printf("Текст записан в файл %s\n", resultFile);
                    }
                    break;
                case 0:
                    isEncryptionWorks = false;
                    break;
                default:
                    System.err.println("Других действий пока нет...");
            }
        }
    }

    private static void encryptionMenu() {
        System.out.println("------------------------------");
        System.out.println("Выберите действие (1,2 или 0)");
        System.out.println("1. Ввести текст в консоли");
        System.out.println("2. Ввести имя файла (txt)");
        System.out.println("0. Выйти в главное меню");
    }

    private static void enterText() {
        String massage = "";
        int signOfKey = 1;
        switch (itemMove) {
            case ENCRYPTION:
                massage = "Введите строку для шифрования:";
                signOfKey = 1;
                break;
            case DESCRYPTION:
                massage = "Введите строку для расшифровки:";
                signOfKey = -1;
                break;
            default:
                break;
        }

        String textForEncryption = InputScannerCheck.getString(massage);
        int keyOfEncryption = InputScannerCheck.getKeyOfEncryption() * signOfKey;

        String encryptedText = Cryptographer.getEncryptedText(textForEncryption, keyOfEncryption);

        System.out.println("Готовый текст:");
        System.out.println(encryptedText);
    }

    public static String giveValuesOfBrutforce(int keyOfCrypt, String inputFileName) {
        itemMove = ItemMoves.BRUTFORCE;
        String rewrittenFile = enterFile(keyOfCrypt, inputFileName);
        return rewrittenFile;
    }

    private static String enterFile(int keyOfCrypt, String inputFileName) {
        int signOfKey = 1;
        switch (itemMove) {
            case ENCRYPTION:
                break;
            case DESCRYPTION:
            case BRUTFORCE:
                signOfKey = -1;
                break;
        }

        if (itemMove != ItemMoves.BRUTFORCE) {
            System.out.println("Введите путь к файлу");
            inputFileName = InputScannerCheck.getLineInput();
        }

        return rewriteCryptFile(keyOfCrypt, inputFileName, signOfKey);
    }

    private static String rewriteCryptFile (int keyOfCrypt, String inputFileName, int signOfKey) {
        try (FileReader reader = new FileReader(inputFileName);
            BufferedReader buffer = new BufferedReader(reader)) {

            Path rewrittenFile = getRewrittenFile(inputFileName);

            if (itemMove != ItemMoves.BRUTFORCE) {
                keyOfCrypt = InputScannerCheck.getKeyOfEncryption();
            }

            try (FileWriter writer = new FileWriter(String.valueOf(rewrittenFile))) {
                while (buffer.ready()) {
                    String lineFromFile = buffer.readLine();
                    writer.write(Cryptographer.getEncryptedText(lineFromFile, keyOfCrypt * signOfKey));
                }
                return String.valueOf(rewrittenFile);
            }
        } catch (Exception e) {
            System.err.println("Что-то пошло не так. Может такого файла не существует?..");
            return "";
        }
    }

    private static Path getRewrittenFile(String inputFileName) throws IOException {
        String fileDirectory = Path.of(inputFileName).getParent().toString();
        String fileName = Path.of(inputFileName).getFileName().toString();

        String newFileName = fileName.substring(0, fileName.length() - 4) + "_rewritten.txt";
        Path rewrittenFile = Path.of(fileDirectory + "\\" + newFileName);

        if (!Files.exists(rewrittenFile)) {
            Files.createFile(rewrittenFile);
        }
        return rewrittenFile;
    }

}
