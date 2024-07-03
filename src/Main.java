import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.loadCards("cards.txt");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер карты (формат: XXXX-XXXX-XXXX-XXXX):");
        String cardNumber = scanner.nextLine();

        // Проверка на заблокированную карту
        if (atm.isCardBlocked(cardNumber)) {
            scanner.close();
            return;
        }

        boolean authenticated = false;
        Card card = null;

        for (int i = 0; i < 3 && !authenticated; i++) {
            System.out.println("Введите ПИН-код:");
            String pin = scanner.nextLine();
            card = atm.authenticate(cardNumber, pin);
            if (card != null) {
                authenticated = true;
            }
        }

        if (authenticated) {
            boolean exit = false;
            while (!exit) {
                System.out.println("Выберите операцию:");
                System.out.println("1. Проверить баланс");
                System.out.println("2. Снять средства");
                System.out.println("3. Пополнить баланс");
                System.out.println("4. Выйти");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        atm.checkBalance(card);
                        break;
                    case 2:
                        System.out.println("Введите сумму для снятия:");
                        double amountWithdraw = scanner.nextDouble();
                        atm.withdraw(card, amountWithdraw);
                        break;
                    case 3:
                        System.out.println("Введите сумму для пополнения:");
                        double amountDeposit = scanner.nextDouble();
                        atm.deposit(card, amountDeposit);
                        break;
                    case 4:
                        exit = true;
                        atm.saveCards("cards.txt");
                        System.out.println("Выход из системы.");
                        break;
                    default:
                        System.out.println("Некорректный выбор. Попробуйте снова.");
                }
            }
        } else {
            System.out.println("Неверный номер карты или ПИН-код.");
            atm.saveCards("cards.txt");
        }

        scanner.close();
    }
}


