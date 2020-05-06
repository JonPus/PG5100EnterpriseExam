package no.enterprise.exam.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {

    @Autowired
    private UserService userService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private ItemService monsterService;

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

        Long pokemonItemOne = attempt(() -> monsterService.createItem(
                "Venusaur the Seed Pokemon",
                "Venusaur is a squat, quadruped Pokémon with bumpy, blue-green skin.",
                1000L,
                "Venusaur")
        );

        Long pokemonItemTwo = attempt(() -> monsterService.createItem(
                "Charizard the Flame Pokemon",
                "Charizard is a draconic, bipedal Pokémon.",
                1000L,
                "Charizard")
        );

        Long pokemonItemthree = attempt(() -> monsterService.createItem(
                "Blastoise the Shell Pokemon",
                "Blastoise is a large, bipedal turtle-like Pokémon.",
                1000L,
                "Blastoise")
        );

        Long pokemonItemFour = attempt(() -> monsterService.createItem(
                "Gyarados the Atrocious Pokémon",
                "Gyarados is a serpentine Pokémon with a long body covered in slightly overlapping scales.",
                1500L,
                "Gyarados")
        );

        Long pokemonItemFive = attempt(() -> monsterService.createItem(
                "Dragonite the Dragon Pokémon",
                "Dragonite is a draconic, bipedal Pokémon with light orange skin.",
                2500L,
                "Dragonite"
                )
        );

        Long pokemonItemSix = attempt(() -> monsterService.createItem(
                "Dragonite the Dragon Pokémon",
                "Mewtwo is a Pokémon created by science.",
                3100L,
                "Mewtwo")
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