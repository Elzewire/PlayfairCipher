import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Encryptor e = new Encryptor("data", "encrypted");
        e.encrypt();

        Decryptor d = new Decryptor("encrypted", "decrypted", e.getMtrx());
        d.decrypt();
    }
}
