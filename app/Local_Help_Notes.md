# Local Service Provider - Development Notes

## 📱 Project Overview

**App Name:** Local Service Provider
**Tech Stack:** Kotlin, Jetpack Compose, MVVM, Hilt, Firebase

---

## 🏗️ Architecture

```text
UI (Compose)
    ↓
ViewModel
    ↓
Repository
    ↓
Firebase
```

### Main Layers

* **presentation** → Compose screens + ViewModels
* **domain** → Models + Repository interfaces
* **data** → Repository implementations + Firebase logic
* **di** → Hilt modules

---

## 📂 Folder Structure

```text
com.techfinder.localserviceprovider
│
├── data
│   └── repository
│
├── domain
│   ├── model
│   └── repository
│
├── presentation
│   ├── screens
│   └── viewmodel
│
└── di
```

---

## 🔐 Authentication Flow

```text
LoginScreen
    ↓
Send OTP
    ↓
Verify OTP
    ↓
FirebaseAuth
    ↓
Create PhoneAuthUser
    ↓
Customer Home
```

### User Model

```kotlin
data class PhoneAuthUser(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val role: String = "customer",
    val providerProfileCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
```

---

## 🛠️ Provider Registration Flow

```text
Profile Screen
    ↓
Register as a Provider
    ↓
ProviderRegistrationScreen
    ↓
Save ProviderModel
    ↓
Update role = provider
    ↓
Provider Home
```

### Provider Model

```kotlin
data class ProviderModel(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val category: String = "",
    val experience: Int = 0,
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val available: Boolean = true,
    val verified: Boolean = false,
    val rating: Double = 0.0,
    val totalReviews: Int = 0
)
```

---

## 📦 Hilt Setup

### AppModule

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()
}
```

### RepositoryModule

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPhoneAuthRepository(
        impl: PhoneAuthRepositoryImpl
    ): PhoneAuthRepository
}
```

---

## 📁 Firebase Structure

```text
users/
   uid123
      role: "customer"

   uid456
      role: "provider"
      providerProfileCompleted: true

providers/
   uid456
      category: "electrician"
      experience: 5
      verified: false
```

---

## 🧠 Important Learnings

### Why Repository?

* ViewModel remains clean
* Firebase logic stays in data layer
* Easy to test
* Easy to scale

### Why StateFlow?

* Compose automatically observes state changes
* Better than mutable variables for UI state
* Lifecycle-aware when collected properly

---

## 🐞 Errors & Fixes

### Error

```text
No parameter with name 'backgroundColor'
```

### Fix

```kotlin
TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White
)
```

---

## 📅 Daily Progress

### Day 1

* [x] Hilt setup
* [x] Firebase setup
* [x] PhoneAuthUser model

### Day 2

* [x] Repository pattern
* [x] AuthState using StateFlow

### Day 3

* [ ] OTP Authentication
* [ ] Navigation after login

### Day 4

* [ ] Provider Registration Screen
* [ ] Save ProviderModel

### Day 5

* [ ] Location permissions
* [ ] Nearby provider search

---

## 🚀 Next Tasks

* [ ] Implement Send OTP
* [ ] Implement Verify OTP
* [ ] Create Profile Screen
* [ ] Add "Register as a Provider" option
* [ ] Save provider location
* [ ] Add map integration

---

## 💡 Interview Notes

### Explain MVVM

* **View** → Compose UI
* **ViewModel** → UI state management
* **Repository** → Data source abstraction
* **Model** → Business entities

### Explain Hilt

* Provides dependencies automatically
* `@Singleton` gives one instance for the whole app
* `@Inject` allows constructor injection

---

## 🔗 Useful Commands

### Git

```bash
git add .
git commit -m "Implemented OTP authentication flow"
git push
```

### Run App

```bash
./gradlew installDebug
```

---

## 🎯 Final Goal

Build a **production-ready Local Service Provider app** with:

* OTP Authentication
* Customer & Provider roles
* Provider registration
* Nearby provider search
* Ratings & reviews
* Real-time availability
* Clean Architecture + Hilt + Firebase
