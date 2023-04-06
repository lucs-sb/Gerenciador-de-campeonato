# Gerenciador de campeonato

## Identificação
**Componente Curricular:** Projeto Integrador <br/>
**Semestre Letivo:** 2022.2 <br/>
**Professor Responsável pelo PI:** Amanda Drielly Pires Venceslau

## Componentes Curriculares Integralizados
- CRT0025 - Programação Funcional
- CRT0034 - Análise e Projeto de Sistemas
- CRT0388 - Computação Gráfica

## Professores das Disciplinas Integralizadas
- Simone de Oliveira Santos
- Bruno Castro Honorato Silva
- Arnaldo Barreto Vila Nova

## Linhas de Extensão
- Desenvolvimento tecnológico
- Metodologias e estratégias de ensino/aprendizagem
- Tecnologia da Informação

## Habilidades
- Prototipação
- Programação de computadores
- Estruturação de informações em Banco de Dados

## Descrição da Temática
Atividades esportivas são importantes para a saúde, ajudando na prevenção e no combate de várias
doenças como a depressão. É muito comum a realização de práticas esportivas em grupo, às vezes
os amigos gostam de competir para tornar a atividade esportiva mais interessante. A organização
e o acompanhamento de uma competição esportiva pode ser mais prática com um programa para
gerenciar o torneio. Assim, os integrantes da equipe desenvolverão um programa para organizar um
campeonato esportivo, onde o modo de competição e a modalidade esportiva será decidido pela equipe.
A equipe envolvida no projeto praticará o desenvolvimento de interface e interação para criação de um
sistema de fácil utilização. A fim de organizar o desenvolvimento é importante estruturar as classes
e pacotes do sistema, além de estabelecer a ordem de execução das ações no sistema. Por isso, a
documentação do sistema em diagramas UML é um passo fundamental para organizar e padronizar a
comunicação para toda equipe, além de encontrar possíveis problemas antes do desenvolvimento do
sistema. Padrões de projeto também são necessários para uma boa organização do código e facilitar
a implementação.

## Produto
Programa para gerenciamento de uma competição esportiva. A modalidade esportiva é definida pela
equipe, abrangendo desde esportes tradicionais e eSports. A forma de realização da competição
também será decidida pela equipe: mata-mata, pontos corridos, grupos seguida de mata-mata, etc.
Uma conta de usuário é necessária para usar o programa. A conta terá nome completo, e-mail, avatar e torneios criados.

Cada competição terá os seguintes dados:
1. Nome;
2. Descrição;
3. Quantidade de times;
4. Premiação;
5. Forma de competição.

Uma partida terá:
1. Data;
2. Horário;
3. Local;
4. Dois times;
5. Placar;
6. Momento da pontuação (competidor que marcou o ponto e tempo da partida, como um gol,ganho de um set, etc.);
7. Ao menos 4 estatísticas (nome e valor). Por exemplo: cartões amarelos, chutes ao gol, bloqueios, etc.

Um time possuirá:
1. Nome;
2. Imagem do escudo;
3. Abreviação;
4. Nome dos competidores dos times.

As funcionalidades do programa são:
1. Criação de conta;
2. Login;
3. Logout;
4. Edição de conta;
5. Remoção de conta;
6. CRUD de time;
7. CRUD de competição;
8. CRUD de partida;
9. Sorteio automático dos times, grupos, partidas, etc. de acordo com a forma de competição.
Após a conclusão do sorteio, o resultado pode ser alterado, como times, grupos, etc;
10. Exibição da forma de organização da competição, como os grupos, mata-mata, tabela de pontos, etc; de acordo com a forma de competição;
11. Nas listagens de cada CRUD deverão existir opções para reordenação ou filtragem dos itens.
Por exemplo, os times listados podem ser reordenadas ou filtrados pelo nome.

O grupo deve elaborar os seguintes diagramas UML do sistema:
1. classe;
2. pacote;
3. caso de uso;
4. sequência;
5. máquina de estados ou transição de estados.
Em seguida, implementar (ou reestruturar) o código do sistema utilizando os princípios SOLID da orientação a objetos.
É permitida utilização de qualquer framework para manipulação do BD e emprego de qualquer SGBD.

## Projeto 3
### Metodologia
1. Definição dos requisitos do programa;
2. Elaboração dos diagramas UML;
3. Elaboração do modelo das tabelas do banco de dados;
5. Criação do protótipo;
6. Testes com usuários;
7. Ajustes no protótipo;
8. Correções de erros e problemas.

### Critérios do Produto
Ao final da disciplina a nota atribuída ao projeto será conforme as funcionalidades concluídas e os
seguintes itens:
1. Diagramas UML;
2. Funcionalidades implementadas;
3. Interface e interação do programa;
4. Estrutura das tabelas no banco de dados;
5. Normalização das tabelas do banco de dados;
6. Utilização dos princípios SOLID;
