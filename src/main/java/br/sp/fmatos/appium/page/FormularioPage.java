package br.sp.fmatos.appium.page;

import br.sp.fmatos.appium.core.BasePage;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

import static br.sp.fmatos.appium.core.Utils.logger;

@SuppressWarnings("unused")
public class FormularioPage extends BasePage {

    public void escreverNome( String nome) {
        escrever(MobileBy.AccessibilityId("nome"), nome);
        logger.info("### [TESTE] Nome inserido na text box com sucesso");
    }

    public String obterNome() {
        String texto = obterTexto(MobileBy.AccessibilityId("nome"));
        logger.info("### [TESTE] Nome obtido com sucesso");
        return texto;
    }

    public String obterConsole() {
        String texto = obterTexto(By.xpath("//android.widget.Spinner/android.widget.TextView"));
        logger.info("### [TESTE] Console obtido com sucesso");
        return texto;
    }

    public String obterNomeCadastrado() {
        String texto = obterTexto(By.xpath("//android.widget.TextView[starts-with(@text,'Nome:')]")).toLowerCase();
        logger.info("### [TESTE] Nome cadastrado obtido com sucesso");
        return texto;
    }

    public String obterConsoleCadastrado() {
        String texto = obterTexto(By.xpath("//android.widget.TextView[starts-with(@text,'Console:')]"));
        logger.info("### [TESTE] Console cadastrado obtido com sucesso");
        return texto;
    }

    public String obterOEstadoCheckBox() {
        String texto = obterTexto(By.xpath("//android.widget.TextView[starts-with(@text,'Checkbox:')]"));
        logger.info("### [TESTE] Estado do checkbox obtido com sucesso");
        return texto;
    }

    public String obterOEstadoSwitch() {
        String texto = obterTexto(By.xpath("//android.widget.TextView[starts-with(@text,'Switch:')]"));
        logger.info("### [TESTE] Estado do switch obtido com sucesso");
        return texto;
    }

    public void selecionaConsole( boolean wait, String console) {
        clicar(MobileBy.AccessibilityId("console"));
        logger.info("### [TESTE] Clique para exibição da lista de itens combo com sucesso");
        clicarEmUmElementoDeUmaListaDeElementos(wait, By.className("android.widget.CheckedTextView"), console);
        logger.info("### [TESTE] Clique no item do combo com sucesso");
    }

    public void clicarCheckBox() {
        clicar(By.className("android.widget.CheckBox"));
        logger.info("### [TESTE] Checkbox clicada com sucesso com sucesso");
    }

    public void clicarSwitch() {
        clicar(MobileBy.AccessibilityId("switch"));
        logger.info("### [TESTE] Switch clicado com sucesso com sucesso");
    }

    public void clicarBotaoSalvar() {
        clicarPorTexto("SALVAR");
        logger.info("### [TESTE] Botão salvar clicado com sucesso com sucesso");
    }

    public void clicarBotaoSalvarDemorado() {
        clicarPorTexto("SALVAR DEMORADO");
        logger.info("### [TESTE] Botão salvar demorado clicado com sucesso com sucesso");
        waitPresenceOfElementLocatedAll(By.xpath("//*[@text='SALVAR DEMORADO']"));
    }

    public boolean checkBoxEstaMarcado() {
        boolean estado = isCheckedMarcado(By.className("android.widget.CheckBox"));
        logger.info("### [TESTE] Estado do checkbox obtido com sucesso");
        return estado;
    }

    public boolean switchEstaMarcado() {
        boolean estado = isCheckedMarcado(MobileBy.AccessibilityId("switch"));
        logger.info("### [TESTE] Estado do switch obtido com sucesso");
        return estado;
    }
}
