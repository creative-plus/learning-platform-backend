package ro.creativeplus.learningplatformbackend.dto.auth;

import javax.validation.constraints.NotEmpty;

public class CheckTokenDto {
    @NotEmpty
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
