//create class rep bank acc of person having
//acc name , balance, transaction, create functions
//for deposit and withdraw and func for calculating balance
//add checks to check if amount debited

fun main() {
    val alexAccount = Account("Alex")
    alexAccount.deposit(1000);
    alexAccount.withDraw(500);
    alexAccount.deposit(-20);
    alexAccount.withDraw(-100);

    val balance = alexAccount.calculateBalance();
    println("Account balance is $balance");

}

class Account(val accountName: String) {
    var balance = 0;
    var transactions = mutableListOf<Int>();

    fun deposit(amount: Int) {
        if (amount > 0) {
            transactions.add(amount)
            balance += amount;
            println("$amount deposited. Balance is now ${this.balance}")
        } else {
            println("Cannot deposit negative sums");
        }
    }

    fun withDraw(withdrawal: Int) {
        if (withdrawal < 0) {
            println("Cannot withdraw negative sums");

        } else {
            transactions.add(-withdrawal);
            this.balance += -withdrawal;
            println("$withdrawal withdraw. Balance is now ${this.balance}");

        }
    }

    fun calculateBalance(): Int {
        this.balance = 0;
        for (transaction in transactions) {
            this.balance += transaction;
        }
        return balance;
    }

}
