package co.Example.services;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.stereotype.Service;




@Service
public class FideliusCLIService {

    public String encryptSecret(String plaintext, String senderKey, String privateKey) {
        try {
            // Encrypt plaintext using Fidelius CLI with senderKey and privateKey
            String command = "fidelius-cli encrypt -s " + plaintext + " -k " + senderKey + " -p " + privateKey;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                // Handle the error
                System.err.println("Fidelius CLI encryption command failed.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptSecret(String ciphertext, String privateKey) {
        try {
            // Decrypt ciphertext using Fidelius CLI with privateKey
            String command = "fidelius-cli decrypt -s " + ciphertext + " -p " + privateKey;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                // Handle the error
                System.err.println("Fidelius CLI decryption command failed.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
