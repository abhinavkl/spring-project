package com.spring.project.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.spring.project.ProjectApplication;
import com.spring.project.entity.Player;
import com.spring.project.entity.Team;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/team")
public class TeamController {
    private List<Team> teams;
    private List<Player> players;
    private void setTeams(){
        ClassLoader classLoader= ProjectApplication.class.getClassLoader();
        try{
            String filePath=classLoader.getResource("data/teams.json").getPath().substring(1);
            File file=new File(filePath);
            InputStream inputStream=new FileInputStream(file);
            Gson gson=new Gson();
            JsonReader jsonReader=new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            jsonReader.setLenient(true);
            Type type= new TypeToken<ArrayList<Team>>(){}.getType();
            this.teams=gson.fromJson(jsonReader, type);
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
    }
    @RequestMapping(value={"","/","/index"})
    public String index(Model model){
        setTeams();
        model.addAttribute("teams",teams);
        return "team/index.html";
    }

    private void setPlayers(){
        ClassLoader classLoader=ProjectApplication.class.getClassLoader();
        try{
            String filePath=classLoader.getResource("data/players.json").getPath().substring(1);
            File file=new File(filePath);
            InputStream inputStream=new FileInputStream(file);
            Gson gson=new Gson();
            JsonReader jsonReader=new JsonReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));
            jsonReader.setLenient(true);
            Type type= new TypeToken<ArrayList<Player>>(){}.getType();
            this.players=gson.fromJson(jsonReader, type);
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }

    }

    @RequestMapping("/players")
    public String getPlayers(Model model, @RequestParam String teamName){
        setPlayers();
        if(teamName!=null){
            players=players.stream().filter(i->teamName.equals(i.getPlayerTeam())).toList();
        }
        model.addAttribute("players",players);
        return "team/players.html";
    }
}
