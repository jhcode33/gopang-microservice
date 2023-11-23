package com.gopang.oauth2server.jose;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.RSAKey;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.interfaces.*;
import java.util.UUID;

public final class Jwks {

    private Jwks() {
    }

    public static RSAKey generateRsa() {
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // @formatter:off
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(Algorithm.parse("RS256"))
                .build();
        // @formatter:on
    }

    public static ECKey generateEc() {
        KeyPair keyPair = KeyGeneratorUtils.generateEcKey();
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        Curve curve = Curve.forECParameterSpec(publicKey.getParams());
        // @formatter:off
        return new ECKey.Builder(curve, publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        // @formatter:on
    }

    public static OctetSequenceKey generateSecret() {
        SecretKey secretKey = KeyGeneratorUtils.generateSecretKey();
        // @formatter:off
        return new OctetSequenceKey.Builder(secretKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        // @formatter:on
    }
}
