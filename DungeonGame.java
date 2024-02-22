import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class DungeonGame {
    // System objects
    Scanner in = new Scanner(System.in);
    Random rand = new Random();

    // Game variables
    String gameTitle = "The Dungeon";
    String characterName = "";
    ArrayList<String> enemies = new ArrayList<String>(
            Arrays.asList("Skeleton", "Zombie", "Warrior", "Assassin"));;
    int maxEnemyHealth = 75;
    int enemyAttackDamage = 25;
    String healthName = "potion";
    String attackName = "attack";
    int enemyHealth;
    String enemy;

    // Player variables
    int health = 100;
    int attackDamage = 50;
    int numHeals = 3;
    int healAmount = 30;
    int healDropChance = 50; // percentage chance of health being dropped
    int enemiesDefeated = 0;

    // Randomly drop health potion
    public void randomHealthPotionDrop() {
        if (rand.nextInt(100) < healDropChance) {
            numHeals++;
            System.out.println(" # The " + enemy + " dropped a health potion! # ");
            System.out.println(" # " + characterName + ", you have " + numHeals + " health potion(s). #");
        }
    }

    // check if the player wants to keep playing or exit
    public boolean shouldKeepPlaying() {
        System.out.println("--------------------------------------------------");
        System.out.println(characterName + ", you have defeated " + enemiesDefeated + " enemies!");
        System.out.println("What would you like to do, next?");
        System.out.println("1. Continue fighting");
        System.out.println("2. Exit dungeon");

        String input = in.nextLine();

        // Validate input before continuing
        while (!input.equals("1") && !input.equals("2")) {
            System.out.println("Invalid command");
            input = in.nextLine();
        }

        if (input.equals("1")) {
            System.out.println(characterName + ", continue on your adventure");
        } else if (input.equals("2")) {
            System.out.println(characterName + ", you exit the dungeon. You are victorious in your adventures!");
            return false;
        }
        return true;

    }

    public void attack() {
        int damageDealt = rand.nextInt(attackDamage);
        int damageTaken = rand.nextInt(enemyAttackDamage);

        enemyHealth -= damageDealt;
        health -= damageTaken;

        System.out.println("\t> You strike the " + enemy + " for " + damageDealt + " damage.");
        System.out.println("\t> You receive " + damageTaken + " damage in retaliation.");
    }

    public void heal() {
        if (numHeals > 0) {
            health += healAmount;
            numHeals--;
            System.out.println("\t> You drink a health potion, healing yourself for "
                    + healAmount
                    + "." + "\n\t> You now have " + health + " HP." + "\n\t> You have " + numHeals
                    + " health potions left. \n");
        } else {
            System.out.println(
                    "\t> You have no health potions left! Defeat enemies for a chance to get one!\n");
        }
    }

    // Get the user's next option
    public String getUserAction() {
        System.out.println("\tYour HP: " + health);
        System.out.println("\t" + enemy + "'s HP: " + enemyHealth);
        System.out.println("\n\t" + characterName + ", what would you like to do?");
        System.out.println("\t1. Attack");
        System.out.println("\t2. Drink health potion");
        System.out.println("\t3. Run!");

        return in.nextLine();
    }

    public void playGame() {

        // Start the game
        Boolean running = true;
        System.out.println("Welcome to the Dungeon");
        System.out.println("What is your name, brave adventurer?");
        characterName = in.nextLine();

        GAME: while (running) {
            System.out.println("--------------------------------------------------");

            // Setup new enemy
            enemyHealth = this.rand.nextInt(maxEnemyHealth);
            enemy = enemies.get(rand.nextInt(enemies.size()));
            System.out.println("\t# " + enemy + " appeared! #\n");

            // While the player and the enemy has health remaining, let the user choose how
            // to interact with the enemy
            while (enemyHealth > 0 && health > 0) {
                String action = getUserAction();

                switch (action) {
                    // Attack
                    case "1":
                        attack();
                        if (health < 1) {
                            System.out.println("\t> You have taken too much damage; you are too weak to go on.");
                            break GAME;
                        }
                        break;
                    // Heal
                    case "2":
                        heal();
                        break;
                    // Run
                    case "3":
                        System.out.println("\tYou run away from the " + enemy + "!");
                        continue GAME;
                    // handle invalid input and reloop
                    default:
                        System.out.println("\tInvalid command!");
                        break;
                }
            }
            // If the player gets defeated, print defeat message and break out of the game
            // loop
            if (health < 1) {
                System.out.println("You limp out of the dungeon, weak from battle.");
                break;
            }
            // If the player defeats the enemy, print out success and do a random health
            // potion drop
            System.out.println("--------------------------------------------------");
            System.out.println(" # " + enemy + " was defeated! #");
            System.out.println(" # You have " + health + " HP left. #");
            enemiesDefeated++;
            randomHealthPotionDrop();

            // Check if we user wants to keep playing
            running = shouldKeepPlaying();
        }

        // End of game message
        System.out.println("######################");
        System.out.println(characterName + ", you defeated " + enemiesDefeated + " enemies!");
        System.out.println("# THANKS FOR PLAYING #");
        System.out.println("#######################");
    }
}