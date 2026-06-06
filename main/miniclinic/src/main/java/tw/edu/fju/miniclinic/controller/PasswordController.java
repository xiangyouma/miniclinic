package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PasswordForm;

@Controller
public class PasswordController {

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/password")
    public String passwordForm(HttpSession session, Model model) {
        String doctorName = (String) session.getAttribute("loggedInDoctorName");
        model.addAttribute("loggedInDoctorName", doctorName);
        
        if (!model.containsAttribute("passwordForm")) {
            model.addAttribute("passwordForm", new PasswordForm());
        }
        return "password";
    }

    @PostMapping("/password")
    public String changePassword(
            @ModelAttribute("passwordForm") PasswordForm form,
            HttpSession session,
            Model model) {
        
        String doctorId = (String) session.getAttribute("loggedInDoctorId");
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
        
        if (doctor == null || !BCrypt.checkpw(form.getOldPassword(), doctor.getPasswordHash())) {
            model.addAttribute("errorMessage", "舊密碼錯誤");
            return passwordForm(session, model);
        }
        
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "兩次密碼不相符");
            return passwordForm(session, model);
        }
        
        if (form.getNewPassword().length() < 8) {
            model.addAttribute("errorMessage", "密碼至少需要 8 個字元");
            return passwordForm(session, model);
        }
        
        doctor.setPasswordHash(BCrypt.hashpw(form.getNewPassword(), BCrypt.gensalt()));
        doctorRepo.save(doctor);
        
        model.addAttribute("successMessage", "密碼修改成功");
        return passwordForm(session, model);
    }
}