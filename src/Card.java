import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Card {
    private String cardNumber;
    private String pin;
    private double balance;
    private boolean blocked;
    private LocalDateTime blockedUntil;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Card(String cardNumber, String pin, double balance, boolean blocked, String blockedUntil) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.blocked = blocked;
        if (blockedUntil != null && !blockedUntil.equals("null")) {
            try {
                this.blockedUntil = LocalDateTime.parse(blockedUntil, formatter);
            } catch (DateTimeParseException e) {

                try {
                    this.blockedUntil = LocalDateTime.parse(blockedUntil + " 00:00:00", formatter);
                } catch (DateTimeParseException ex) {
                    System.out.println("Ошибка при парсинге даты блокировки: " + blockedUntil);
                    this.blockedUntil = null;
                }
            }
        } else {
            this.blockedUntil = null;
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        if (blocked && blockedUntil != null && LocalDateTime.now().isAfter(blockedUntil)) {
            blocked = false;
            blockedUntil = null;
        }
        return blocked;
    }

    public void block() {
        this.blocked = true;
        this.blockedUntil = LocalDateTime.now().plusDays(1);
    }

    public String getBlockedUntil() {
        return blockedUntil != null ? blockedUntil.format(formatter) : "null";
    }

    public void unblock() {
        this.blocked = false;
        this.blockedUntil = null;
    }
}


