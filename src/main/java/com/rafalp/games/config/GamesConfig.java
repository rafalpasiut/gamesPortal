package com.rafalp.games.config;

import com.rafalp.games.games.sudoku.generator.SudokuGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class GamesConfig {

    @Bean
    @Scope("prototype")
    public SudokuGenerator getNewSudokuGenerator() {
        return new SudokuGenerator();
    }
}
