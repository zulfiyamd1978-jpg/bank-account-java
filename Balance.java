import java.util.Objects;

public final class Balance {
    private final long amountInKopecks;
    private final Currency currency;

    public Balance(long amountInKopecks, Currency currency) 
    {
        if (amountInKopecks < 0) 
            {
            throw new IllegalArgumentException("Баланс не может быть отрицательным: " + amountInKopecks);
        }
        this.currency = Objects.requireNonNull(currency, "Валюта не должна быть null");
        this.amountInKopecks = amountInKopecks;
    }

    public long getAmountInKopecks() 
    { 
        return amountInKopecks; 
    }
    public Currency getCurrency() 
    { 
        return currency; 
    }

    public Balance add(long amount) 
    {
        if (amount < 0) 
            {
            throw new IllegalArgumentException("Нельзя прибавить отрицательную сумму: " + amount);
        }
        return new Balance(this.amountInKopecks + amount, this.currency);
    }

    public Balance subtract(long amount) 
    {
        if (amount < 0) 
            {
            throw new IllegalArgumentException("Нельзя вычесть отрицательную сумму: " + amount);
        }
        if (this.amountInKopecks < amount) 
            {
            throw new IllegalArgumentException("Недостаточно средств на балансе");
        }
        return new Balance(this.amountInKopecks - amount, this.currency);
    }

    public Balance convertTo(Currency targetCurrency) {
        Objects.requireNonNull(targetCurrency, "Целевая валюта не должна быть null");
        if (this.currency == targetCurrency) 
            {
            return this;
        }
        long totalKopecksInRub = this.amountInKopecks * this.currency.getPriceInRublesKopecks() / 100;
        long targetAmount = (totalKopecksInRub * 100) / targetCurrency.getPriceInRublesKopecks();
        
        return new Balance(targetAmount, targetCurrency);
    }

    @Override
    public String toString() 
    {
        double mainUnit = amountInKopecks / 100.0;
        return String.format("%.2f %s", mainUnit, currency.getSymbol());
    }
}
