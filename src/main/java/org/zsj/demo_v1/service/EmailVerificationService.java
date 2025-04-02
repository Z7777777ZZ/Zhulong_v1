package org.zsj.demo_v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class EmailVerificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailVerificationService.class);

    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${mail.verification.subject}")
    private String emailSubject;
    
    @Value("${mail.verification.template}")
    private String emailTemplate;

    @Value("${spring.mail.from}")
    private String mailFrom;

    // 存储验证码的Map：email -> [验证码, 过期时间戳]
    private final Map<String, String[]> verificationCodes = new ConcurrentHashMap<>();
    
    // 验证码有效期（10分钟）
    private final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10);
    
    /**
     * 生成6位数字验证码
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 生成100000-999999之间的数
        return String.valueOf(code);
    }
    
    /**
     * 发送验证码到指定邮箱
     */
    public void sendVerificationCode(String email) {
        String code = generateVerificationCode();
        long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
        
        // 存储验证码和过期时间
        verificationCodes.put(email, new String[]{code, String.valueOf(expirationTime)});
        
        logger.info("尝试发送验证码邮件至: {}", email);
        
        try {
            // 使用MimeMessage代替SimpleMailMessage以正确处理中文
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setFrom(mailFrom);
            helper.setTo(email);
            helper.setSubject(emailSubject);
            helper.setText(String.format(emailTemplate, code), true); // 第二个参数true表示使用HTML
            
            mailSender.send(mimeMessage);
            logger.info("验证码邮件发送成功: {}", email);
        } catch (Exception e) {
            logger.error("发送验证码邮件失败: {}, 错误信息: {}", email, e.getMessage(), e);
            throw new RuntimeException("发送验证码邮件失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 验证邮箱验证码
     */
    public boolean verifyCode(String email, String code) {
        String[] storedData = verificationCodes.get(email);
        
        if (storedData == null) {
            logger.warn("验证码不存在: {}", email);
            return false; // 没有找到对应邮箱的验证码
        }
        
        String storedCode = storedData[0];
        long expirationTime = Long.parseLong(storedData[1]);
        
        // 验证码已过期
        if (System.currentTimeMillis() > expirationTime) {
            logger.warn("验证码已过期: {}", email);
            verificationCodes.remove(email); // 清除过期验证码
            return false;
        }
        
        // 验证通过后，删除验证码（一次性使用）
        boolean isValid = code.equals(storedCode);
        if (isValid) {
            logger.info("验证码验证成功: {}", email);
            verificationCodes.remove(email);
        } else {
            logger.warn("验证码不匹配: {}, 输入: {}, 存储: {}", email, code, storedCode);
        }
        
        return isValid;
    }
} 