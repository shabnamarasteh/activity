package com.eshragh.activity.modules;

import com.eshragh.activity.config.ParameterStringBuilder;
import com.eshragh.activity.modules.admins.entity.Admin;
import com.eshragh.activity.modules.captcha.exp.ReCaptchaInvalidException;
import com.eshragh.activity.modules.captcha.service.CaptchaService;
import com.eshragh.activity.modules.jobs.service.JobService;
import com.eshragh.activity.modules.jwt.controller.JwtAuthenticationController;
import com.eshragh.activity.modules.jwt.entity.AdminPrincipal;
import com.eshragh.activity.modules.jwt.model.JwtRequest;
import com.eshragh.activity.modules.unity.service.UnityService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.HttpResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sun.net.www.http.HttpClient;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;
import java.util.*;

// create view sunburst as select j.id as j_id, j.title as j_title , u.id as u_id , u.title as u_title , j.state from job j inner join unity u on u.id = j.unity_id;
// select sum (case when j.state between 0 and 30 then 1 else 0 end) as firstlevel, sum (case when j.state between 31 and 70 then 1 else 0 end) as secondlevel, sum (case when j.state between 71 and 100 then 1 else 0 end) as lastlevel,j.unity_id from job j group by j.unity_id;
// create view countjob as select count(j.unity_id) as count,j.unity_id from job j group by j.unity_id;

@Controller
public class MainController {
    private JobService jobService;
    private UnityService unityService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CaptchaService captchaService;

    @Autowired
    public MainController(JobService jobService, UnityService unityService) {
        this.jobService = jobService;
        this.unityService = unityService;
    }

    @RequestMapping(value = {"/" , ""})
    public String index(Model model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jobs =  mapper.writeValueAsString(this.jobService.findAllJob());
        model.addAttribute("jobs", jobs);

        String unity =  mapper.writeValueAsString(this.unityService.findAllUnity());
        model.addAttribute("unity", unity);

        String jobCount =  mapper.writeValueAsString(this.jobService.findCountJob());
        System.out.println(jobCount);
        model.addAttribute("jobCount", jobCount);

        return "index";
    }

    @RequestMapping(value = {"/login" , "/login/"} , method = RequestMethod.GET)
    public String loginForm(Model model,HttpServletRequest request){
        model.addAttribute("AdminPrincipal" , new AdminPrincipal());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("Authorization") && cookie.getValue().startsWith("Bearer") ) {
                    return "redirect:/";
                }
            }
        }
        return "login";
    }

    @RequestMapping(value = {"/auth_user" , "/auth_user/"} , method = RequestMethod.POST)
    public String login(@ModelAttribute AdminPrincipal adminPrincipal, Model model, HttpServletResponse servletResponse, HttpServletRequest servletrequest) {
        String response = servletrequest.getParameter("g-recaptcha-response");
        try {
            captchaService.processResponse(response);
            servletrequest.setAttribute("g-recaptcha-response" , "");
            model.addAttribute("AdminPrincipal" , adminPrincipal);
            restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject jsonobj = new JSONObject();
            jsonobj.put("username", adminPrincipal.getUsername());
            jsonobj.put("password", adminPrincipal.getPassword());

            HttpEntity<String> request =
                    new HttpEntity<>(jsonobj.toString(), headers);

            String responces;
            responces = restTemplate.postForObject("http://localhost:8080/signin", request, String.class);

            JSONObject obj = new JSONObject(responces);
            String accessToken = obj.getString("accessToken");
            String tokenType = obj.getString("tokenType");


            Cookie cookie = new Cookie("Authorization",tokenType+accessToken);
            cookie.setHttpOnly(true);
            servletResponse.addCookie(cookie);

        } catch (ReCaptchaInvalidException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "redirect:/login?error=3";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/login?error=4";
        }

        return "redirect:/";
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("Admin");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        "admin")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
//    @RequestMapping(value = {"/login" , "/login/"} , method = RequestMethod.POST)
//    public String login(){
//        return "login";
//    }

    @RequestMapping(value = {"/signup" , "/signup/"} , method = RequestMethod.GET)
    public String signupForm(Model model,HttpServletRequest request){
        model.addAttribute("Admin" , new Admin());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("Authorization") && cookie.getValue().startsWith("Bearer") ) {
                    return "redirect:/";
                }
            }
        }
        return "signup";
    }

    @RequestMapping(value = "/login/{error}", method = RequestMethod.GET)
    public  String  loginError(Model model, @PathVariable("error") String error, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("Authorization") && cookie.getValue().startsWith("Bearer") ) {
                    return "redirect:/";
                }
            }
        }
        model.addAttribute("error" , error);
        return "login";
    }
}
