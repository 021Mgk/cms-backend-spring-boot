package com.cms.controller;


import com.cms.model.entity.Ostan;
import com.cms.model.entity.Shahr;
import com.cms.model.service.OstanSharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/v1")
public class OstanShahrController {

    @Autowired
    private OstanSharService ostanSharService;


    @RequestMapping("/city/sug")
    public List<Shahr> suggestionShahr(@Param("q") String q){
         List<Shahr> shahrs= ostanSharService.findByWhereShahr("o.name LIKE '%"+q+"%'" );
        return  shahrs;
    }



    @RequestMapping("/city")
    public List<Shahr> getAllShahr(){
        return ostanSharService.findAllShar();
    }
    @RequestMapping("/state")
    public List<Ostan> getAllOstan(){
        return ostanSharService.findAllOstan();
    }

    @RequestMapping("/city/{Id}")
    public Shahr findByIdShahr(@PathVariable("Id") String shahrId){
        Shahr shahr = new Shahr();
        Shahr user = ostanSharService.findOneShahr(shahr ,Long.parseLong(shahrId));
        return  user;
    }
    @RequestMapping("/state/{Id}")
    public Ostan findById(@PathVariable("Id") String ostanId){
        Ostan ostan = new Ostan();
        Ostan user = ostanSharService.findOneOstan(ostan ,Long.parseLong(ostanId));
        return  user;
    }

    @RequestMapping(value = "/city", method = RequestMethod.POST)
    public void saveShahr(@RequestBody Shahr shahr) {
        ostanSharService.saveShahr(shahr);
    }
    @RequestMapping(value = "/state", method = RequestMethod.POST)
    public void saveOstan(@RequestBody Ostan ostan) {
        ostanSharService.saveOstan(ostan);
    }

    @RequestMapping(value = "/city/{Id}", method = RequestMethod.PUT)
    public void updateShar(@PathVariable("Id") String sharId, @RequestBody Shahr shahr) {
        ostanSharService.updateShahr(shahr.setId(Long.parseLong(sharId)));
    }
    @RequestMapping(value = "/state/{Id}", method = RequestMethod.PUT)
    public void updateOstan(@PathVariable("Id") String ostanId, @RequestBody Ostan ostan) {
        ostanSharService.updateOstan(ostan.setId(Long.parseLong(ostanId)));
    }

    @RequestMapping(value = "/city/{Id}", method = RequestMethod.DELETE)
    public void deleteShehr(@PathVariable("Id") String shahrId) {
        Shahr shahr = new Shahr();
        shahr.setId(Long.parseLong(shahrId));
        ostanSharService.deleteShahr(shahr);
    }

    @RequestMapping(value = "/state/{Id}", method = RequestMethod.DELETE)
    public void deleteOstan(@PathVariable("Id") String ostanId) {
        Ostan ostan = new Ostan();
        ostan.setId(Long.parseLong(ostanId));
        ostanSharService.deleteOstan(ostan);
    }
}
