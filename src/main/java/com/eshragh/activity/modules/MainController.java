package com.eshragh.activity.modules;

import com.eshragh.activity.config.ParameterStringBuilder;
import com.eshragh.activity.modules.jobs.service.JobService;
import com.eshragh.activity.modules.jwt.controller.JwtAuthenticationController;
import com.eshragh.activity.modules.jwt.entity.AdminPrincipal;
import com.eshragh.activity.modules.jwt.model.JwtRequest;
import com.eshragh.activity.modules.unity.service.UnityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.HttpResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sun.net.www.http.HttpClient;
import java.io.IOException;
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

    @Autowired
    private JwtAuthenticationController jwtAuthenticationController;

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
    public String loginForm(Model model){
        model.addAttribute("AdminPrincipal" , new AdminPrincipal());

        return "login";
    }
    private ServerSocket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    @RequestMapping(value = {"/xxx" , "/xxx/"} , method = RequestMethod.POST)
//    @SendTo("/signin")
    public String login(@ModelAttribute AdminPrincipal adminPrincipal, Model model) {
        model.addAttribute("AdminPrincipal" , adminPrincipal);
        Socket socket = null;


            String payload="{\"jsonrpc\":\"2.0\",\"method\":\"post\",\"params\":[{\"username\":"+adminPrincipal.getUsername()+"}],\"password\":"+adminPrincipal.getPassword()+"}";





        return "login";
    }

//    @RequestMapping(value = {"/login1" , "/login1/"} )
//    public String login(@ModelAttribute AdminDTO adminDTO) throws Exception {
//        System.out.println("--------72222-----------");c
//        JwtRequest jwtRequest = new JwtRequest();
//        jwtRequest.setUsername(adminDTO.getUsername());
//        jwtRequest.setPassword(adminDTO.getPassword());
//        HttpEntity<?> s = jwtAuthenticationController.createAuthenticationToken(jwtRequest);
//        System.out.println(s.getHeaders()+"-----------------");
//        return "www";
//    }
//
//    @RequestMapping(value = {"/login1" , "/login1/"} , method = RequestMethod.POST)
//    public String login(@ModelAttribute Admin admin){
//        System.out.println("----------1------------"+admin.getEmail());
//        String token = getJWTToken(admin.getEmail());
//        AdminDTO admindto = new AdminDTO();
//        admindto.setUsername(admin.getEmail());
//        admindto.setToken(token);
//        System.out.println("----------2------------"+token);
//        return  "redirect:/admin/";
//    }

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
}
