package com.bala.microservice.vault_demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;
import org.springframework.vault.core.VaultSysOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultMount;
import org.springframework.vault.support.VaultResponse;

import com.bala.microservice.vault_demo.config.VaultService;



@SpringBootApplication
public class VaultDemoApplication implements CommandLineRunner{
	
	@Autowired
    private VaultService vaultService;
	
	@Autowired
	private VaultTemplate vaultTemplate;

	public static void main(String[] args) {
		SpringApplication.run(VaultDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Write into vault
		Map<String, String> secrets = new HashMap<>();
        secrets.put("apiKey", "12345");
        secrets.put("apiSecret", "67890");
        vaultService.writeSecret("api/credentials", secrets);
		
        //Read the values written
		Map<String, Object> readSecrets = vaultService.readSecret("api/credentials");
        System.out.println("Read Secrets: " + readSecrets);
        
        // Read key-value pair already inside vault
        readSecrets = vaultService.readSecret("gs-vault-config");
        System.out.println("Read Secrets: " + readSecrets);
		
        
        // Another way to read
        VaultResponse response = vaultTemplate
                .opsForKeyValue("secret", KeyValueBackend.KV_2).get("gs-vault-config");
        System.out.println("Value of gs-vault-config");
        System.out.println("-------------------------------");
        System.out.println(response.getData().get("example.username"));
        System.out.println(response.getData().get("example.password"));
        System.out.println("-------------------------------");
        System.out.println();
        
        
        // Let's encrypt some data using the Transit backend.
        VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();

        // We need to setup transit first (assuming you didn't set up it yet).
        VaultSysOperations sysOperations = vaultTemplate.opsForSys();

        if (!sysOperations.getMounts().containsKey("transit/")) {

          sysOperations.mount("transit", VaultMount.create("transit"));

          transitOperations.createKey("foo-key");
        }

        // Encrypt a plain-text value
        String ciphertext = transitOperations.encrypt("foo-key", "Secure message");

        System.out.println("Encrypted value");
        System.out.println("-------------------------------");
        System.out.println(ciphertext);
        System.out.println("-------------------------------");
        System.out.println();

        // Decrypt

        String plaintext = transitOperations.decrypt("foo-key", ciphertext);

        System.out.println("Decrypted value");
        System.out.println("-------------------------------");
        System.out.println(plaintext);
        System.out.println("-------------------------------");
        System.out.println();
	}

}
