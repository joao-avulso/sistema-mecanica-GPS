﻿# sistema-mecanica-GPS

# Gerando um JAR e Criando um Instalador MSI com JavaFX e Spring Boot

---

## **1. Pré-requisitos**

Certifique-se de ter os seguintes itens instalados:

-   **Java Development Kit (JDK)**: Versão 17 ou superior.
-   **Maven**: Para gerenciar dependências e construir o JAR.
-   **WiX Toolset**: Necessário para criar o instalador MSI. Baixe [aqui](https://github.com/wixtoolset/wix3/releases).

---

## **2. Gerando o JAR com Dependências**

No terminal, execute:

```bash
mvn clean package
```

O JAR gerado estará localizado em `target/mecanica-0.0.1-SNAPSHOT-jar-with-dependencies.jar`.

---

## **3. Criando um Instalador MSI**

1. **Configure o `jpackage`**:
   Certifique-se de que o `jpackage` esteja disponível no seu JDK. Você pode verificar com:

    ```bash
    jpackage --version
    ```

2. **Comando para Criar o Instalador**:
   Execute o seguinte comando para criar o instalador MSI:

    ```bash
    jpackage \
        --input target \
        --main-jar mecanica-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
        --main-class br.com.gps.mecanica.MecanicaApplication \
        --type msi \
        --name Mecanica \
        --win-dir-chooser
    ```

    - **Parâmetros Importantes**:
        - `--input`: Diretório onde o JAR está localizado.
        - `--main-jar`: Nome do JAR principal.
        - `--main-class`: Classe principal com o método `main`.
        - `--type`: Tipo do instalador (neste caso, `msi`).
        - `--win-shortcut`: Cria atalhos no menu Iniciar.
        - `--win-menu`: Adiciona o programa ao menu de aplicativos.
        - `--win-dir-chooser`: Permite que o usuário escolha o diretório de instalação.

3. **Localização do Instalador**:
   O instalador será gerado no mesmo diretório onde o comando foi executado.
