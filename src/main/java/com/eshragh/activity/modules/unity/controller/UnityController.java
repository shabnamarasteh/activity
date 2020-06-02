package com.eshragh.activity.modules.unity.controller;

import com.eshragh.activity.modules.unity.entity.Unity;
import com.eshragh.activity.modules.unity.service.UnityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = {"/unity" , "/unity/"})
@Controller
public class UnityController {
    private UnityService unityService;

    @Autowired
    public UnityController(UnityService unityService) {
        this.unityService = unityService;
    }


    @RequestMapping(value = {"/register" , "/register/"} , method = RequestMethod.GET)
    public String registerUnityForm(Model model){

        model.addAttribute("unity" , new Unity());
        return "unity/register";
    }

    @RequestMapping(value = {"/delete/{id}" , "/delete/{id}/"} , method = RequestMethod.GET)
    public String deleteUnityForm(Model model, @PathVariable("id") long id){
       this.unityService.delete(id);
        return "redirect:/unity/";
    }

    @RequestMapping(value = {"/register", "/register"} , method = RequestMethod.POST)
    public String registerUnity(@ModelAttribute Unity unity){
        this.unityService.saveUnity(unity);
        return "redirect:/unity/";
    }


    @RequestMapping(value = {"/edit/{id}" , "/edit/{id}/"} , method = RequestMethod.GET)
    public String editUnityForm(Model model, @PathVariable("id") long id){
        model.addAttribute("unity" , this.unityService.findOne(id));
        return "unity/register";
    }

    @RequestMapping(value = {"/" , ""})
    public String allUnity(Model model){
        model.addAttribute("unity", this.unityService.findAllUnity());
        return "unity/allUnitys";
    }
}
