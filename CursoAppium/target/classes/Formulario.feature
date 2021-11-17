#language: pt
@formulario
Funcionalidade: FORMULARIO SENHOR BARRIGA

  @wip
  Esquema do Cenario: 01 - FORMULARIO - Prencher campo texto
    Dado que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm
    E clica na opcao "Formulário" do menu
    E preenche o campo Nome com "<NOME>"
    Quando clica no botao salvar
    Então o nome "<NOME>" deve estar sendo exibido na tela
    Exemplos:
      | NOME  |
      | Fabio |

  @wip
  Esquema do Cenario: 02 - FORMULARIO - Interagir com combo
    Dado que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm
    E clica na opcao "Formulário" do menu
    Quando seleciona o videogame "<VIDEOGAME>" na lista de combo
    Então o videogame "<VIDEOGAME>" deve estar sendo exibido na tela
    Exemplos:
      | VIDEOGAME       |
      | XBox One        |
      | PS4             |
      | Nintendo Switch |

  @wip
  Cenario: 03 - FORMULARIO - Validar estado do checkbox e do switch
    Dado que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm
    Quando clica na opcao "Formulário" do menu
    Entao o checkbox deve estar desativado
    E o switch deve estar ativado

  @wip
  Cenario: 04 - FORMULARIO - Interagir com checkbox e switch
    Dado que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm
    E clica na opcao "Formulário" do menu
    E clica no checkbox
    Quando clica no switch
    Entao o checkbox deve estar ativado
    E o switch deve estar desativado

  Esquema do Cenario: 05 - FORMULARIO - Preencher cadastro e Salvar e Salvar Demorado
    Dado que o usuario esta no aplicativo Senhor Barriga em um dispositivo da device farm
    E clica na opcao "Formulário" do menu
    E preenche o campo Nome com "<NOME>"
    E seleciona o videogame "<VIDEOGAME>" na lista de combo
    E clica no checkbox
    E clica no switch
    Quando clica no botao salvar "<TIPO DE BOTAO>"
    Então o nome "<NOME>" deve estar sendo exibido na tela
    E o videogame "<VIDEOGAME>" deve estar sendo exibido na tela
    E o checkbox deve estar ativado
    E o switch deve estar desativado
    Exemplos:
      | NOME  | VIDEOGAME       | TIPO DE BOTAO   |
      | Fabio | XBox One        | Salvar Demorado |
      | Fabio | PS4             | Salvar          |
      | Fabio | Nintendo Switch | Salvar Demorado |




