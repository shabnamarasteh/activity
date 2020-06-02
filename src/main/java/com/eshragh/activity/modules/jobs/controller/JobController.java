package com.eshragh.activity.modules.jobs.controller;

import com.eshragh.activity.modules.jobs.entity.Job;
import com.eshragh.activity.modules.jobs.service.JobService;
import com.eshragh.activity.modules.unity.service.UnityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/job")
public class JobController {
    private JobService jobService;
    private UnityService unityService;

    @Autowired
    public JobController(JobService jobService, UnityService unityService) {
        this.jobService = jobService;
        this.unityService = unityService;
    }

    @RequestMapping(value = {"/register" , "/register/"} , method = RequestMethod.GET)
    public String registerJobForm(Model model){
        model.addAttribute("job" , new Job());
        model.addAttribute("unity" ,this.unityService.findAllUnity());
        return "job/register";
    }
    @RequestMapping(value = {"/delete/{id}" , "/delete/{id}/"} , method = RequestMethod.GET)
    public String deleteJobForm(Model model, @PathVariable("id") long id){
        this.jobService.delete(id);
        return "redirect:/job/";
    }
    @RequestMapping(value = {"/register", "/register"} , method = RequestMethod.POST)
    public String registerJob(@ModelAttribute Job job){
        this.jobService.saveJob(job);
        return "redirect:/job/";
    }


    @RequestMapping(value = {"/edit/{id}" , "/edit/{id}/"} , method = RequestMethod.GET)
    public String editJobForm(Model model, @PathVariable("id") long id){
        model.addAttribute("job" , this.jobService.findOne(id));
        model.addAttribute("unity" ,this.unityService.findAllUnity());
        return "job/register";
    }
    @RequestMapping(value = {"/" , ""})
    public String allJobs(Model model){
        model.addAttribute("jobs", this.jobService.findAllJob());
        return "job/allJobs";
    }

}
