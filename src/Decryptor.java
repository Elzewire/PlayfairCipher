import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Decryptor {
    public String input;
    public String output;
    public String mtrx;

    public Decryptor(String input, String output, String mtrx) {
        this.input = input;
        this.output = output;
        this.mtrx = mtrx;
    }

    public void decrypt() throws IOException {
        // Чтение из файла
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line = br.readLine();

        FileWriter w = new FileWriter(output);


        while (line != null) {
            String result = "";
            String bigramm;
            int pos = 0;

            // Дешифровка по ключу
            while (line.length() - pos > 0) {
                bigramm = line.substring(pos, pos + 2);
                pos += 2;

                result += decryptBigramm(bigramm, mtrx);
            }

            // Сохранение результата
            w.write(result + "\n");
            line = br.readLine();
        }

        br.close();
        w.close();
    }

    private String decryptBigramm(String bigramm, String mtrx) {
        String result = "";
        int x1 = mtrx.indexOf(bigramm.charAt(0)) % 5;
        int y1 = mtrx.indexOf(bigramm.charAt(0)) / 5;
        int x2 = mtrx.indexOf(bigramm.charAt(1)) % 5;
        int y2 = mtrx.indexOf(bigramm.charAt(1)) / 5;

        if (x1 == x2 || y1 == y2) {
            if (x1 == x2) {
                // Столбец
                if (y2 == 0) {
                    y2 = 5;
                }

                if (y1 == 0) {
                    y1 = 5;
                }
                result += mtrx.charAt(((y1 - 1) % 5) * 5 + x1);
                result += mtrx.charAt(((y2 - 1) % 5) * 5 + x2);
            } else {
                // Строка
                if (x2 == 0) {
                    x2 = 5;
                }

                if (x1 == 0) {
                    x1 = 5;
                }
                result += mtrx.charAt(y1 * 5 + (x1 - 1));
                result += mtrx.charAt(y2 * 5 + (x2 - 1));
            }
        } else {
            // Прямоугольник
            result += mtrx.charAt(y1 * 5 + x2);
            result += mtrx.charAt(y2 * 5 + x1);
        }

        return result;
    }
}
