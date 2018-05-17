package com.applicationswp.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Statische Hilfsklasse welche MD5 Hashs generiert
 */
public final class MD5Generator {
    private MD5Generator(){

    }

    /**
     * Generiert einen MD5 Hash
     * @param input der zu hashende Text
     * @return den Hash fuer die Eingabe
     */
    public static String getMD5(String input) {
        MessageDigest m= null;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(input.getBytes(),0,input.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
