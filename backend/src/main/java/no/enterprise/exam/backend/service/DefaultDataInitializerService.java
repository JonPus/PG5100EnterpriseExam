package no.enterprise.exam.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

//This code is edited and adjusted to fit this project from Andrea Arcuri's repository - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/DefaultDataInitializerService.java'

@Service
public class DefaultDataInitializerService {

    @Autowired
    private UserService userService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private ItemService itemService;

    @PostConstruct
    public void init() {

        String firstUser = "admin";
        String secondUser = "foo";
        String thirdUser = "bar";

        attempt(() -> {
            return userService.createUser(
                    firstUser, firstUser, "admin-last-name", 3000L, 3, "123", "admin@email.com", "admin");
        });
        attempt(() -> {
            return userService.createUser(
                    secondUser, secondUser, "foo-last-name", 3000L, 3, "123", "foo@email.com", "user");
        });
        attempt(() -> {
            return userService.createUser(
                    thirdUser, thirdUser, "bar-last-name", 3000L, 3, "123", "bar@email.com", "user");
        });

        Long pokemonItemOne = attempt(() -> itemService.createItem(
                "Venusaur the Seed Pokemon",
                "Venusaur is a squat, quadruped Pokémon with bumpy, blue-green skin.",
                1000L,
                "Venusaur",
                1)
        );

        Long pokemonItemTwo = attempt(() -> itemService.createItem(
                "Charizard the Flame Pokemon",
                "Charizard is a draconic, bipedal Pokémon.",
                1000L,
                "Charizard",
                1)
        );

        Long pokemonItemthree = attempt(() -> itemService.createItem(
                "Blastoise the Shell Pokemon",
                "Blastoise is a large, bipedal turtle-like Pokémon.",
                1000L,
                "Blastoise",
                1)
        );

        Long pokemonItemFour = attempt(() -> itemService.createItem(
                "Gyarados the Atrocious Pokémon",
                "Gyarados is a serpentine Pokémon with a long body covered in slightly overlapping scales.",
                1500L,
                "Gyarados", 1
                )
        );

        Long pokemonItemFive = attempt(() -> itemService.createItem(
                "Dragonite the Dragon Pokémon",
                "Dragonite is a draconic, bipedal Pokémon with light orange skin.",
                2500L,
                "Dragonite",
                1
                )
        );

        Long pokemonItemSix = attempt(() -> itemService.createItem(
                "Mewtwo the Genetic Pokemon",
                "Mewtwo is a Pokémon created by science.",
                3100L,
                "Mewtwo",
                1)
        );

        Long pokemonItemSeven = attempt(() -> itemService.createItem(
                "Mew the New Species Pokemon",
                "Mew is a pink, bipedal Pokémon with mammalian features.",
                4000L,
                "Mew",
                1)
        );

        Long pokemonItemEight = attempt(() -> itemService.createItem(
                "Typhlosion the Volcano Pokemon",
                "Tyranitar is a large, bipedal, dinosaurian Pokémon with a green, armor-like hide covering its body.",
                4500L,
                "Tyranitar",
                1)
        );

        Long pokemonItemNine = attempt(() -> itemService.createItem(
                "Suicune the Aurora Pokemon",
                "Suicune is a slim, quadruped, blue, mammalian Pokémon with white, diamond-shaped markings.",
                4800L,
                "Suicune",
                1)
        );

        Long pokemonItemTen = attempt(() -> itemService.createItem(
                "Lugia the Diving Pokemon",
                "Lugia is a large Pokémon that resembles a dragon, a plesiosaur, and a bird.",
                5000L,
                "Lugia",
                1)
        );


        //Vensaur
        copyService.newCopies(pokemonItemOne, firstUser);
        copyService.newCopies(pokemonItemOne, thirdUser);
        //Charizard
        copyService.newCopies(pokemonItemTwo, secondUser);
        copyService.newCopies(pokemonItemTwo, thirdUser);
        //Blastoise
        copyService.newCopies(pokemonItemthree, firstUser);
        copyService.newCopies(pokemonItemthree, secondUser);
        copyService.newCopies(pokemonItemthree, thirdUser);
        //Gyarados
        copyService.newCopies(pokemonItemFour, secondUser);
        //Dragonite
        copyService.newCopies(pokemonItemFive, secondUser);

    }

    private <T> T attempt(Supplier<T> lambda) {
        try {
            return lambda.get();
        } catch (Exception e) {
            return null;
        }
    }
}