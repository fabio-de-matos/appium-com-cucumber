package br.sp.fmatos.appium.steps;

import br.sp.fmatos.appium.core.BaseSteps;
import br.sp.fmatos.appium.page.MenuPage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;

import static br.sp.fmatos.appium.core.TestingSetup.*;
import static br.sp.fmatos.appium.core.Utils.logger;

public class CommonSteps extends BaseSteps {

    private MenuPage menu = new MenuPage();

    @Dado("que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm")
    public void queOUsuarioEstaNoAplicativoSenhorBarrigaEmUmDispositivoDaDeviceFarm() {
        String message;
        if (DEVICE_NAME.equals("Samsumg J5")) {
            message = "[LOCAL DEVICE] " + DEVICE_NAME + " was selected";
        } else {
            message = "[REMOTE DEVICE] [OPEN STF] [IP/PORT] "
                    + DEVICE_FARM_IP_ADDRESS + ":" + DEVICE_PORT
                    + " [DEVICE NAME] " + DEVICE_NAME
                    + " was selected";
        }
        logger.info(message);
    }

    @E("clica na opcao {string} do menu")
    public void clicaNaOpcaoDoMenu(String opcaoDoMenu) {
        menu.acessarOpcaoDoMenu(true, opcaoDoMenu);
    }
}
