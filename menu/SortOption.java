package budget.menu;

public enum SortOption {
    ALL("1", "Sort all purchases"),
    TYPE("2", "Sort by type"),
    CERTAIN_TYPE("3", "Sort certain type"),
    BACK("4", "Back");

    private String number;
    private String option;

    SortOption(String number, String option) {
        this.number = number;
        this.option = option;
    }

    public static void printSortOptions() {
        System.out.println("\nHow do you want to sort?");
        System.out.println(ALL.number + ") " + ALL.option);
        System.out.println(TYPE.number + ") " + TYPE.option);
        System.out.println(CERTAIN_TYPE.number + ") " + CERTAIN_TYPE.option);
        System.out.println(BACK.number + ") " + BACK.option);
    }

    public static SortOption getSortOptionByNumber(String number) {
        for (SortOption value : SortOption.values()) {
            if (value.number.equals(number)) {
                return value;
            }
        }
        throw new RuntimeException("Ei leitud ühtegi enumi vastet.");
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
