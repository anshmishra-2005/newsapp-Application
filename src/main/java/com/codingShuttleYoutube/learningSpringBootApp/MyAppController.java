package com.codingShuttleYoutube.learningSpringBootApp;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyAppController {

    private UserRepository userRepository;
    public MyAppController(UserRepository userRepository)
    {
        this.userRepository = userRepository ;
    }

    @PostMapping("/formData")
    public String formHandler(@ModelAttribute User user, RedirectAttributes redirectAttributes)
    {
        // To add from data into database
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message","Record Inserted Successfully.... :)");

        return "redirect:/add";
    }

    @GetMapping("/")
    public String HomePage() {
        return "HomePage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "LoginPage";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes ra) {

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user != null) {

            // Save in session
            session.setAttribute("user", user);
            return "redirect:/main";
        } else {
            ra.addFlashAttribute("error", "Invalid Email or Password");
            return "redirect:/login";
        }

    }


    @GetMapping("/main")
    public String MainPage(){
        return "Index";
    }


    @GetMapping("/signup")
    public String SignUp(){
        return "SignUp";
    }
}
