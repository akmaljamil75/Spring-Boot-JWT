package com.spring_jwt.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.MongoCollection;
import com.spring_jwt.dto.auth.ForgetPasswordDTO;
import com.spring_jwt.dto.auth.LoginDTO;
import com.spring_jwt.dto.user.CreateUserDTO;
import com.spring_jwt.entity.RoleEntity;
import com.spring_jwt.entity.UserEntity;
import com.spring_jwt.exception.impl.BadRequestException;
import com.spring_jwt.exception.impl.ConflictException;
import com.spring_jwt.repository.UserRepository;
import com.spring_jwt.utility.DateUtils;
import com.spring_jwt.utility.JwtUtils;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public Object save(CreateUserDTO dto) throws Exception { 

        Optional<RoleEntity> findRole = roleService.findByRole(dto.getRole());
        if(!findRole.isPresent()) {
            throw new BadRequestException("Role tidak ditemukan");
        }

        Optional<UserEntity> findDuplicateUsername = userRepository.findByUsername(dto.getUsername());
        if(findDuplicateUsername.isPresent()) {
            throw new ConflictException("Username " + dto.getUsername() + " Telah digunakan");
        }

        Optional<UserEntity> findDuplicateMail = userRepository.findByUsername(dto.getUsername());
        if(findDuplicateMail.isPresent()) {
            throw new ConflictException("Email " + dto.getUsername() + " Telah digunakan");
        }

        UserEntity entity = new UserEntity();
        entity.setRole(dto.getRole());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(encoder.encode(dto.getPassword()));
        entity.setCreated_at(DateUtils.getDate());
        entity.setUpdated_at(DateUtils.getDate());
        entity.set__version(System.currentTimeMillis());
        entity.setCreated_by("DMUSER");
        entity.setUpdated_by("DMUSER");
        return userRepository.save(entity);
    }

    public String generatedToken(LoginDTO dto) throws Exception {
        MongoCollection<Document> col = mongoTemplate.getCollection("users");
        Document find = col.find(new Document("username", dto.getUsername())).first();
        if(find.isEmpty()) {
            throw new BadRequestException("Username Tidak Ditemukan");
        }
        boolean comparePass = encoder.matches(dto.getPassword(), find.getString("password"));
        if(!comparePass) {
            throw new BadRequestException("Password Salah");
        }
        Document bodyClaim = new Document();
        bodyClaim.append("username", find.getString("username"));
        bodyClaim.append("role", find.getString("role"));
        return jwtUtils.generateToken(find.getString("username"), bodyClaim);
    }

    public void sendMailVertificationResetPassword(ForgetPasswordDTO dto) throws Exception {
        MongoCollection<Document> col = mongoTemplate.getCollection("users");
        Document find = col.find(new Document("username", dto.getUsername())).first();
        if(find == null) {
            throw new BadRequestException("Username Tidak Ditemukan");
        }
        if(!find.getString("email").equals(dto.getEmail())) {
            throw new BadRequestException("Email yang Dimasukan Berbeda Dengan Email yang Didaftarkan");
        }
        boolean comparePass = encoder.matches(dto.getPassword(), find.getString("passsword"));
        if(comparePass) {
            throw new BadRequestException("Password Sudah Pernah Digunakan, Silahkan Isi Password Yang Lain");
        }
        Document bodyClaim = new Document();
        bodyClaim.append("username", find.getString("username"));
        bodyClaim.append("role", find.getString("role"));
        String token = jwtUtils.generateTokenForForgetPassowrd(find.getString("username"),bodyClaim);
        String htmlBody = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/ForgetEmailTemplate.html")));
        htmlBody = htmlBody.replace("${linkForgetPassowrd}", "http://localhost:8080/auth/reset-password/" + token + "/" + find.getString("username") + "?password=" + dto.getPassword());
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(dto.getEmail()); 
        helper.setSubject("Link Berlaku Hanya 15 Menit");
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }

    @Transactional
    public void resetPassword(String token, String username, String password) throws Exception {
        jwtUtils.extractAllClaims(token);
        MongoCollection<Document> col = mongoTemplate.getCollection("users");
        Document find = col.find(new Document("username", username)).first();
        if(find.isEmpty()) {
            throw new BadRequestException("Username Tidak Ditemukan");
        }
        if(username == null || username.equals("")) {
            throw new BadRequestException("Password Tidak Boleh Kosong");
        }
        boolean comparePass = encoder.matches(password, find.getString("passsword"));
        if(comparePass) {
            throw new BadRequestException("Password Sudah Pernah Digunakan, Silahkan Isi Password Yang Lain");
        }
        col.updateOne(new Document("_id", find.getObjectId("_id")), new Document("$set", new Document("password", encoder.encode(password))));
        return;
    }

}
