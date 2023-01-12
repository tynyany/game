package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rest")
public class PlayerController {
    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        Player newPlayer = playerService.createPlayer(player);
        if (newPlayer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(newPlayer, HttpStatus.OK);
        }
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable(name = "id") Long id) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Player player = playerService.getPlayer(id);
        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable("id") Long id) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (playerService.deletePlayer(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /*
    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") Long id, @RequestBody Player player) {
        if (id <= 0 || PlayerServiceImpl.checkPlayerParameters(player)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        PlayerParam responsePlayer = playerService.updatePlayer(id, requestPlayer);
        if (responsePlayer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(responsePlayer, HttpStatus.OK);
    }

     */




}
