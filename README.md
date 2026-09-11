# ADT Tree — Parte 2

Nesta atividade será revista a especificação do **ADT Tree** desenvolvida na aula anterior. A interface `Position<E>` será integrada no contrato e será completada uma implementação ligada da árvore. O sistema de ficheiros da Parte 1 será reutilizado para planear e executar testes unitários.

## Objetivos

No final da atividade deverá ser capaz de:

- distinguir elemento, nó e posição;
- explicar por que motivo uma árvore deve receber e devolver posições;
- integrar `Position<E>` na interface `Tree<E>`;
- compreender uma implementação baseada em nós ligados;
- preservar as invariantes da árvore nas operações modificadoras;
- planear e escrever testes unitários a partir do contrato do ADT;
- validar a implementação com um exemplo de sistema de ficheiros.

## Ponto de partida

```text
Computador
├── Documentos
│   ├── aulas.pdf
│   └── notas.txt
└── Imagens
    └── ferias.jpg
```

Referir um nó apenas através do elemento armazenado não é suficiente: podem existir ficheiros com o mesmo nome em pastas diferentes e o valor de um elemento pode ser alterado. Nesta atividade, cada nó será referenciado por uma `Position<E>`.

## Estrutura do projeto

```text
src
├── main/java/pt/unips/estsetubal/tapoo
│   ├── adt
│   │   ├── Tree.java
│   │   ├── Position.java
│   │   ├── TreeImpl.java
│   │   └── ...Exception.java
│   ├── model
│   │   └── FileSystemItem.java
│   └── Main.java
└── test/java/pt/unips/estsetubal/tapoo
    └── TreeImplTest.java
```

## 1. Rever o contrato

Observe `Tree.java` e identifique:

- as operações que recebem ou devolvem `Position<E>`;
- a operação que permite obter o elemento armazenado;
- as exceções previstas;
- a política de criação da raiz;
- a política de remoção: neste projeto, `remove` elimina uma subárvore.

Compare este contrato com a proposta elaborada pelo grupo na Parte 1.

## 2. Analisar `Position<E>` e `TreeImpl<E>`

Em `TreeImpl<E>`, identifique:

- o atributo que representa a raiz;
- a classe interna privada `TreeNode`;
- a relação entre `TreeNode` e `Position<E>`;
- as referências para o pai e para os filhos;
- o método `checkPosition`;
- os métodos já completos e os métodos marcados com `TODO A2.2`.

### Invariantes

A implementação deve garantir que:

- `root == null` se e só se a árvore está vazia;
- a raiz tem `parent == null`;
- cada nó não raiz tem exatamente um pai;
- cada filho referencia o pai cuja lista o contém;
- uma posição removida deixa de poder ser utilizada;
- `size()` coincide com o número de posições válidas.

## 3. Planear os testes

Antes de implementar, complete a tabela:

| Operação | Estado inicial | Ação | Resultado esperado |
|---|---|---|---|
| `isEmpty` | árvore vazia | consultar | `true` |
| `insert` | árvore vazia | inserir `Computador` com pai `null` | devolve a raiz |
| `parent` | `aulas.pdf` sob `Documentos` | consultar o pai | posição de `Documentos` |
|  |  |  |  |

Inclua casos normais, casos-limite e casos inválidos: árvore vazia, raiz, folhas, nós internos, posição `null`, posição de outra árvore, posição removida e índices de inserção inválidos.

## 4. Completar a implementação

Em `TreeImpl.java`, implemente os métodos pela ordem seguinte:

1. `isRoot`, `isExternal` e `isInternal`;
2. `size`;
3. `positions`;
4. `remove`.

Depois de completar cada método:

1. retire o `@Disabled` dos testes correspondentes;
2. execute todos os testes;
3. confirme que as invariantes continuam a ser respeitadas.

### Remoção

Neste projeto, `remove(position)` remove a subárvore cuja raiz é a posição recebida. A implementação deve:

- desligar o nó do respetivo pai ou esvaziar a árvore, caso seja a raiz;
- invalidar o nó e todos os descendentes removidos;
- devolver o elemento que estava na posição recebida;
- garantir que o novo resultado de `size()` está correto.

## 6. Completar os testes

A classe `TreeImplTest` fornece a árvore de teste e alguns exemplos. Acrescente testes para:

- `insert(parent, element, order)` com índices válidos e inválidos;
- `replace` e respetivo valor devolvido;
- remoção de uma folha;
- remoção de um nó interno;
- remoção da raiz;
- utilização de qualquer posição removida;
- sequência completa de `positions()` e `elements()`.

Um teste deve explicitar o estado inicial, a operação realizada e o resultado esperado. Utilize `assertThrows` para validar exceções.

## 7. Atualizar o `Main`

Depois de todos os testes passarem, complete o `Main` para:

1. mostrar `size()`;
2. substituir um elemento;
3. remover uma subárvore;
4. apresentar novamente a árvore e o percurso.

## Resultado esperado

| Ficheiro | Resultado |
|---|---|
| `Position.java` | interface genérica de acesso ao elemento |
| `Tree.java` | contrato do ADT com posições e exceções documentadas |
| `TreeImpl.java` | implementação completa e coerente |
| `FileSystemItem.java` | modelo de pastas e ficheiros |
| `TreeImplTest.java` | testes normais, limite, inválidos e estruturais |
| `Main.java` | demonstração aplicada ao sistema de ficheiros |

## Critérios de conclusão

A atividade fica concluída quando:

- o projeto compila e todos os testes passam;
- não são expostos objetos `TreeNode` na interface pública;
- as operações rejeitam posições inválidas;
- tamanho, relações e percursos permanecem coerentes após alterações;
- o grupo consegue justificar a necessidade de `Position<E>` e as decisões da remoção.
