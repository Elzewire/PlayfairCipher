import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Encryptor {
    public String input;
    public String output;
    public String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    public char [] linearMtrx;
    public char [][] mtrx;

    public Encryptor(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public void encrypt() throws IOException {
        // Чтение из файла
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line = br.readLine();

        FileWriter w = new FileWriter(output);

        // Выбор ключевого слова
        if (line != null) {
            String keyword = line.split(" ")[0].toUpperCase();
            keyword = keyword.replaceAll("[^A-Z]+", "");

            // Создание матрицы
            linearMtrx = new char[25];
            mtrx = new char[5][5];
            int k = 0;

            String aCopy = alphabet;

            for (int j = 0; j < keyword.length(); j++) {
                if (aCopy.contains(keyword.substring(j, j + 1))) {
                    aCopy = aCopy.replace(keyword.substring(j, j + 1), "");
                    linearMtrx[k] = keyword.charAt(j);
                    System.out.println(linearMtrx[k]);
                    k++;
                }
            }

            for (int i = k; i < 25; i++) {
                linearMtrx[i] = aCopy.charAt(0);
                aCopy = aCopy.substring(1);
            }

            String mtrx  = new String(linearMtrx);
            System.out.println(mtrx);

            while (line != null) {
                // Обработка строки
                String result = "";
                line = line.replace(" ", "");
                line = line.toUpperCase();
                line = line.replaceAll("[^A-Z]+", "");
                line = line.replace("J", "I");

                // Шифрование
                int pos = 0;
                String bigramm;
                while (line.length() - pos >= 1) {
                    if (line.length() - pos == 1 || line.charAt(pos) == line.charAt(pos + 1)) {
                        bigramm = line.charAt(pos) + "X";
                        pos++;
                    } else {
                        bigramm = line.substring(pos, pos + 2);
                        pos += 2;
                    }

                    result += ecryptBigramm(bigramm, mtrx);
                }

                // Сохранение результата
                w.write(result + "\n");

                line = br.readLine();
            }
        }

        br.close();
        w.close();
    }

    private String ecryptBigramm(String bigramm, String mtrx) {
        String result = "";
        int x1 = mtrx.indexOf(bigramm.charAt(0)) % 5;
        int y1 = mtrx.indexOf(bigramm.charAt(0)) / 5;
        int x2 = mtrx.indexOf(bigramm.charAt(1)) % 5;
        int y2 = mtrx.indexOf(bigramm.charAt(1)) / 5;

        if (x1 == x2 || y1 == y2) {
            if (x1 == x2) {
                // Столбец
                result += mtrx.charAt(((y1 + 1) % 5) * 5 + x1);
                result += mtrx.charAt(((y2 + 1) % 5) * 5 + x2);
            } else {
                // Строка
                result += mtrx.charAt(y1 * 5 + (x1 + 1) % 5);
                result += mtrx.charAt(y2 * 5 + (x2 + 1) % 5);
            }
        } else {
            // Прямоугольник
            result += mtrx.charAt(y1 * 5 + x2);
            result += mtrx.charAt(y2 * 5 + x1);
        }

        return result;
    }

    public String getMtrx() {
        return new String(linearMtrx);
    }
}
