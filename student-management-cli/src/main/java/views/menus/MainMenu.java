package views.menus;

import controllers.Controller;
import views.helpers.MenuMessages;
import views.menus.enums.MainMenuOptions;
import views.menus.interfaces.ViewInterface;
import views.utils.ScannerSingleton;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static views.helpers.MenuMessages.CHOOSE_OPTION;


public class MainMenu implements ViewInterface {
    private Controller controller;


    public MainMenu(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void perform() {

        printMainMenuOptions();
        goToNextMenu();
    }

    private void printMainMenuOptions() {
        Arrays.stream(MainMenuOptions.values())
                .forEach(MainMenuOptions::printFullMessage);
        System.out.print(CHOOSE_OPTION);
    }

    private void goToNextMenu() {
        try {

            int chosenOption = Integer.parseInt(ScannerSingleton.getInstance().nextLine());

            System.out.println();

            ViewInterface nextMenu = Arrays.stream(MainMenuOptions.values())
                    .filter(v -> v.getOption() == chosenOption)
                    .findFirst()
                    .get()
                    .getNextMenu();

            nextMenu.setController(this.controller);
            nextMenu.perform();

        } catch (NumberFormatException | NoSuchElementException ex) {

            System.out.println(MenuMessages.CHOOSE_VALID_NUMBER);

            this.perform();
        }
    }
}
