
---

# Análise de Desempenho de Algoritmos de Ordenação em Java

## Descrição
Este projeto tem como objetivo analisar o desempenho de diferentes algoritmos de ordenação, incluindo Bubble Sort, Counting Sort, Merge Sort e Quick Sort, em suas versões seriais e paralelas. Foram realizados testes em arrays de tamanhos variados (10, 100, 1.000, 10.000 e 100.000 elementos) e com diferentes configurações de threads para identificar o impacto do paralelismo no tempo de execução.

## Estrutura do Projeto
- **/src**: Contém as implementações dos algoritmos de ordenação em Java, divididas em versões seriais e paralelas. Além dos arquivos CSV com os resultados dos testes de desempenho, incluindo tempos de execução em nanosegundos e milissegundos
- **README.md**: Este arquivo de documentação.

## Algoritmos Implementados
### Versões Seriais
- **Bubble Sort**
- **Counting Sort**
- **Merge Sort**
- **Quick Sort**

### Versões Paralelas
- **Parallel Bubble Sort**
- **Parallel Counting Sort**
- **Parallel Merge Sort**
- **Parallel Quick Sort**

## Tecnologias Utilizadas
- **Java SE 8+**: Para o desenvolvimento dos algoritmos e execução dos testes.
- **Python 3**: Para a plotagem de gráficos dinâmicos.


## Como Executar
1. Clone o repositório:
   ```bash
   git clone https://github.com/eduardotajra/AnaliseDesempenhoAlgoritmosBuscaAmbientesConcorrentesParalelos
   cd AnaliseDesempenhoAlgoritmosBuscaAmbientesConcorrentesParalelos
   ```

2. Compile o projeto Java:
   ```bash
   javac -d bin src/**/*.java
   ```

3. Execute a análise de desempenho:
   ```bash
   java -cp bin PerformanceAnalyzer
   ```

4. Instalar as bibliotecas python:
   ```bash
   pip install nbformat --upgrade
   pip install pandas
   pip install plotly
   ```

## Resultados dos Testes
Os resultados de desempenho estão disponíveis nos arquivos CSV localizados na pasta `/src`. Eles mostram os tempos de execução dos algoritmos em diferentes tamanhos de arrays e números de threads.

## Metodologia
- **Ambiente de Teste**: Testes realizados em um ambiente multi-core com suporte para threads.
- **Medição de Tempo**: Os tempos de execução foram registrados em nanosegundos e convertidos para milissegundos para melhor interpretação.
- **Análise de Desempenho**: Comparação do desempenho de cada algoritmo em versões seriais e paralelas, observando o ponto de saturação do paralelismo.

## Contribuições
Contribuições são bem-vindas! Se você deseja melhorar o projeto ou adicionar novos algoritmos, sinta-se à vontade para enviar um pull request.

## Contato
- **Autor:** Eduardo de Morais Tajra
- **Email:** eduardotajra2@hotmail.com
- **LinkedIn:** [Eduardo Tajra](https://www.linkedin.com/in/eduardo-tajra-703361235/)

---
