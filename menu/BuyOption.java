
package budget.menu;

public enum BuyOption {
    FOOD("1", "Food"),
    CLOTHES("2", "Clothes"),
    ENTERTAINMENT("3", "Entertainment"),
    OTHER("4", "Other"),
    BACK("5", "Back");

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setOption(String option) {
        this.option = option;
    }

    private String option;

    BuyOption(String number, String option) {
        this.number = number;
        this.option = option;
    }

    public static void printBuyOptions() {
        printBuyOptionsWithoutBack();
        System.out.println(BACK.number + ") " + BACK.option);
    }

    public static void printBuyOptionsWithoutBack() {
        System.out.println("\nChoose the type of purchase");
        System.out.println(FOOD.number + ") " + FOOD.option);
        System.out.println(CLOTHES.number + ") " + CLOTHES.option);
        System.out.println(ENTERTAINMENT.number + ") " + ENTERTAINMENT.option);
        System.out.println(OTHER.number + ") " + OTHER.option);
    }

    public static BuyOption getBuyOptionByNumber(String number) {
        for (BuyOption value : BuyOption.values()) {
            if (value.number.equals(number)) {
                return value;
            }
        }
        throw new RuntimeException("Ei leitud ühtegi enumi vastet.");
    }

    public String getOption() {
        return option;
    }
}