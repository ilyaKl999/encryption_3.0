import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)){
            System.out.println("Вам нужно расшифровать файл?(YES или NO)");
            String answer_user1 = scanner.nextLine();
            System.out.println("Вам нужно ЗАШИФРОВАТЬ файл?(YES или NO)");
            String answer_user2 = scanner.nextLine();
            if (answer_user1.equals("NO") & answer_user2.equals("NO")){System.out.println("ИДИ НАХУЙ ОТСУДОВА ТОГДА Я БОЛЬШЕ НИЧЕ И НЕ УМЕЮ");return;}
            System.out.println("Введите абсолютный адрес файла для применения выбраного действия (файл-пкм-свойства-безопасность) "+"\n"+"" +
                    "Расширение должно быть TXT и в пути к файлу разрешение не удаляется а оставляйте");
            String adr_read = scanner.nextLine();
            String result = Files.readString(Path.of(adr_read));
            if (answer_user1.equals("YES")){
                Encryption encryption = new Encryption(result);
                String get_File = encryption.method_Decryption_File();
                File myFile = new File("get_File_myfile_Decryption.txt");
                FileOutputStream outputStream = new FileOutputStream(myFile);
                byte[] buffer = get_File.getBytes();
                outputStream.write(buffer);
                outputStream.close();
                System.out.println("ГОТОВО НАХУЙ");
            }else if (answer_user2.equals("YES")){

                Encryption encryption = new Encryption(result);
                String get_File = encryption.method_Encryption();
                File myFile = new File("get_File_myfile_encryption.txt");
                FileOutputStream outputStream = new FileOutputStream(myFile);
                byte[] buffer = get_File.getBytes();
                outputStream.write(buffer);
                outputStream.close();
                System.out.println("ГОТОВО НАХУЙ");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    static class Encryption {
        private static int getRandom(int min, int max) {
            int range = (max - min) + 1;
            int random = (int) ((range * Math.random()) + min);
            return random;
        }

        private static final int CONST = 20;
        private String text;
        private static final String KEY_SEPARATOR = ":_<";
        private static final String SEPARATOR = "'@>";

        public String getText() {
            return text;
        }

        public Encryption(String text) {
            this.text = text;
        }

        public String method_Encryption() {
            if (text.length()<20){return new String("Ограничение по длине текста- минимум 20 символов");}
            byte key_1, key_2, key_3;
            key_1 = (byte) getRandom(1, 9);
            key_2 = (byte) getRandom(10, 99);
            key_3 = (byte) getRandom(100, 127);
            StringBuilder secret_file = new StringBuilder();
            byte counter = 1;
            secret_file = secret_file.append(KEY_SEPARATOR + SEPARATOR + key_1 +
                    SEPARATOR + key_2 + SEPARATOR + key_3 + SEPARATOR + KEY_SEPARATOR);
            for (int i = 0; i < text.length(); i++) {
                if (i % CONST == 0) {
                    counter++;
                }
                if (counter == 1) {
                    secret_file = secret_file.append((char) (text.charAt(i) + key_1));

                } else if (counter == 2) {
                    secret_file = secret_file.append((char) (text.charAt(i) + key_2));
                } else if (counter == 3) {
                    secret_file = secret_file.append((char) (text.charAt(i) + key_3));
                }
                counter=1;
            }
            return secret_file.toString();
        }
        public  String method_Decryption_File() {
            String [] find_key= text.split(KEY_SEPARATOR);
            if (find_key.length<2){return new String("В тексте не найдено ключей.Расшифровка невозможна"); }
            String [] get_Key = text.split(SEPARATOR);

            int key_1,key_2,key_3;
            key_1= Integer.parseInt(get_Key[1]);
            key_2= Integer.parseInt(get_Key[2]);
            key_3= Integer.parseInt(get_Key[3]);
            int counter= 1 ;
            String a = text.substring(24);
            StringBuilder secret_file = new StringBuilder();
            for (int i = 0; i < a.length(); i++) {
                if (i % CONST == 0) {
                    counter++;
                }
                if (counter == 1) {
                    secret_file = secret_file.append((char) (a.charAt(i) - key_1));

                } else if (counter == 2) {
                    secret_file = secret_file.append((char) (a.charAt(i) - key_2));
                } else if (counter == 3) {
                    secret_file = secret_file.append((char) (a.charAt(i) - key_3));
                }
                counter=1;
            }
            return secret_file.toString();

        }

    }
}