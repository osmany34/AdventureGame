public class Money {
    private String name;
    private int id;
    private int amount;

    public Money(String name, int id, int amount) {
        this.name = name;
        this.id = id;
        this.amount = amount;
    }

    public static Money[] moneys(){
        Money[] moneyList = new Money[3];
        moneyList[0] = new Money("1 Para", 1, 1);
        moneyList[1] = new Money("5 Para", 2, 5);
        moneyList[2] = new Money("10 Para", 3, 10);
        return moneyList;
    }

    public static Money getMoneyObjByID(int id){
        for (Money m : Money.moneys()){
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
