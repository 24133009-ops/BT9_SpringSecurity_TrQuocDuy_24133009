package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.iotstar.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String email, String otp, String subject) {
        log.info("==================================================");
        log.info("== [SHOP OTP SENDER] EMAIL: {} | MÃ OTP: {} ==", email, otp);
        log.info("==================================================");
        System.out.println("==================================================");
        System.out.println("== [SHOP OTP SENDER] EMAIL: " + email + " | MÃ OTP: " + otp + " ==");
        System.out.println("==================================================");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText("""
                    Xin chào,
                    
                    Mã OTP của bạn là: %s
                    OTP có hiệu lực trong 5 phút và chỉ sử dụng một lần.
                    Không chia sẻ mã này cho người khác.
                    
                    Trân trọng,
                    IOTSTAR SHOP
                    """.formatted(otp));
            mailSender.send(message);
        } catch (Exception e) {
            log.warn("Không thể gửi email thực tế qua SMTP ({}), OTP hiển thị ở log trên console.", e.getMessage());
        }
    }
}
