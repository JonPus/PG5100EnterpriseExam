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
                "Mewtwo the Genetic Pokemon",
                "Mewtwo is a Pokémon created by science.",
                3100L,
                "Mewtwo")
        );

        Long pokemonItemSeven = attempt(() -> monsterService.createItem(
                "Mew the New Species Pokemon",
                "Mew is a pink, bipedal Pokémon with mammalian features.",
                4000L,
                "Mew")
        );

        Long pokemonItemEight = attempt(() -> monsterService.createItem(
                "Typhlosion the Volcano Pokemon",
                "Tyranitar is a large, bipedal, dinosaurian Pokémon with a green, armor-like hide covering its body.",
                4500L,
                "Tyranitar")
        );

        Long pokemonItemNine = attempt(() -> monsterService.createItem(
                "Suicune the Aurora Pokemon",
                "Suicune is a slim, quadruped, blue, mammalian Pokémon with white, diamond-shaped markings.",
                4800L,
                "Suicune")
        );

        Long pokemonItemTen = attempt(() -> monsterService.createItem(
                "Lugia the Diving Pokemon",
                "Lugia is a large Pokémon that resembles a dragon, a plesiosaur, and a bird.",
                5000L,
                "Lugia")
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