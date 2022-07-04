package com.betaplan.angela.endidemo.controllers;

import com.betaplan.angela.endidemo.models.Art;
import com.betaplan.angela.endidemo.services.ArtService;
import com.betaplan.angela.endidemo.services.OwnerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class NftArtController {

    //inject services into the controller by creating an object so that all the methos can be used from the artservice
    @Autowired
    private ArtService artService;
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/")
    public String index(Model artModel) {
        List<Art> arts= artService.getAllArts();
        artModel.addAttribute("allArts", arts);
        return "index";
    }

    //Add Art(Get/Post)
    @GetMapping("/new")
    public String newArt(@ModelAttribute("newArt") Art newArt,Model model) {
        model.addAttribute("allOwners" , ownerService.getAll());
        return "newP";
    }

    //Create ART

    @PostMapping("/create")
    public String createArt(@Valid @ModelAttribute("newArt") Art newArt, @NotNull BindingResult results){
        if(results.hasErrors()){
            return "newP";
        }
    //go to services to create a new art
        artService.createArt(newArt);
        return "redirect:/";
    }
    //Update Method
    @GetMapping("/edit/{id}")
    public String editArt(@PathVariable Long id, Model model) {
        //i need to edit the info but i also need to show the info from the current state
        //go to services to get all the data
        //create an object that passes the data to that specific id
        Art editArt=artService.artDetails(id);
        model.addAttribute("editArt",editArt);
        return "edit";

    }
    @PutMapping("/update/{id}")
    public String updateArt(@ModelAttribute("editArt") Art editArt, @PathVariable Long id){

        artService.updateArt(editArt);
        return "redirect:/";
    }

    //Delete Art
    //@GetMapping("/delete/{id}")
    //public String deletaArt(@PathVariable Long id){
        //artService.deleteArt(id);
       // return "rdirect:/";
        //try on the url to put the id,you can delete the item from the url bad practice

    //}
    @DeleteMapping("/delete/{id}")
    public String deleteArt(@PathVariable Long id){
        artService.deleteArt(id);
        return "redirect:/";


    }   
    //Art Details
    @GetMapping("/artDetails/{id}")
    public String artDetails(@PathVariable Long id, Model model) {
    Art art = artService.artDetails(id);
    model.addAttribute("art", art);
    return "details";

    }

}
