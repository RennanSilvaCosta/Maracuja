# :melon: Maracuja :melon:
Projeto criado para consultar CEP's

# :bulb: Ideia :bulb:
Eu trabalhava na USB INTERNET, um provedor de internet regional. Quando um cliente se interessava por assinar um pacote de internet, era solicitado dados para realizar o cadastro, como o endereço; Precisavamos saber se naquele endereço informado existia estrutura de fibra optica para fazer a instalação. Então teriamos que abrir o google maps, pesquisar o endereço, confirmar com o setor, e então assim, poder confirmar para o cliente se havia estrutura no determinado endereço. 
Com essa trabalheira toda, veio em minha cabeça a ideia de criar o Maracuja, onde é digitado o CEP informado pelo cliente, e ele me retorna se temos estrutura para fazer a instalação ou não. Simples assim.

## :thinking: Legal, mas como funciona? :thinking:
Bom primeiro precisamos criar uma conta; basta clicar em "Cadastra-se", e preencher o formulario. Peço coisas simples:

* O nome da sua empresa (no caso era um provedor de internet)
* Um nome de usuario
* Um email válido (Se você esquecer a senha mandamos um email de recuperação no email cadastrado)
* Uma senha

<img src="https://media.giphy.com/media/ROxNPEQKoNn9qXM2YJ/giphy.gif">

Com sua conta criada, basta fazer o login.

Caso você tenha uma planilha com os CEP's onde você tem estrutura, basta clicar em "Importar Excel", no menu lateral da tela principal.

<img src="https://media.giphy.com/media/O6hNGIsiadqwwbmrO6/giphy.gif">

Selecionar sua planilha, e em seguida a coluna onde os CEP's estão localizados, aguarde alguns instantes e pronto. Os CEP's onde você já tem estrutura estão cadastrados e já podem ser pesquisados por qualquer usuario da sua empresa.

## :hammer_and_wrench: Tecnologias utilizadas :hammer_and_wrench:

* CSS
* Heroku
* intellij IDEA
* Java
* JavaFX
* Maven
* ~~Mysql~~
* SQLite
* Spring Boot ( API : https://github.com/RennanSilvaCosta/MaracujaAPI )
* ViaCEP (API : https://viacep.com.br/)

## :books: Bibliotecas utilizadas :books:

* AnimateFX 1.2.1
* ApachePOI 4.1.2
* JFoenix 9.0.10
* Gson 2.8.6
* SQLite JDBC 3.32.3.2

## :wrench: Ajustes e melhorias :wrench:

O projeto não foi finalizado, ainda estou com ele em laboratorio e pretendo fazer as seguintes funções e alterações:

- [ ] Adicionar CEP através da tela de "gerenciamento de CEP's" (sem utilizar planilha apenas usando o CEP desejado)
- [ ] Deletar um CEP especifico através da tela de "gerenciamento de CEP's"
- [ ] O usuario com perfil de ADM poderá adicionar novos usuarios e deleta-los
- [ ] Recuperação de senha através do email
- [ ] O usuario poderá editar seus dados como; nome de usuario, nome da empresa, senha. Através da tela de "configurações"
- [x] Utilizar o SQLite localmente em vez do Mysql

## :raising_hand_woman: Seja um contribuidor :raising_hand_man:

Caso queira contribuir com o projeto, fique a vontade para me mandar um email: rennansilvacosta@hotmail.com
Ou faça um fork no projeto e faça seu pull request :slightly_smiling_face:

##

### :alien: Apenas, busquem conhecimento. :alien:
