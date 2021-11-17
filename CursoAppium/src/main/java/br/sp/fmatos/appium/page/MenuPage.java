package br.sp.fmatos.appium.page;

import br.sp.fmatos.appium.core.BasePage;
import org.openqa.selenium.By;

import static br.sp.fmatos.appium.core.Utils.logger;

public class MenuPage extends BasePage {

    public void acessarOpcaoDoMenu(boolean wait, String opcaoDoMenu) {
        clicarEmUmElementoDeUmaListaDeElementos(wait, By.className("android.widget.TextView"), opcaoDoMenu);
        logger.info("### [TESTE] Clique no item do menu com sucesso");
//        android.widget.ListView
//        By.className("android.widget.ListView")
    }
}
