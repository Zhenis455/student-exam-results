# Student Exam Results — Java / Spring Boot / Thymeleaf

**Восстановленная версия учебного проекта от 2 октября 2025 года.** Не оригинальный ZIP из 2025 года: исходный архив в доступной истории не найден. Логика и технологии воспроизведены по сохранившемуся описанию.

## Что делает проект

- Показывает всех студентов с ID, именем, фамилией, баллом и оценкой A–F.
- Добавляет, редактирует и удаляет студентов.
- Ищет студентов по имени и фамилии (без учёта регистра).
- Автоматически назначает оценку по результату экзамена:
  - A: 90–100
  - B: 75–89
  - C: 60–74
  - D: 50–59
  - **F: 0–49 (красный цвет)**
- Проверяет, что балл — целое число от 0 до 100, имя и фамилия заполнены.
- Отображает статистику: всего, сдали, не сдали, средний балл.
- 6 демонстрационных студентов для ознакомления.

**Важно:** как и в первоначальном учебном варианте, данные хранятся **в оперативной памяти без базы данных**. После перезапуска изменения исчезнут, демоданные восстановятся.

## Быстрый запуск Windows 10/11

1. Распакуйте ZIP-архив в папку, например `C:\Projects\Student_Exam_Results`.
2. Установите **JDK 21** (не только JRE): [Eclipse Temurin Java 21](https://adoptium.net/temurin/releases/?version=21). Если Java уже установлена, проверьте в PowerShell командой `java -version`.
3. Откройте файл **`START_WINDOWS.bat`** двойным кликом.
4. На первом запуске нужен **интернет**: скрипт автоматически загрузит Gradle 8.14.3, а Gradle — зависимости Spring Boot.
5. После появления сообщения `Started StudentsApplication` откройте **http://localhost:8080/students**.
6. Чтобы остановить сайт, вернитесь к окну консоли и нажмите **Ctrl+C**.

**Проверка тестов:** запустите `TEST_WINDOWS.bat`.

## Mac / Linux

1. Установите JDK 21, убедитесь, что `java -version` показывает `21`.
2. В терминале, в папке проекта:

```bash
chmod +x START_MAC_LINUX.sh TEST_MAC_LINUX.sh scripts/gradle-bootstrap.sh
./START_MAC_LINUX.sh
```

3. Откройте **http://localhost:8080/students**.

Проверка тестов:

```bash
./TEST_MAC_LINUX.sh
```

На первом запуске нужны `curl`, `unzip` и интернет.

## IntelliJ IDEA

- File → Open → укажите папку `Student_Exam_Results`.
- IDE распознает `build.gradle` и предложит импорт Gradle-проекта.
- Выберите JDK 21.
- Выполните Gradle Task `bootRun` либо запустите `com.example.students.StudentsApplication`.
- Для скачивания библиотек при первом импорте требуется интернет.

## Структура

```text
Student_Exam_Results/
├── START_WINDOWS.bat                  # 1-клик запуск Windows
├── START_MAC_LINUX.sh                # запуск Mac/Linux
├── TEST_WINDOWS.bat                   # тесты Windows
├── TEST_MAC_LINUX.sh                 # тесты Mac/Linux
├── build.gradle                      # Gradle + Spring Boot 3.5.6 + Thymeleaf
├── settings.gradle
├── scripts/                          # авто-скачивание Gradle при первом запуске
└── src/
    ├── main/
    │   ├── java/com/example/students/
    │   │   ├── StudentsApplication.java
    │   │   ├── controller/StudentController.java
    │   │   ├── model/Student.java
    │   │   ├── service/{GradeCalculator,StudentService}.java
    │   │   └── validation/StudentForm.java
    │   └── resources/
    │       ├── application.properties
    │       ├── templates/fragments/nav.html
    │       ├── templates/students/{list,form}.html
    │       └── static/{css/style.css,js/app.js}
    └── test/java/com/example/students/
        ├── controller/StudentControllerTest.java
        └── service/{GradeCalculatorTest,StudentServiceTest}.java
```

## Статус проверки

- Проверена отдельно Java-логика выставления оценок (границы шкалы).
- В проект включены автоматические JUnit / MockMvc-тесты проверки логики и веб-маршрутов.
- В среде подготовки архива нет доступа к Maven Central и Gradle distributions, поэтому полноценная **Spring Boot сборка и JUnit-тесты здесь не были запущены**. На вашем компьютере их можно запустить по инструкции выше (при наличии интернета).

## Возможные проблемы

- **`java не найдена` / Java 17** — установите JDK 21 и откройте новую консоль.
- **Port 8080 already in use** — остановите другую программу на 8080 или поменяйте `server.port` в `src/main/resources/application.properties`.
- **Gradle не скачивается** — проверьте интернет / прокси; позже можно использовать установленный Gradle 8.14+ вручную через `gradle bootRun`.
- **Сайт не открывается** — дождитесь `Started StudentsApplication` в консоли и проверьте адрес `http://localhost:8080/students`.
