import java.util.Random;

public class Main {
    public static int bossHealth = 1200;
    public static int bossDamage = 50;
    public static String bossDefense;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic",
            "Golem", "Lucky", "Thor", "Witcher"};
    public static int[] heroesHealth = {280, 270, 250, 300, 300, 270, 280, 300};

    public static int[] heroesDamage = {10, 15, 20, 0, 5, 20, 15, 0};
    public static int roundNumber = 0;


    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            round();
        }


    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttacks();
        showStatistics();
        medic();
        golem();
        lucky();
        thor();
        witcher();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefense = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefense);

    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] <= 0) {
                continue;
            }
            if (heroesHealth[i] - bossDamage < 0) {
                heroesHealth[i] = 0;
            } else {
                heroesHealth[i] = heroesHealth[i] - bossDamage;
            }
        }
    }

    public static void heroesAttacks() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] <= 0 || bossHealth <= 0) {
                bossHealth = bossHealth - heroesDamage[i];
            }

            if (heroesAttackType[i] == bossDefense) {
                Random random = new Random();
                int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                int criticalDamage = heroesDamage[i] * coeff;
                if (bossHealth - criticalDamage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - criticalDamage;
                }
                System.out.println("Critical damage: " + criticalDamage);
            } else {
                if (bossHealth - heroesDamage[i] < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - heroesDamage[i];
                }
            }
        }
    }


    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + "------------");
        /*String defense;
        if (bossDefense == null){
            defense = "No defense";
        } else {
            defense = bossDefense;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defense: "
                + (bossDefense == null ? "No defense" : bossDefense));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " +
                    heroesDamage[i]);
        }

    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
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

    public static void medic() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                if (heroesHealth[3] > 0 && heroesHealth[3] < 100) {
                    heroesHealth[3] = heroesHealth[3] - bossDamage;
                } else {
                    int drugs = 40;
                    heroesHealth[i] = heroesHealth[i] + drugs;
                    System.out.println("medic cured");
                    break;
                }

            } else if (heroesHealth[3] < 0) {
                heroesHealth[3] = 0;
                heroesHealth[i] -= bossDamage;
            }

        }
    }


    public static void golem() {
        int golemSaved = bossDamage / 5;
        int playersNumber = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[4] > 0) {
                    continue;
                } else {
                    playersNumber++;
                    heroesHealth[i] = heroesHealth[i] - (bossDamage - golemSaved);
                    heroesHealth[4] = heroesHealth[4] - bossDamage - (golemSaved * playersNumber);
                    System.out.println("golem saved boss attack");
                    break;
                }
            } else if (heroesHealth[4] < 0) {
                heroesHealth[4] = 0;
            }
        }
    }

    public static void lucky() {
        Random random = new Random();
        boolean luckyBoolean = random.nextBoolean();
        int luckyDamage = 0;
        if (heroesHealth[5] > 0 && luckyBoolean) {
            heroesHealth[5] = heroesHealth[5] - luckyDamage;
            System.out.println("lucky avoided boss attack");
            luckyBoolean=false;
        } else if (heroesHealth[5] < 0) {
            heroesHealth[5] = 0;
        } else {
            heroesHealth[5] = heroesHealth[5] - bossDamage;
        }
    }


    public static void thor() {
        Random random = new Random();
        boolean thorBoolean = random.nextBoolean();
        for (int i = 0; i <heroesHealth.length ; i++) {
            if (heroesHealth[6] > 0 && thorBoolean&& heroesHealth[i]>0) {
                bossDamage = 0;
                System.out.println("thor deafened boss attack");
                thorBoolean=false;
                break;
            }
            if (heroesHealth[6] < 0) {
                heroesHealth[6] = 0;
            } else {
                bossDamage = 50;
            }
        }

    }

    public static void witcher() {
        boolean witcherBoolean = false;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (!witcherBoolean && heroesHealth[i] <= 0 && heroesHealth[7] > 0) {
                heroesHealth[i] = heroesHealth[7];
                heroesHealth[7] = 0;
                witcherBoolean  = true;
                System.out.println("Witcher saved hero.");
            }
            break;

        }
    }
}
