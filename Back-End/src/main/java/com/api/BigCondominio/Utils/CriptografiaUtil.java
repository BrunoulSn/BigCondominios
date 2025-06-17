package com.api.BigCondominio.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CriptografiaUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashSenha(String senha) {
        return passwordEncoder.encode(senha);
    }


    private static final String CHAVE = "1234567890123456";
    private static final String ALGORITMO = "AES";

    public static String criptografarAES(String valor) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            SecretKeySpec keySpec = new SecretKeySpec(CHAVE.getBytes(), ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(valor.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar: " + e.getMessage());
        }
    }

    public static String descriptografarAES(String valorCriptografado) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            SecretKeySpec keySpec = new SecretKeySpec(CHAVE.getBytes(), ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(valorCriptografado));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar: " + e.getMessage());
        }
    }
}
