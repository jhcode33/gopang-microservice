package com.gopang.oauth2server.security;

import com.gopang.oauth2server.entity.Role;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.List;

public class CustomUserPrincipalDeserializer extends JsonDeserializer<CustomUserPrincipal> {

    @Override
    public CustomUserPrincipal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);

        Long id = readJsonNode(jsonNode, "id").asLong();
        Boolean enable = readJsonNode(jsonNode, "enable").asBoolean();
        String username = readJsonNode(jsonNode, "username").asText();
        String password = readJsonNode(jsonNode, "password").asText();

        List<GrantedAuthority> authorities = mapper
                .readerForListOf(GrantedAuthority.class)
                .readValue(jsonNode.get("authorities"));

        Role role = authorities.isEmpty()
                ? new Role("ROLE_USER")
                : new Role(authorities.get(0).getAuthority());

        return new CustomUserPrincipal(id, username, password, role, enable, authorities);
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
