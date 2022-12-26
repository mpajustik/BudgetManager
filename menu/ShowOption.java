
package budget.menu;

public enum ShowOption {

    FOOD("1", "Food"),
    CLOTHES("2", "Clothes"),
    ENTERTAINMENT("3", "Entertainment"),
    OTHER("4", "Other"),
    ALL("5", "All"),
    BACK("6", "Back");

    private String number;
    private String option;

    ShowOption(String number, String option) {
        this.number = number;
        this.option = option;
    }

    public static void printShowOptions() {
        System.out.println("\nChoose the type of purchases");
        System.out.println(FOOD.number + ") " + FOOD.option);
        System.out.println(CLOTHES.number + ") " + CLOTHES.option);
        System.out.println(ENTERTAINMENT.number + ") " + ENTERTAINMENT.option);
        System.out.println(OTHER.number + ") " + OTHER.option);
        System.out.println(ALL.number + ") " + ALL.option);
        System.out.println(BACK.number + ") " + BACK.option);
    }

    public static ShowOption getShowOptionByNumber(String number) {
        for (ShowOption value : ShowOption.values()) {
            if (value.number.equals(number)) {
                return value;
            }
        }
        throw new RuntimeException("Ei leitud ühtegi enumi vastet.");
    }

}