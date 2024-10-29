package authservice.models;

public record PasswordResetConfirmation(String email, String token, String newPassword) {}
