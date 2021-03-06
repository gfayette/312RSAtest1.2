package PGP;

import java.math.BigInteger;

/**
 * PGP Project
 * PGP Class
 *
 * This class implements PGP using RSA and bitwise XOR encryption. The main method creates two RSA objects "Alice"
 * and "Bob", which each generate their own set of key pairs given a minimum prime number value and a minimum public
 * key value. Alice and Bob then use RSA to exchange a shared key for bitwise XOR encryption. Once a shared key has
 * been established, Alice and Bob use bitwise XOR to encrypt/decrypt messages and RSA to generate
 * digital signatures/verify each other's messages.
 *
 * @author George Fayette
 **/

public class PGP {

    public void exchangeKey(RSA alice, RSA bob, String sharedKey) {
        System.out.println("-----------Exchanging key-----------");
        System.out.println();

        alice.setSharedKey(sharedKey);

        BigInteger cipherTextRSA = alice.encryptStringRSA(sharedKey, bob.getPublicKey(), bob.getModulus());
        BigInteger signature = alice.signMessage(cipherTextRSA);

        bob.verifySignature(signature, cipherTextRSA, alice.getPublicKey(), alice.getModulus());
        bob.decryptStringRSA(cipherTextRSA);

    }

    public void sendMessage(RSA alice, RSA bob, String message) {
        System.out.println("-----------Exchanging message-----------");
        System.out.println();

        BigInteger cipherTextXOR = alice.encryptStringXOR(message);
        BigInteger signature = alice.signMessage(cipherTextXOR);

        bob.verifySignature(signature, cipherTextXOR, alice.getPublicKey(), alice.getModulus());
        bob.decryptStringXOR(cipherTextXOR);

    }

    public static void main(String[] args) {

        System.out.println("-----------Creating Alice and " + "Bob-----------");
        System.out.println();

        RSA alice = new RSA("Alice");
        RSA bob = new RSA("Bob");
        alice.keysFromMinVal(new BigInteger("12345678901234"), new BigInteger("1234567894"));
        bob.keysFromMinVal(new BigInteger("2234567890123"), new BigInteger("123456789"));
        String sharedKey = "Shared key";


        PGP p = new PGP();
        p.exchangeKey(alice, bob, sharedKey);

        String message = "Message 1";
        p.sendMessage(alice, bob, message);

        message = "Message 2";
        p.sendMessage(bob, alice, message);
    }
}
