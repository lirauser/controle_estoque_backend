# controle_estoque_backend
Módulo backend do sistema de controle de estoque

O projeto NEXDOM - CONTROLE DE ESTOQUE é um gerenciador de inventário que permite a usuário realizar a compra e a venda de produtos, adicionar itens e consultar transações.

Para instalar e executar o sistema:

PRÉ-CONFIGURAÇÕES

Instale o Java 17 ou superior (oracle.com/br/java/technologies/downloads/)
Instale o MySQL Workbench (www.mysql.com/products/workbench/)
Instale a IDE de sua preferência. Sugestão: VSCode para rodar o frontend (code.visualstudio.com), e Eclipse para rodar o backend (eclipseide.org)
Instale o Node.js para executar o Javascript (nodejs.org)
Instale o gerenciador Git (git-scm.com/downloads) e o TortoiseSVN (tortoisesvn.net/downloads.html)

INSTALANDO E EXECUTANDO O BACKEND:

1) Crie uma pasta qualquer para inserir o projeto.
2) Na pasta, clique o botão da direita e vá na opção Git Clone. No campo URL, insira <b>https://github.com/lirauser/controle_estoque_backend.git</b>
3) Execute o Eclipse, escolhendo a workspace de sua preferência.
4) Clique em "Import projects" e vá na opção Maven > Existing Maven Projects. Selecione a pasta (controle_estoque_backend) e marque o pom.xml que vai aparecer no quadro.
5) Uma vez aberto o projeto no Eclipse, clique com o botão da direita em cima de pom.xml na raíz do projeto e selecione "Maven Install"
6) Clique em cima do projeto e em "Run" (ou com o botão da direita em cima de "EstoqueBackendApplication.java" > Run As > Java Application
7) Verifique se o Spring foi iniciado (na linha final do terminal deve aparecer "EstoqueBackendApplication : Started EstoqueBackendApplication")

INSTALANDO E EXECUTANDO O FRONTEND:

1) Na mesma pasta, clique o botão da direita e vá na opção Git Clone. No campo URL, insira <b>https://github.com/lirauser/controle_estoque_frontend.git</b>
2) Vá em File > Open Folder e selecione a pasta onde está o módulo backend.
3) Abra um novo terminal em Terminal > New Terminal
4) Digite "npm start"
5) O navegador vai abrir com a página do projeto.

AMBIENTE DE TESTE: 
(Foi utilizado o Postman para testes de API)
1) Baixe a plataforma em: postman.com.
2) Com ambos o backend e o frontend rodando, utilize o arquivo "NEXDOM - Estoque.postman_collection" e importe no Postman, clicando no botão <Import> acima da tela.
