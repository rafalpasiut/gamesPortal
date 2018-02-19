package com.rafalp.games.games.rps.web;

import com.rafalp.games.domain.RPSGames;
import com.rafalp.games.games.rps.Champion;
import com.rafalp.games.games.rps.FightResult;
import com.rafalp.games.games.rps.GameMaster;
import com.rafalp.games.repository.RPSGamesRepository;
import com.rafalp.games.service.dto.RPSGame.PlayerMoveDto;

import static com.rafalp.games.games.rps.web.GameType.GAMES_TO_WIN;

public class FightResolver {

    private PlayerResolver playerResolver;

    public FightResolver(RPSGamesRepository repository) {
        this.playerResolver = new PlayerResolver(repository);
    }

    public FightResolver(PlayerResolver playerResolver) {
        this.playerResolver = playerResolver;
    }

    public void resolvePlayerPosition(PlayerMoveDto playerMove) {
        playerResolver.resolvePlayerPosition(playerMove);
    }

    public RPSGames resolveFightResult(RPSGames game, FightResult result, Champion player1, Champion player2, GameMaster gameMaster) {
        switch (result) {
            case WIN:
                game.setPlayer1Count(game.getPlayer1Count() + 1);
                game.setPlayer1Win(true);
                game.setPlayer2Win(false);
                break;
            case LOSE:
                game.setPlayer2Count(game.getPlayer2Count() + 1);
                game.setPlayer2Win(true);
                game.setPlayer1Win(false);
                break;
            case TIE:
                game.setPlayer1Win(false);
                game.setPlayer2Win(false);
                break;
        }
        game.setMessage(gameMaster.fightResult(result, player1, player2));
        game.setIsRoundFinished(true);
        if (game.getPlayer1Count() >= GAMES_TO_WIN || game.getPlayer2Count() >= GAMES_TO_WIN) {
            game.setIsGameFinished(true);
        }
        return game;
    }
}
