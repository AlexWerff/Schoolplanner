package com.applicationswp.security;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by alexwerff on 27.02.17.
 */
public class RandomStringGenerator {


    public static String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32).toUpperCase().substring(0,8);
    }
}
