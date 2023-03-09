import java.util.Random;

public class Main {
    public static int bossHealth = 1500;
    public static int bossDamage = 60;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 300, 400, 280, 320, 310};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 25, 10, 20};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber = 0;
    public static String message = "";
    public static Random random = new Random();

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        message = "";
        chooseBossDefence();
        bossHits();
        heroesHit();
        treatHeroes();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }


    public static void bossHits() {
        boolean lucky = random.nextBoolean();
        boolean didThorStopBoss = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && !didThorStopBoss) {
                if (heroesHealth[i] - bossDamage < 0 || (heroesHealth[i] - ((bossDamage / 5) * (heroesAttackType.length - 1) + bossDamage)) < 0) {
                    heroesHealth[i] = 0;
                } else if (heroesAttackType[i] == "Golem" ) {
                    heroesHealth[i] = heroesHealth[i] - ((bossDamage / 5) * (heroesAttackType.length - 1) + bossDamage);
                } else if (lucky && heroesAttackType[i] == "Lucky") {
                    heroesHealth[i] = heroesHealth[i];
                } else if (heroesAttackType[i] == "Berserk") {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage / 10) * 9;
                } else {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage / 5) * 4;
                }
            }
        }
        System.out.println(didThorStopBoss);
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == "Medic") {
                    continue;
                }
                if (heroesAttackType[i] == bossDefence) {
                    int coefficient = random.nextInt(9) + 2;
                    damage = damage * coefficient;
                    message = "Critical damage: " + damage;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else if (heroesAttackType[i] == "Berserk") {
                    bossHealth = bossHealth - (bossDamage / 10 + damage);
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void treatHeroes() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                heroesHealth[i] += 10;
                break;
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        System.out.println(message);
    }
}
