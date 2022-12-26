package budget;


import budget.menu.ActionMenu;
import budget.menu.BuyOption;
import budget.menu.ShowOption;
import budget.menu.SortOption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static budget.menu.ActionMenu.*;
import static budget.menu.BuyOption.printBuyOptions;
import static budget.menu.ShowOption.printShowOptions;
import static budget.menu.SortOption.BACK;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Budget budget = new Budget();

        String input = "";
        while (!EXIT.getNumber().equals(input)) {
            printActionMenu();
            input = scanner.nextLine();
            ActionMenu action = getActionByNumber(input);

            switch (action) {
                case ADD_INCOME:
                    addIncome(scanner, budget);
                    break;
                case ADD_PURCHASE:
                    addPurchase(scanner, budget);
                    break;
                case LIST_PURCHASES:
                    showPurchases(budget, scanner);
                    break;
                case BALANCE:
                    displayBalance(budget);
                    break;
                case SAVE:
                    savePurchases(budget);
                    break;
                case LOAD:
                    loadPurchases(budget);
                    break;
                case ANALYZE:
                    analyzeBudget(scanner, budget);
                    break;
                default:
                    System.out.println("\nBye!");
                    break;
            }
        }
    }

    private static void addIncome(Scanner scanner, Budget budget) {
        System.out.println("\nEnter income:");
        String incomeAsString = scanner.nextLine();
        Double income = Double.valueOf(incomeAsString);
        budget.addIncomeToBalance(income);
        System.out.println("Income was added!");
    }


    private static void addPurchase(Scanner scanner, Budget budget) {
        while (true) {
            printBuyOptions();
            String input = scanner.nextLine();
            BuyOption option = BuyOption.getBuyOptionByNumber(input);
            if (BuyOption.BACK.equals(option)) {
                return;
            }
            Item item = createItemFromInput(scanner);
            budget.addItemToPurchases(item, option);
            budget.reduceBalanceByPrice(item.getItemPrice());
            System.out.println("Purchase was added!");
        }
    }

    private static Item createItemFromInput(Scanner scanner) {
        System.out.println("\nEnter purchase name:");
        String itemName = scanner.nextLine();
        System.out.println("Enter its price:");
        Double itemPrice = Double.valueOf(scanner.nextLine());
        Item item = new Item(itemName, itemPrice);
        return item;
    }

    private static void showPurchases(Budget budget, Scanner scanner) {

        while (true) {
            printShowOptions();
            String input = scanner.nextLine();
            ShowOption option = ShowOption.getShowOptionByNumber(input);

            if (ShowOption.BACK.equals(option)) {
                return;
            } else if (ShowOption.ALL.equals(option)) {
                budget.printAllPurchases();
            } else {
                BuyOption buyOption = BuyOption.getBuyOptionByNumber(input);
                budget.printPurchasesByOption(buyOption);
            }
        }

    }

    private static void displayBalance(Budget budget) {
        System.out.println("\nBalance: $" + budget.getBalance());
    }


    private static void savePurchases(Budget budget) {
        StringBuilder sb = new StringBuilder();
        sb.append(budget.getBalance() + System.lineSeparator());
        Map<BuyOption, List<Item>> purchasesMap = budget.getPurchasesMap();
        addPurchasesInfoToString(sb, purchasesMap);
        saveStringToFile(sb);
        System.out.println("\nPurchases were saved!");
    }

    private static void loadPurchases(Budget budget) {
        File file = new File("purchases.txt");
        try {
            Scanner scanner = new Scanner(file);

            int counter = 0;
            while (scanner.hasNextLine()) {

                if (counter == 0) {
                    Double balance = Double.valueOf(scanner.nextLine());
                    budget.setBalance(balance);
                } else {
                    String optionNumber = scanner.next().trim();
                    BuyOption buyOption = BuyOption.getBuyOptionByNumber(optionNumber);
                    Double itemPrice = Double.valueOf(scanner.next().trim());
                    String itemName = scanner.nextLine().trim();
                    Item item = new Item(itemName, itemPrice);
                    budget.addItemToPurchases(item, buyOption);
                }
                counter++;
            }
            System.out.println("\nPurchases were loaded!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void analyzeBudget(Scanner scanner, Budget budget) {

        while (true) {
            SortOption.printSortOptions();
            String input = scanner.nextLine();
            SortOption sortOption = SortOption.getSortOptionByNumber(input);

            if (BACK.equals(sortOption)) {
                return;
            }
            switch (sortOption) {
                case ALL:
                    printSortAll(budget);
                    break;
                case TYPE:
                    printSortType(budget);
                    break;
                case CERTAIN_TYPE:
                    printCertainType(scanner, budget);
                    break;
            }
        }
    }

    private static void printCertainType(Scanner scanner, Budget budget) {
        BuyOption.printBuyOptionsWithoutBack();
        String input = scanner.nextLine();
        BuyOption buyOption = BuyOption.getBuyOptionByNumber(input);

        List<Item> groupItems = budget.getItemsByBuyOption(buyOption);
        if (groupItems.isEmpty()) {
            System.out.println("\nThe purchase list is empty!");
        } else {
            groupItems.sort(Comparator.comparing(Item::getItemPrice).reversed());
            System.out.println("\n" + buyOption.getOption() + ":");
            printItemsAndTotalPrice(groupItems, " ");
        }
    }

    private static void printSortAll(Budget budget) {
        List<Item> allItems = budget.getAllItems();
        if (allItems.isEmpty()) {
            System.out.println("\nThe purchase list is empty!");
        } else {
            allItems.sort(Comparator.comparing(Item::getItemPrice).reversed());
            System.out.println("\nAll:");
            printItemsAndTotalPrice(allItems, " ");
        }
    }

    private static void printSortType(Budget budget) {
        List<Item> groupItems = budget.getGroupItems();
        if (groupItems.isEmpty()) {
            System.out.println("\nThe purchase list is empty!");
        } else {
            groupItems.sort(Comparator.comparing(Item::getItemPrice).reversed());
            System.out.println("\nTypes:");
            printItemsAndTotalPrice(groupItems, " - ");
        }
    }

    private static void printItemsAndTotalPrice(List<Item> items, String hyphen) {
        double total = 0;
        for (Item item : items) {
            System.out.println(item.getItemName() + hyphen + "$" + String.format("%.2f", item.getItemPrice()));
            total = total + item.getItemPrice();
        }
        System.out.println("Total: $" + String.format("%.2f", total));
    }


    private static void addPurchasesInfoToString(StringBuilder sb, Map<BuyOption, List<Item>> purchasesMap) {
        for (Map.Entry<BuyOption, List<Item>> entry : purchasesMap.entrySet()) {
            String optionNumber = entry.getKey().getNumber();
            for (Item item : entry.getValue()) {
                Double itemPrice = item.getItemPrice();
                String itemName = item.getItemName();
                sb.append(optionNumber + " " + itemPrice + " " + itemName + System.lineSeparator());
            }
        }
    }

    private static void saveStringToFile(StringBuilder sb) {
        File file = new File("purchases.txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.substring(0, sb.length() - 2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}