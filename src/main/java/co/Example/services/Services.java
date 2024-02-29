package co.Example.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
// import java.security.KeyPair;
import java.io.BufferedReader;
import java.io.InputStreamReader;
// import org.springframework.stereotype.Service;

@Service
public class Services {

    @Autowired
    private FideliusCLIService fideliusCLIService;
    public String generateKeyPair() {
        try {
            // Generate a sender key and private key using Fidelius CLI
            String command = "fidelius-cli generate-keys";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String senderKey = reader.readLine();
            String privateKey = reader.readLine();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return senderKey + ":" + privateKey;
            } else {
                // Handle the error
                System.err.println("Fidelius key generation command failed.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptFile(String filePath, String senderKey, String privateKey) {
        // Read the file's content
        String plaintext = readFileContent(filePath);
        
        if (plaintext != null) {
            // Encrypt the content using Fidelius
            String encryptedContent = fideliusCLIService.encryptSecret(plaintext, senderKey, privateKey);
            return encryptedContent;
        } else {
            // Handle file read error
            return null;
        }
    }

    public String decryptFile(String encryptedContent, String senderKey, String privateKey) {
        // Decrypt the content using Fidelius
        String decryptedContent = fideliusCLIService.decryptSecret(encryptedContent, privateKey);
        return decryptedContent;
    }

    // Implement a method to read the content of a file
    private String readFileContent(String filePath) {
        try {
            // Read the content of the file at 'filePath'
            // You can use standard Java file reading techniques
            // Here's a simplified example using java.nio
            // Read and return the content as a String
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Implement other methods as needed
}
