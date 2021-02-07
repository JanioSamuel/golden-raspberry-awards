## Golden Raspberry Awards

#### Tecnologias
- Java 11
- Maven 3.6.3
- Spring boot 2.4.2

#### Frontend

- Caso prefira utilizar uma uma interface web, foi criada uma aplicação frontend a qual consome esta API REST e se encontra [AQUI](https://github.com/JanioSamuel/golden-raspberry-awards-frontend) com as instruções de como subir a aplicação.

#### Como usar
- A aplicação consiste em, a partir de um csv no diretório `resources`, adicionar todos os dados em uma base em memória `H2` e criar os endpoints abaixo:

`http://localhost:8080/api/movies`

 Busca todos os filmes carregados na base apresentando da seguinte forma:
 
  ```
  {
      "id": 2,
      "year": 1980,
      "title": "Cruising",
      "studios": "Lorimar Productions, United Artists",
      "producers": "Jerry Weintraub",
      "winner": false
  }
  ```

`http://localhost:8080/api/winners`

Busca entre todos os vencedores aqueles que tiveram o maior e o menor intervalo entre as premiações, não trazendo aqueles que obtiveram uma única vitória.
Exemplo do retorno:

```
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Buzz Feitshans",
      "interval": 9,
      "previousWin": 1985,
      "followingWin": 1994
    }
  ]
}
```