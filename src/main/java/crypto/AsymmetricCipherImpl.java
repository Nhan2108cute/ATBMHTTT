package crypto;

import org.bouncycastle.jce.spec.IESParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.List;

public final class AsymmetricCipherImpl {

    private AsymmetricCipherImpl() { }

    public static byte[] encrypt(String algorithm, byte[] payload, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(payload);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(String algorithm,
                                 byte[] payload,
                                 Key key,
                                 byte[] derivation,
                                 byte[] encoding,
                                 byte[] nonce) {

        try {
            Cipher cipher = Cipher.getInstance(algorithm, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IESParameterSpec(derivation, encoding, 128, 128, nonce));

            return cipher.doFinal(payload);
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchPaddingException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

    }

    public static byte[] decrypt(String algorithm, byte[] payload, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, "BC");
            cipher.init(Cipher.DECRYPT_MODE, key);

            return cipher.doFinal(payload);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(String algorithm,
                                 byte[] payload,
                                 Key key,
                                 byte[] derivation,
                                 byte[] encoding,
                                 byte[] nonce) {

        try {
            Cipher cipher = Cipher.getInstance(algorithm, "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, new IESParameterSpec(derivation, encoding, 128, 128, nonce));

            return cipher.doFinal(payload);
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchPaddingException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

    }

    public static KeyPair generateKeyPair(String algorithm, String option) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm, "BC");

            if ("EC".equals(algorithm))
                keyGen.initialize(new ECGenParameterSpec(option));
            else
                keyGen.initialize(Integer.parseInt(option));

            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAvailableKeyOptions(String algorithm) {
        return switch (algorithm) {
            case "DHIES", "DHIESWITHDESEDE-CBC", "DHIESwithAES-CBC", "ELGAMAL", "IES", "IESWITHDESEDE-CBC",
                 "IESwithAES-CBC" -> List.of("512", "1024", "2048");
            case "RSA" -> List.of("512", "1024", "2048", "3072", "4096");
            case "ECIES", "ECIESwithAES-CBC", "ECIESwithDESEDE-CBC", "ECIESwithSHA1", "ECIESwithSHA1andAES-CBC",
                 "ECIESwithSHA1andDESEDE-CBC", "ECIESwithSHA256", "ECIESwithSHA256andAES-CBC",
                 "ECIESwithSHA256andDESEDE-CBC", "ECIESwithSHA384", "ECIESwithSHA384andAES-CBC",
                 "ECIESwithSHA384andDESEDE-CBC", "ECIESwithSHA512", "ECIESwithSHA512andAES-CBC",
                 "ECIESwithSHA512andDESEDE-CBC" -> List.of("secp256r1", "secp384r1", "secp521r1");
            default -> throw new RuntimeException("Unsupported algorithm: " + algorithm);
        };
    }

    public static String getProperKeyGenAlgorithm(String algorithm) {
        return switch (algorithm) {
            case "DHIES", "DHIESWITHDESEDE-CBC", "DHIESwithAES-CBC" -> "DH";
            case "ECIES", "ECIESwithAES-CBC", "ECIESwithDESEDE-CBC", "ECIESwithSHA1", "ECIESwithSHA1andAES-CBC",
                 "ECIESwithSHA1andDESEDE-CBC", "ECIESwithSHA256", "ECIESwithSHA256andAES-CBC",
                 "ECIESwithSHA256andDESEDE-CBC", "ECIESwithSHA384", "ECIESwithSHA384andAES-CBC",
                 "ECIESwithSHA384andDESEDE-CBC", "ECIESwithSHA512", "ECIESwithSHA512andAES-CBC",
                 "ECIESwithSHA512andDESEDE-CBC" -> "EC";
            case "ELGAMAL", "ELGAMAL/PKCS1" -> "ElGamal";
            case "IES", "IESWITHDESEDE-CBC", "IESwithAES-CBC" -> "DH";
            case "RSA", "RSA/1", "RSA/2", "RSA/ISO9796-1", "RSA/OAEP", "RSA/PKCS1", "RSA/RAW" -> "RSA";
            default -> throw new RuntimeException("Unsupported algorithm: " + algorithm);
        };
    }

}
