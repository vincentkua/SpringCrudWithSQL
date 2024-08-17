package com.example.demo.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pokemon;

@Repository
public class PokemonRepository {

    private static final String GETPOKEMONRSQL = """
            select * from pokemonlist where name = ?;
            """;

    private static final String GETALLPOKEMON = """
            select * from pokemonlist;
            """;

    private static final String INSERTPOKEMON = """
            insert into pokemonlist (name,hpmax,level) values (? , ? , ?)
            """;

    private static final String UPDATEPOKEMON = """
            update pokemonlist set name = ? , hpmax = ? , level = ? where id = ?
            """;

    private static final String DELETEPOKEMON = """
            delete from pokemonlist where id = ?;
            """;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Pokemon getPokemon(String name) {
        Pokemon pokemon = new Pokemon();
        pokemon = jdbcTemplate.queryForObject(GETPOKEMONRSQL, BeanPropertyRowMapper.newInstance(Pokemon.class), name);
        return pokemon;
    }

    public List<Pokemon> getAllPokemon() {
        List<Pokemon> pokemons = new LinkedList<>();
        pokemons = jdbcTemplate.query(GETALLPOKEMON, BeanPropertyRowMapper.newInstance(Pokemon.class));
        return pokemons;
    }

    public Integer insertPokemon(Pokemon pokemon) {
        Integer rowsUpdated = jdbcTemplate.update(INSERTPOKEMON, pokemon.getName(), pokemon.getHpmax(),
                pokemon.getLevel());
        return rowsUpdated;
    }

    public Integer updatePokemon(Pokemon pokemon, Integer id) {
        Integer rowsUpdated = jdbcTemplate.update(UPDATEPOKEMON, pokemon.getName(), pokemon.getHpmax(),
                pokemon.getLevel(), id);
        return rowsUpdated;
    }

    public Integer deletePokemon(Integer id) {
        Integer rowsUpdated = jdbcTemplate.update(DELETEPOKEMON, id);
        return rowsUpdated;
    }

}
