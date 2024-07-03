import java.util.HashMap;
import java.util.Map;

public class ATM {
    private Map<String, Card> cards = new HashMap<>();
    private double atmBalance = 1000000; // Лимит средств в банкомате
    private Map<String, Integer> pinAttempts = new HashMap<>();

    public void loadCards(String filename) {
        Database.load(cards, filename);
    }

    public void saveCards(String filename) {
        Database.save(cards, filename);
    }

    public boolean isCardBlocked(String cardNumber) {
        Card card = cards.get(cardNumber);
        if (card != null) {
            boolean blocked = card.isBlocked();
            if (blocked) {
                System.out.println("Карта заблокирована. Программа завершается.");
                return true;
            }
        } else {
            System.out.println("Карта " + cardNumber + " не найдена");
            System.out.println("Выход из системы.");
            saveCards("cards.txt");
            System.exit(0);
        }
        return false;
    }

    public Card authenticate(String cardNumber, String pin) {
        Card card = cards.get(cardNumber);
        if (card != null) {
            if (card.isBlocked()) {
                System.out.println("Карта заблокирована.");
                return null;
            }

            if (card.getPin().equals(pin)) {
                pinAttempts.put(cardNumber, 0);
                return card;
            } else {
                int attempts = pinAttempts.getOrDefault(cardNumber, 0) + 1;
                pinAttempts.put(cardNumber, attempts);
                if (attempts >= 3) {
                    card.block();
                    System.out.println("Карта заблокирована из-за 3 неверных попыток ввода ПИН-кода.");
                    System.out.println("Выход из системы.");
                    saveCards("cards.txt");
                    System.exit(0);
                } else {
                    System.out.println("Неверный ПИН-код. Осталось попыток: " + (3 - attempts));
                }
                return null;
            }
        }
        return null;
    }

    public void checkBalance(Card card) {
        System.out.println("Ваш баланс: " + card.getBalance());
    }

    public void withdraw(Card card, double amount) {
        if (amount > atmBalance) {
            System.out.println("Недостаточно средств в банкомате.");
        } else if (amount > card.getBalance()) {
            System.out.println("Недостаточно средств на счете.");
        } else {
            card.setBalance(card.getBalance() - amount);
            atmBalance -= amount;
            System.out.println("Вы сняли " + amount + ". Ваш новый баланс: " + card.getBalance());
        }
    }

    public void deposit(Card card, double amount) {
        if (amount > 1000000) {
            System.out.println("Сумма пополнения не должна превышать 1 000 000.");
        } else {
            card.setBalance(card.getBalance() + amount);
            System.out.println("Вы пополнили " + amount + ". Ваш новый баланс: " + card.getBalance());
        }
    }
}

