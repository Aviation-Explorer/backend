package authservice.models;

public record ResetPasswordRequest(String email, String token) {}
