package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    private PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getPlayerList(Specification<Player> specification) {
        return null;
    }

    @Override
    public Page<Player> getPlayerList(Specification<Player> specification, Pageable sortedByName) {
        return null;
    }

    @Override
    public Player createPlayer(Player player) {
        if (player == null
                || player.getName() == null
                || player.getTitle() == null
                || player.getRace() == null
                || player.getProfession() == null
                || player.getBirthday() == null
                || player.getExperience() == null) {
            return null;
        }
        if (!checkPlayerParameters(player)) return null;
        if (player.getBanned() == null) player.setBanned(false);
        setLevelAndExperienceUntilNextLevel(player);
        return playerRepository.saveAndFlush(player);
    }

    @Override
    public Player getPlayer(Long id) {
        if (playerRepository.findById(id).isPresent()) {
            return playerRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Player updatePlayer(Long id, Player player) {
        return null;
    }

    @Override
    public boolean deletePlayer(Long id) {
        if (playerRepository.findById(id).isPresent()) {
            playerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void setLevelAndExperienceUntilNextLevel(Player player) {
        player.setLevel(calculateLevel(player));
        player.setUntilNextLevel(calculateExperienceUntilNextLevel(player));
    }

    private int calculateLevel(Player player) {
        int exp = player.getExperience();
        return (int) ((Math.sqrt(2500 + 200 * exp) - 50) / 100);
    }

    private int calculateExperienceUntilNextLevel(Player player) {
        int exp = player.getExperience();
        int lvl = calculateLevel(player);
        return 50 * (lvl + 1) * (lvl + 2) - exp;
    }

    public static boolean checkPlayerParameters(Player player) {
        if (player.getName().length() < 1 || player.getName().length() > 12) return false;
        // TODO: null?

        if (player.getTitle().length() < 1 || player.getTitle().length() > 30) return false;
        // TODO: null?

        if (player.getExperience() < 0 || player.getExperience() > 10_000_000) return false;

        if (player.getBirthday().getTime() < 0) return false;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(player.getBirthday());
        if (calendar.get(Calendar.YEAR) < 2_000 || calendar.get(Calendar.YEAR) > 3_000) return false;

        return true;
    }



}
