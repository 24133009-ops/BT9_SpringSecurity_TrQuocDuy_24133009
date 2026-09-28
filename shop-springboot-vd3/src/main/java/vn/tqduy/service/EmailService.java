package vn.tqduy.service;

public interface EmailService {
    void sendOtp(String email, String otp, String subject);
}
