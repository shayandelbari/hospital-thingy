package com.hospital_thingy.view;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApp implements CommandLineRunner {

    private final MainMenuView mainMenuView;

    public ConsoleApp(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    @Override
    public void run(String... args) {
        mainMenuView.start();
    }
}
