package com.tadaboh.datn.furniture.selling.web.services.impl;


import com.tadaboh.datn.furniture.selling.web.dtos.request.RegisterRequest;
import com.tadaboh.datn.furniture.selling.web.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String  sender;
    /**
     * Gửi email xác minh đến người dùng.
     *
     * @param user Đối tượng User chứa thông tin người dùng, bao gồm email, tên người dùng, và mã xác minh.
     * @return Thông báo trạng thái việc gửi email, "Email Sent" nếu gửi thành công,
     *         hoặc "Error while Sending Mail" nếu có lỗi xảy ra.
     */
    public  String  sendVerificationCode (User user) {
        String subject = "Verify your email";
        String senderName = "Fami Furniture Store";
        String mailContent = "Hello " + user.getFullname() + ",\n\n";
        mailContent += "Your verification code is: " + user.getVerificationCode() + ",\n\n";
        mailContent += "Please enter this code to verify your email.,\n\n";
        mailContent += "Best regards,\n";
        mailContent += senderName;
        String backupEmail = "hobadat0303@gmail.com";
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(mailContent);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
        return "Email Sent";
    }
}
