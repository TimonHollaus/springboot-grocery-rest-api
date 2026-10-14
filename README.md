# 🛒 Spring Boot Grocery REST API (CRUD, DTOs & Global Exception Handling)

Ein modularer RESTful Web Service auf Basis von **Spring Boot** zur Verwaltung digitaler Einkaufslisten mit Datenvalidierung, DTO-Pattern und zentralem Exception Handling.

Entwickelt im Rahmen des Gegenstands **Softwareentwicklung (SEW)** an der HTL TGM Wien (4. Jahrgang, 2. Semester).

---

## 🏛 Architektur & Design

Die Anwendung folgt dem standardmäßigen Layered-Architecture-Muster von Spring Boot:

```mermaid
graph TD
    Client["HTTP Client (cURL / Frontend)"]
    
    subgraph SpringBoot ["Spring Boot Application"]
        C["GroceryController (@RestController)"]
        GEH["GlobalExceptionHandler (@ControllerAdvice)"]
        DTO["GroceryItemDto (Jakarta Validation)"]
        S["GroceryListService (Interface)"]
        SM["GroceryListServiceMock (Implementation)"]
        
        C --> DTO
        C --> S
        S <|.. SM
        C -.->|Fehlerbehandlung| GEH
    end

    Client <-->|JSON Requests & Responses| C
    GEH -.->|Standardisierte Fehlermeldungen| Client
```

- **Controller Layer (`GroceryController.java`)**: Definiert RESTful Endpunkte mit standardisierten HTTP-Statuscodes (`200 OK`, `201 Created`, `204 No Content`, `400 Bad Request`).
- **DTO Layer (`GroceryItemDto.java`)**: Trennt die Schnittstellen-Datenstruktur von der internen Datenhaltung. Nutzt `@Valid` und Jakarta-Validierungen.
- **Service Layer (`GroceryListService.java` & `GroceryListServiceMock.java`)**: Kapselt die Geschäftslogik für Erstellung, Aktualisierung, Status-Toggle und Löschung.
- **Zentrales Exception Handling (`GlobalExceptionHandler.java`)**: Fängt Validierungsfehler und ungültige Argumente per `@ControllerAdvice` global ab und liefert lesbare Fehlerantworten.

---

## 🌐 Endpunkte & API-Referenz

| Methode | Pfad | Beschreibung | Status |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/grocery` | Liefert alle Artikel der Einkaufsliste | `200` / `204` |
| `GET` | `/api/grocery/{id}` | Liefert einen einzelnen Artikel anhand der ID | `200` / `404` |
| `POST` | `/api/grocery` | Erstellt einen neuen Artikel (ID darf nicht gesetzt sein) | `201` / `400` |
| `PUT` | `/api/grocery` | Aktualisiert einen bestehenden Artikel vollständig | `200` / `400` |
| `PATCH` | `/api/grocery/{id}?collected=true` | Markiert einen Artikel als eingekauft / erledigt | `200` / `404` |
| `DELETE` | `/api/grocery/{id}` | Entfernt einen bestimmten Artikel | `200` |
| `DELETE` | `/api/grocery` | Leert die gesamte Einkaufsliste | `200` / `204` |

### Beispiel-Aufrufe (cURL)

**Neuen Artikel anlegen:**
```bash
curl -X POST http://localhost:8080/api/grocery \
     -H "Content-Type: application/json" \
     -d '{"description": "Milch", "amount": 2, "unit": "Liter"}'
```

**Status auf "eingekauft" setzen (PATCH):**
```bash
curl -X PATCH "http://localhost:8080/api/grocery/1?collected=true"
```

---

## 📁 Projektstruktur

```
springboot-grocery-rest-api/
├── README.md                           # Projektdokumentation
├── build.gradle                        # Gradle Build-Konfiguration
└── src/
    ├── main/java/org/example/webendpunkteneu/
    │   ├── WebEndpunkteNeuApplication.java # Spring Boot Entry Point
    │   ├── GroceryController.java          # REST Controller
    │   ├── GroceryItemDto.java             # Data Transfer Object mit Validierung
    │   ├── GroceryListService.java         # Service Interface
    │   ├── GroceryListServiceMock.java     # Service Implementierung
    │   └── GlobalExceptionHandler.java     # @ControllerAdvice Error Handling
    ├── main/resources/
    │   └── application.properties          # Server-Konfiguration
    └── test/java/org/example/webendpunkteneu/
        └── WebEndpunkteNeuApplicationTests.java # Spring Boot Context & Unit Tests
```

---

## 🚀 Ausführung & Tests

### Server starten (Gradle)
```bash
# Unter Windows:
.\gradlew.bat bootRun

# Unter Linux/macOS:
./gradlew bootRun
```
> Die Anwendung startet standardmäßig auf Port `8080`.

### Tests ausführen
```bash
./gradlew test
```

---

## 👤 Autor

**Timon Hollaus**  
HTL TGM Wien – Abteilung für Informationstechnologie
