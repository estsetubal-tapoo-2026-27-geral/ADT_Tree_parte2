# ADT Tree — Parte 2

Nesta atividade será revista a especificação do **ADT Tree** desenvolvida na aula anterior. A interface `Position<E>` será integrada no contrato e será completada uma implementação ligada da árvore. O sistema de ficheiros da Parte 1 será reutilizado para planear e executar testes unitários.
> Esta atividade deverá ser realizada sem qualquer utilização de ferramentas de IA generativa.
## Objetivos

No final da atividade deverá ser capaz de:

- distinguir elemento, nó e posição;
- explicar por que motivo uma árvore deve receber e devolver posições;
- integrar `Position<E>` na interface `Tree<E>`;
- compreender uma implementação baseada em nós ligados;
- discutir o contrato do método `checkPosition`;
- demonstrar por que uma posição de outra árvore deve ser rejeitada;
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

## 3. Construir `checkPosition` por experiência e erro

O método começa apenas com o cast:

```java
private TreeNode checkPosition(Position<E> position) {
    return (TreeNode) position;
}
```

Os testes das três primeiras experiências estão inicialmente anotados com
`@Disabled`. Retire essa anotação **apenas a um teste de cada vez**. Em cada
etapa, antecipe o resultado, execute o teste, interprete a falha e acrescente a
`checkPosition` somente a validação necessária.

### Experiência 1 — posição `null`

Ative `nullPositionIsRejected`.

1. É possível fazer cast de `null` para `TreeNode`?
2. Que valor resulta desse cast?
3. Onde ocorre o erro quando `children` tenta utilizar esse valor?
4. A exceção obtida é a exceção definida pelo contrato do ADT?

Não altere o teste. Modifique `checkPosition` até ser lançada
`InvalidPositionException`.

### Experiência 2 — uma `Position` que não é um `TreeNode`

Ative `positionFromAnotherImplementationIsRejected`. O teste fornece um objeto
que implementa `Position<E>`, mas que não foi criado por `TreeImpl`.

1. O parâmetro respeita o tipo declarado pela interface?
2. O cast para `TreeNode` funciona?
3. Que exceção é observada?
4. Essa exceção deve ser exposta ao cliente do ADT?

Melhore `checkPosition` para traduzir esta situação numa
`InvalidPositionException`, preservando a validação da etapa anterior.

### Experiência 3 — um `TreeNode` pertencente a outra árvore

Ative `positionFromAnotherTreeIsRejected`. São agora construídas duas árvores e
é passada à primeira árvore a raiz da segunda.

1. O cast funciona desta vez? Porquê?
2. Ser uma instância de `TreeNode` é suficiente para a posição ser válida?
3. O que aconteceria ao executar
   `tree.insert(otherRoot, file("intruso.txt"))`?
4. Como pode um nó registar qual foi a instância de `TreeImpl` que o criou?
5. Como se compara a identidade de duas árvores?

Acrescente a validação descoberta e confirme que os três testes passam.

### Experiência 4 — uma posição removida

Esta etapa é realizada depois de implementar `remove`. Ative ou escreva um
teste que remova um nó e tente depois utilizá-lo em `children`.

1. O tipo da posição está correto?
2. A posição pertence à árvore atual?
3. Como pode a implementação distinguir um nó atual de um nó removido?

Complete `checkPosition` e, apenas no final, formule por palavras todas as suas
responsabilidades.

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

## 5. Completar cinco testes

A classe `TreeImplTest` fornece métodos de teste já completos e métodos a
completar. Complete apenas os cinco métodos assinalados com `TODO A2.2`:

1. `insertWithOrderAddsChildAtSpecifiedPosition` — inserção de um filho numa
   posição específica;
2. `replaceChangesElementAndReturnsPreviousElement` — substituição do elemento
   e verificação do valor devolvido;
3. `removingRootEmptiesTreeAndInvalidatesAllPositions` — remoção da raiz segundo
   a política de remoção de uma subárvore;
4. `operationWithRemovedPositionThrowsInvalidPositionException` — utilização
   de uma posição depois de removida;
5. `insertWithInvalidOrderThrowsBoundaryViolationException` — inserção com um
   índice inválido.

Em cada teste:

1. prepare o estado inicial;
2. execute a operação;
3. verifique o resultado e as alterações na árvore;
4. utilize `assertThrows` quando se espera uma exceção;
5. retire `@Disabled` quando o teste estiver completo.

## 6. Atualizar o `Main`

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
| `TreeImplTest.java` | testes de casos normais, de limite, de entradas inválidas e estruturais |
| `Main.java` | demonstração aplicada ao sistema de ficheiros |

## Critérios de conclusão

A atividade fica concluída quando:

- o projeto compila e todos os testes passam;
- não são expostos objetos `TreeNode` na interface pública;
- as operações rejeitam posições inválidas;
- tamanho, relações e percursos permanecem coerentes após alterações;
- o grupo consegue justificar a necessidade de `Position<E>` e as decisões da remoção.
