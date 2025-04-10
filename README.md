# MovieCom-Mobile

## Opis projektu  
**MovieCom-Mobile** to aplikacja mobilna na system Android stworzona z wykorzystaniem języka **Kotlin** oraz **Jetpack Compose**. 
Jej głównym celem jest umożliwienie użytkownikom wygodnego przeglądania, wyszukiwania oraz komentowania filmów z poziomu urządzenia mobilnego. 
Aplikacja komunikuje się z backendem za pomocą REST API dostarczanego przez aplikację [MovieCom-API](https://github.com/bartosz-kanadys/MovieCom-API).

---

## Funkcjonalności
- 📽️ **Przeglądanie filmów** – lista dostępnych filmów z możliwością podglądu szczegółów.  
- 🔍 **Wyszukiwanie** – filtrowanie i wyszukiwanie filmów na podstawie tytułu.  
- 💬 **Komentarze** – użytkownicy mogą dodawać i usuwać komentarze do filmów. (in progress)
- ⭐ **Ulubione filmy** – możliwość dodawania filmów do ulubionych oraz zapisywania ich lokalnie w bazie danych **Room**. (in progress)
- 🔐 **Autoryzacja** – logowanie oraz rejestracja użytkowników.  (in progress)
- 👥 **Obsługa ról użytkowników** – dostęp do niektórych funkcji zależny od roli (np. użytkownik, administrator).  (in progress)

---

## Technologie
- **Język programowania**: Kotlin  
- **UI**: Jetpack Compose  
- **Architektura**: MVVM  
- **Nawigacja**: Jetpack Navigation  
- **Baza danych lokalna**: Room – wykorzystywana do zapisu ulubionych filmów  
- **Sieć**: Ktor + Kotlinx Serialization  
- **Zarządzanie stanem**: StateFlow 
- **Autoryzacja**: JWT  
- **Dependency Injection**: Koin
- **Backend**: Komunikacja z REST API aplikacji MovieCom (Spring Boot + MongoDB)  

---

## Repozytoria powiązane
- 💻 Backend:  [MovieCom-API](https://github.com/bartosz-kanadys/MovieCom-API)  
- 🌐 Frontend webowy:  [MovieCom-Client](https://github.com/bartosz-kanadys/MovieCom-Client)
- 📱 Aplikacja mobilna: **MovieCom-Mobile** (to repozytorium)  

---

## Autorzy
- 👨‍💻 Bartosz Kanadys

