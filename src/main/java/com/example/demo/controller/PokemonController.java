package com.example.demo.controller;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pokemon;
import com.example.demo.repository.PokemonRepository;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
@RequestMapping(path = "/api")
public class PokemonController {
    @Autowired
    PokemonRepository pokemonRepo;

    @GetMapping(value = "/getpokemon")
    public ResponseEntity<String> getPokemonSQL(@RequestParam("name") String name) {

        try {
            Pokemon pokemonfound = new Pokemon();
            pokemonfound = pokemonRepo.getPokemon(name);

            JsonObject respondPayload = Json.createObjectBuilder()
                    .add("id", pokemonfound.getId())
                    .add("name", pokemonfound.getName())
                    .add("level", pokemonfound.getLevel())
                    .add("hpmax", pokemonfound.getHpmax())
                    .build();
            System.out.println(pokemonfound.toString());

            return ResponseEntity.status(HttpStatus.OK).body(respondPayload.toString());
        } catch (Exception e) {
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("error", "An unexpected error occurred")
                    .add("message", e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }

    }

    @GetMapping(value = "/getallpokemon")
    public ResponseEntity<String> getAllPokemon() {

        try {
            List<Pokemon> pokemons = new LinkedList<>();
            pokemons = pokemonRepo.getAllPokemon();

            JsonArrayBuilder jarray = Json.createArrayBuilder();
            for (Pokemon x : pokemons) {
                JsonObject jsonreview = Json.createObjectBuilder()
                        .add("id", x.getId())
                        .add("name", x.getName())
                        .add("level", x.getLevel())
                        .add("maxhp", x.getHpmax())
                        .build();
                jarray.add(jsonreview);
            }
            JsonObject respondPayload = Json.createObjectBuilder()
                    .add("pokemons", jarray)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(respondPayload.toString());
        } catch (Exception e) {
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("error", "An unexpected error occurred")
                    .add("message", e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }

    }

    @PostMapping(value = "/addpokemon")
    public ResponseEntity<String> addPokemon(@RequestBody String requestPayload) {

        // Read JsonString and convert to JSONObject
        JsonReader reader = Json.createReader(new StringReader(requestPayload));
        JsonObject json = reader.readObject();
        String name = json.getString("name");
        Integer hpmax = json.getInt("hpmax");
        Integer level = json.getInt("level");

        // Create new Pokemon model and insert into SQL table
        Pokemon newpokemon = new Pokemon(null, name, hpmax, level);
        Integer rowsupdated = pokemonRepo.insertPokemon(newpokemon);

        // Check SQL result and return it as response entity
        if (rowsupdated == 1) {
            JsonObject respondPayload = Json.createObjectBuilder()
            .add("status", "Data Inserted")
            .build();
            return ResponseEntity.status(HttpStatus.OK).body(respondPayload.toString());
        } else {
            JsonObject respondPayload = Json.createObjectBuilder()
            .add("status", "Data insert Failed")
            .build();
            return ResponseEntity.status(HttpStatus.valueOf(404)).body(respondPayload.toString());
        }
    }

    @PutMapping(value = "/updatepokemon")
    public ResponseEntity<String> updatePokemon(@RequestBody String requestPayload) {

        // Read JsonString and convert to JSONObject
        JsonReader reader = Json.createReader(new StringReader(requestPayload));
        JsonObject json = reader.readObject();
        Integer id = json.getInt("id");
        String name = json.getString("name");
        Integer hpmax = json.getInt("hpmax");
        Integer level = json.getInt("level");

        // Create new Pokemon model and update SQL table
        Pokemon newpokemon = new Pokemon(null, name, hpmax, level);
        Integer rowsupdated = pokemonRepo.updatePokemon(newpokemon, id);

        // Check SQL result and return it as response entity
        if (rowsupdated == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("Data Updated");
        } else {
            return ResponseEntity.status(HttpStatus.valueOf(404)).body("Data update Failed");
        }
    }

    @DeleteMapping(value = "/deletepokemon")
    public ResponseEntity<String> deletePokemon(@RequestParam Integer id) {
        Integer rowsupdated = pokemonRepo.deletePokemon(id);

        // Check SQL result and return it as response entity
        if (rowsupdated == 1) {
            JsonObject respondPayload = Json.createObjectBuilder()
            .add("status", "Pokemon Deleted")
            .build();
            return ResponseEntity.status(HttpStatus.OK).body(respondPayload.toString());
        } else {
            JsonObject respondPayload = Json.createObjectBuilder()
            .add("status", "Pokemon Delete Failed")
            .build();
            return ResponseEntity.status(HttpStatus.valueOf(404)).body(respondPayload.toString());
        }

    }

}
