package com.gopang.gatewayserver.filters.login;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

//@Component
public class LoginConfig {
    private final String client_id = "demo-client";
    private final String client_secret = "demo-client-secret";
    private final String response_type = "code";
    private final String redirect_uri = "http://localhost:8072/login/callback";
    private final String scope = "openid";
    private final String state = "state";
    private final String code_verifier = "qPsH306-ZDDaOE8DFzVn05TkN3ZZoVmI_6x4LsVglQI";
    private final String code_challenge = "QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8";
    private final String code_challenge_method = "S256";

    private String generateCodeVerifier() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }

    private static String generateCodeChallenge(String codeVerifier) {
        byte[] codeVerifierBytes = codeVerifier.getBytes();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifierBytes);
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getResponse_type() {
        return response_type;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public String getScope() {
        return scope;
    }

    public String getState() {
        return state;
    }

    public String getCode_challenge() {
        return code_challenge;
    }

    public String getCode_challenge_method() {
        return code_challenge_method;
    }

    public String getCode_verifier() {
        return code_verifier;
    }
}
