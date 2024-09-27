# Generic structure
A small and simple generic structure made with spring boot and postgresql that can be used as base to create other projects! ☕

## 💻 Requirements
-  Docker compose
- Java 21

## 💼 Features
- Dependencies configured with Docker compose
- Postgresql connection with r2dbc driver
- Basic auth with Spring security for users and roles
- Database migration structure with flyway
- Test structure configured with kotest and mockk

## 🚀 Build and execution

### Docker compose
The first step is run docker compose to start the dependencies
```
docker compose up -d
```
### Test and build

The tests can be run with the following command:
```
./gradlew test
```

The project can be built with gradlew using the following command:
```
./gradlew build
```
After build process finishes, the jar application will be available on  `build/libs/genericstructure-0.0.1.jar`

## 🤝 Contributors

<table>
  <tr>
    <td align="center">
      <a href="#" title="defina o título do link">
        <img src="https://avatars3.githubusercontent.com/u/20827243" width="100px;" alt="Foto do Iuri Silva no GitHub"/><br>
        <sub>
          <b>Cristiano Hahn</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

## 📝 License

This project does not have a license, it's free for everyone!
