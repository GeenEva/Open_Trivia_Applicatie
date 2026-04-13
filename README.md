# 🎯 OpenTrivia

OpenTrivia is een kleine quiz-applicatie die gebruik maakt van trivia vragen om je kennis te testen.  
Het project bestaat uit een **Spring Boot backend** en een **Angular frontend**.

---

## 📦 Projectstructuur

```bash
OpenTrivia/
├── OpenTriviaBackend (Spring Boot API)
└── OpenTriviaFrontend (Angular applicatie)
```

---

## 🚀 Aan de slag

### 🔧 Vereisten

Zorg dat je de volgende tools hebt geïnstalleerd:

- Java 21 ☕
- Maven
- Node.js (LTS aanbevolen)
- Angular CLI (`npm install -g @angular/cli`)

---

## Git clone het project
```bash
git clone https://github.com/GeenEva/Open_Trivia_Applicatie.git
```

## 🖥️ Backend (Spring Boot)

### ▶️ Backend bouw de applicatie
```bash
mvn clean install
```

### ▶️ Backend starten

```bash
cd OpenTriviaBackend
mvn spring-boot:run
```

De backend draait vervolgens op:

http://localhost:8080/

De data voor de applicatie bevindt zich dan op:
http://localhost:8080/api/opentrivia/questions

## 🌐 Frontend (Angular)
### ▶️ Dependencies installeren

```bash
cd OpenTriviaFrontend
npm install
```

### ▶️ Frontend starten

```bash
ng serve
```

De frontend draait vervolgens op:

http://localhost:4200


# 🔗 API koppeling

De frontend communiceert met de backend via:

http://localhost:8080/api/opentrivia

# 🧠 Functionaliteiten
- Startscherm
- Meerkeuze trivia vragen
- Score berekening
- Antwoorden controleren via backend
- Simpel quizverloop (vraag → volgende → resultaat)

# 🛠️ Technische stack
Backend
Java 21
Spring Boot
MapStruct
Caching (@EnableCaching)
Frontend
Angular
TypeScript
Signals API

# 📌 Opmerkingen
Start altijd eerst de backend voordat je de frontend opstart
CORS is geconfigureerd voor http://localhost:4200

Voor de implementatie van de quizlogica heb ik ervoor gekozen om de vragen tijdelijk in de backend op te slaan na het ophalen via de OpenTrivia API. Hierdoor kan de backend de gegeven antwoorden controleren tegen dezelfde set vragen binnen dezelfde sessie.

Dit ontwerp is niet geschikt voor een schaalbare multi-user omgeving, de huidige implementatie gaat ui van een single-player scenario waarbij slechts één gebruiker tegelijk actief is. Hiervoor heb ik in deze opdracht gekozen vanwege de eenvoud en focus op de core functionaliteit.