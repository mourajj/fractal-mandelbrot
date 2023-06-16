# Mandelbrot Fractal

Projeto que gera um fractal de mandelbrot em Java com a interface Runnable para utilizacao de threads

## Variáveis


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `INTERACTIONS` | `int` | Quantidade de interacoes que serao feitas |
| `ZOOM` | `double` | Coeficiente de zoom  |
| `WIDTH` | `int` | Largura da tela |
| `HEIGHT` | `int` | Altura da tela |


## Testes

| Quantidade de pixels | Quantidade de threads    | Tempo de execucao                |
| :-------- | :------- | :------------------------- |
| `15000` | 1 | 14259ms |
| `15000` | 2 | 7885ms |
| `15000` | 4 | 4710ms |
| `15000` | 8 | 3449ms |
| `15000` | 16 | 3528ms |

Referencia: https://www3.nd.edu/~dthain/courses/cse30341/spring2018/project3/

