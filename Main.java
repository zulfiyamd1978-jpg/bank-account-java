public class Main {
    public static void main(String[] args) {
        System.out.println();

        System.out.println("1. Проверка обычного перевода (рубли в доллары)");
        try {
            Balance rubBalance = new Balance(500_000, Currency.RUB); // 5000 рублей
            Balance usdBalance = new Balance(100_00, Currency.USD);  // 100 долларов

            BankAccount ivanAccount = new BankAccount("408102", "Иван Иванов", rubBalance, 2000_00);
            BankAccount johnAccount = new BankAccount("554499", "John Doe", usdBalance, 2000_00);

            System.out.println("Счета до операции:");
            System.out.println("  " + ivanAccount);
            System.out.println("  " + johnAccount);

            System.out.println("Переводим 1500 рублей от Ивана к John...");
            ivanAccount.transfer(johnAccount, 1500_00);

            System.out.println("Счета после операции:");
            System.out.println("  " + ivanAccount);
            System.out.println("  " + johnAccount);
        } catch (Exception e) {
            System.out.println("Что-то пошло не так: " + e.getMessage());
        }
        System.out.println();

        System.out.println("2. Проверка: создание отрицательного баланса");
        try {
            new Balance(-100, Currency.RUB);
            System.out.println("Ошибка: код пропустил минус!");
        } catch (IllegalArgumentException e) {
            System.out.println("Пройдено. Поймали ошибку: " + e.getMessage());
        }
        System.out.println();

        System.out.println("3. Проверка: передача null вместо валюты");
        try {
            new Balance(1000_00, null);
            System.out.println("Ошибка: код пропустил null!");
        } catch (NullPointerException e) {
            System.out.println("Пройдено. Поймали NullPointerException: " + e.getMessage());
        }
        System.out.println();

        System.out.println("4. Проверка: попытка прибавить минус к балансу");
        try {
            Balance testBalance = new Balance(100_00, Currency.RUB);
            testBalance.add(-50_00);
            System.out.println("Ошибка: метод add принял минус!");
        } catch (IllegalArgumentException e) {
            System.out.println("Пройдено. Поймали ошибку: " + e.getMessage());
        }
        System.out.println();

        System.out.println("5. Проверка: перевод суммы больше, чем есть на балансе");
        try {
            Balance smallBalance = new Balance(10_00, Currency.RUB); // 10 рублей
            BankAccount poorAccount = new BankAccount("111111", "Студент", smallBalance, 5000_00);
            BankAccount richAccount = new BankAccount("222222", "Олигарх", new Balance(10000_00, Currency.RUB), 5000_00);

            System.out.println("Пробуем перевести 500 рублей, имея всего 10 рублей...");
            poorAccount.transfer(richAccount, 500_00);
        } catch (IllegalArgumentException e) {
            System.out.println("Пройдено. Поймали ошибку: " + e.getMessage());
        }
        System.out.println();

        System.out.println("6. Проверка: превышение лимита на вывод");
        try {
            Balance bigBalance = new Balance(100_000_00, Currency.RUB); // 100к рублей
            // Лимит на вывод всего 1000 рублей
            BankAccount limitedAccount = new BankAccount("333333", "Лимитный счет", bigBalance, 1000_00);
            BankAccount targetAccount = new BankAccount("444444", "Получатель", new Balance(0, Currency.RUB), 5000_00);

            System.out.println("Пробуем перевести 5000 рублей при лимите 1000 рублей...");
            limitedAccount.transfer(targetAccount, 5000_00);
        } catch (IllegalArgumentException e) {
            System.out.println("Пройдено. Поймали ошибку: " + e.getMessage());
        }
        System.out.println();
        
    }
}
