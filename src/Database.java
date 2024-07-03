import java.io.*;
import java.util.Map;

public class Database {
    public static void load(Map<String, Card> cards, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 5 || parts.length == 6) {
                    String cardNumber = parts[0];
                    String pin = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    boolean blocked = Boolean.parseBoolean(parts[3]);
                    String blockedUntil = parts[4];
                    Card card = new Card(cardNumber, pin, balance, blocked, blockedUntil);
                    cards.put(cardNumber, card);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных карт: " + e.getMessage());
        }
    }

    public static void save(Map<String, Card> cards, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Card card : cards.values()) {
                writer.write(card.getCardNumber() + " " + card.getPin() + " " + card.getBalance() + " " + card.isBlocked() + " " + card.getBlockedUntil());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных карт: " + e.getMessage());
        }
    }
}

