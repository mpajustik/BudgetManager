package budget.menu;

public enum ActionMenu {

    ADD_INCOME("1", "Add income"),
    ADD_PURCHASE("2", "Add purchase"),
    LIST_PURCHASES("3", "Show list of purchases"),
    BALANCE("4", "Balance"),
    SAVE("5", "Save"),
    LOAD("6", "Load"),
    ANALYZE("7", "Analyze (Sort)"),
    EXIT("0", "Exit");

    private String number;
    private String option;

    ActionMenu(String number, String option) {
        this.number = number;
        this.option = option;
    }

    public static void printActionMenu() {
        System.out.println("\nChoose your actionMenu:");
        System.out.println(ADD_INCOME.number + ") " + ADD_INCOME.option);
        System.out.println(ADD_PURCHASE.number + ") " + ADD_PURCHASE.option);
        System.out.println(LIST_PURCHASES.number + ") " + LIST_PURCHASES.option);
        System.out.println(BALANCE.number + ") " + BALANCE.option);
        System.out.println(SAVE.number + ") " + SAVE.option);
        System.out.println(LOAD.number + ") " + LOAD.option);
        System.out.println(ANALYZE.number + ") " + ANALYZE.option);
        System.out.println(EXIT.number + ") " + EXIT.option);
    }

    public static ActionMenu getActionByNumber(String number) {
        for (ActionMenu value : ActionMenu.values()) {
            if (value.number.equals(number)) {
                return value;
            }
        }
        throw new RuntimeException("Ei leitud ühtegi enumi vastet.");
    }

    public String getNumber() {
        return number;
    }

}