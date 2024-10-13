package dicegamenew;

import java.util.*;

public class Dicegamenew {

    private static final int ROUNDS = 7;
    private static final int DICE_COUNT = 5;
    private static final int SEQUENCE_SCORE = 20;

    private static class Player {
        String name;
        Map<String, Integer> scoreSheet;

        Player(String name) {
            this.name = name;
            this.scoreSheet = new LinkedHashMap<>();
            scoreSheet.put("Ones", null);
            scoreSheet.put("Twos", null);
            scoreSheet.put("Threes", null);
            scoreSheet.put("Fours", null);
            scoreSheet.put("Fives", null);
            scoreSheet.put("Sixes", null);
            scoreSheet.put("Sequence", null);
        }
    }

    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        for (int round = 1; round <= ROUNDS; round++) {
            System.out.println("Round " + round);
            playTurn(player1);
            printScoreSheet(player1, player2);
            playTurn(player2);
            printScoreSheet(player1, player2);
        }

        announceWinner(player1, player2);
    }

    private static void playTurn(Player player) {
        System.out.println(player.name + "'s turn");
        List<Integer> dice = rollDice(DICE_COUNT);
        List<Integer> setAsideDice = new ArrayList<>();
        String selectedCategory = null;

        for (int throw_ = 1; throw_ <= 3; throw_++) {
            System.out.println("Throw " + throw_ + ": " + dice);

            if (throw_ == 1) {
                System.out.println("Do you want to set aside dice or defer? (set/defer)");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("defer")) {
                    dice = rollDice(DICE_COUNT);
                    continue;
                }
            }

            if (selectedCategory == null) {
                selectedCategory = chooseCategory(player);
                if (selectedCategory == null) {
                    System.out.println("You must choose a category. Try again.");
                    selectedCategory = forceCategoryChoice(player);
                }
            }

            List<Integer> newSetAsideDice = setAsideDiceForCategory(selectedCategory, dice);
            setAsideDice.addAll(newSetAsideDice);
            dice.removeAll(newSetAsideDice);

            System.out.println("Set aside dice: " + setAsideDice);
            System.out.println("Remaining dice: " + dice);

            if (dice.isEmpty() || throw_ == 3) {
                int score = calculateScore(selectedCategory, setAsideDice);
                player.scoreSheet.put(selectedCategory, score);
                System.out.println("Turn ended. Score for " + selectedCategory + ": " + score);
                return;
            }

            if (throw_ < 3) {
                System.out.println("Press Enter to roll remaining dice...");
                scanner.nextLine();
                dice = rollDice(dice.size());
            }
        }
    }

    private static List<Integer> rollDice(int count) {
        List<Integer> dice = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dice.add(random.nextInt(6) + 1);
        }
        return dice;
    }

    private static boolean isSequence(List<Integer> dice) {
        if (dice.size() != 5) return false;
        return dice.equals(Arrays.asList(1, 2, 3, 4, 5)) || dice.equals(Arrays.asList(2, 3, 4, 5, 6));
    }

    private static String chooseCategory(Player player) {
        System.out.println("Choose a category:");
        for (Map.Entry<String, Integer> entry : player.scoreSheet.entrySet()) {
            if (entry.getValue() == null) {
                System.out.println("- " + entry.getKey());
            }
        }
        String category = scanner.nextLine().trim();
        if (player.scoreSheet.containsKey(category) && player.scoreSheet.get(category) == null) {
            return category;
        }
        return null;
    }

    private static String forceCategoryChoice(Player player) {
        while (true) {
            String category = chooseCategory(player);
            if (category != null) {
                return category;
            }
            System.out.println("Invalid category. Please choose again.");
        }
    }

    private static List<Integer> setAsideDiceForCategory(String category, List<Integer> dice) {
        if (category.equals("Sequence")) {
            return isSequence(dice) ? new ArrayList<>(dice) : new ArrayList<>();
        }
        final int targetValue = "OnessTwosThreFourFiveSixe".indexOf(category.substring(0, 4)) / 4 + 1;
        return dice.stream().filter(d -> d == targetValue).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private static int calculateScore(String category, List<Integer> dice) {
        if (category.equals("Sequence")) {
            return isSequence(dice) ? SEQUENCE_SCORE : 0;
        }
        int targetValue = "OnessTwosThreFourFiveSixe".indexOf(category.substring(0, 4)) / 4 + 1;
        return targetValue * dice.size();
    }

    private static void printScoreSheet(Player player1, Player player2) {
        System.out.println("\nScore Sheet:");
        System.out.println("+-----------+----------+----------+");
        System.out.printf("| %-9s | %-8s | %-8s |\n", "Category", player1.name, player2.name);
        System.out.println("+-----------+----------+----------+");
        
        for (String category : player1.scoreSheet.keySet()) {
            System.out.printf("| %-9s | %-8s | %-8s |\n", 
                category, 
                formatScore(player1.scoreSheet.get(category)), 
                formatScore(player2.scoreSheet.get(category)));
        }
        
        System.out.println("+-----------+----------+----------+");
        System.out.printf("| %-9s | %-8d | %-8d |\n", "TOTAL", calculateTotal(player1), calculateTotal(player2));
        System.out.println("+-----------+----------+----------+");
        System.out.println();
    }

    private static String formatScore(Integer score) {
        return score == null ? "-" : score.toString();
    }

    private static int calculateTotal(Player player) {
        return player.scoreSheet.values().stream()
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum();
    }

    private static void announceWinner(Player player1, Player player2) {
        int score1 = calculateTotal(player1);
        int score2 = calculateTotal(player2);
        
        System.out.println("Final Scores:");
        printScoreSheet(player1, player2);
        
        if (score1 > score2) {
            System.out.println(player1.name + " wins!");
        } else if (score2 > score1) {
            System.out.println(player2.name + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}