import java.util.Objects;

public class BankAccount {
    private final String accountNumber;
    private final String ownerName;
    private Balance balance;
    private long withdrawLimitInKopecks;

    public BankAccount(String accountNumber, String ownerName, Balance initialBalance, long withdrawLimitInKopecks) {
        this.accountNumber = Objects.requireNonNull(accountNumber, "Номер счета не может быть null");
        this.ownerName = Objects.requireNonNull(ownerName, "Имя владельца не может быть null");
        this.balance = Objects.requireNonNull(initialBalance, "Начальный баланс не может быть null");

        if (withdrawLimitInKopecks < 0) {
            throw new IllegalArgumentException("Лимит на вывод не может быть отрицательным");
        }
        this.withdrawLimitInKopecks = withdrawLimitInKopecks;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Balance getBalance() {
        return balance;
    }

    public long getWithdrawLimitInKopecks() {
        return withdrawLimitInKopecks;
    }

    public void setWithdrawLimitInKopecks(long withdrawLimitInKopecks) {
        if (withdrawLimitInKopecks < 0) {
            throw new IllegalArgumentException("Лимит не может быть отрицательным");
        }
        this.withdrawLimitInKopecks = withdrawLimitInKopecks;
    }

  public void transfer(BankAccount targetAccount, long amountInKopecksToTransfer) {
    Objects.requireNonNull(targetAccount, "Целевой аккаунт не может быть null");
    if (this == targetAccount) {
        throw new IllegalArgumentException("Нельзя переводить деньги на тот же самый счет!");
    }
    if (amountInKopecksToTransfer <= 0) {
        throw new IllegalArgumentException("Сумма перевода должна быть больше нуля");
    }
    if (amountInKopecksToTransfer > this.withdrawLimitInKopecks) {
        throw new IllegalArgumentException("Превышен установленный лимит на вывод средств!");
    }
    if (this.balance.getAmountInKopecks() < amountInKopecksToTransfer) {
        throw new IllegalArgumentException("Недостаточно средств на счете!");
    }

    Balance newSourceBalance = this.balance.subtract(amountInKopecksToTransfer);

    Balance temporaryBalance = new Balance(amountInKopecksToTransfer, this.balance.getCurrency());
    Balance convertedBalanceForTarget = temporaryBalance.convertTo(targetAccount.getBalance().getCurrency());
    Balance newTargetBalance = targetAccount.balance.add(convertedBalanceForTarget.getAmountInKopecks());

    this.balance = newSourceBalance;
    targetAccount.balance = newTargetBalance;
}


    @Override
    public String toString() {
        return String.format("Аккаунт №%s (%s) | Баланс: %s | Лимит вывода: %.2f %s",
                accountNumber, ownerName, balance, withdrawLimitInKopecks / 100.0, balance.getCurrency().getSymbol());
    }
}