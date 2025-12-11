# Thmanyah Podcast App

A modern Android podcast application built with Jetpack Compose, following Clean Architecture
principles and MVVM pattern.

## 🎬 Demo

https://github.com/user-attachments/assets/6761a3cd-822d-4c03-b255-55e3c932e73b

## 📱 Features

### Home Screen

- **Dynamic Sections**: Fetches and displays multiple podcast sections from API
- **Multiple Layout Types**: Supports different section layouts based on API response:
    - `square` - Grid layout
    - `2_lines_grid` - Horizontal grid with 2 rows
    - `queue` - Horizontal scrolling list
    - `big_square` - Large featured items
- **Content Type Filtering**: Dynamic filter chips extracted from API response
- **Pagination**: Infinite scroll with proper duplicate page prevention
- **Real-time Network Monitoring**: Auto-retry when connection restored
- **Offline Support**: Shows offline banner and cached data

### Search Screen

- **Debounced Search**: 200ms debounce to prevent excessive API calls
- **Distinct Queries**: Prevents redundant API calls for identical queries
- **Loading/Empty/Error States**: Clear visual feedback for all states

## 🏗️ Architecture

This project follows **Clean Architecture** with a multi-module structure:

```
┌─────────────────────────────────────────────────────────────────┐
│                        APP MODULE                                │
│   UI (Compose) • ViewModels • Navigation • DI Setup              │
└─────────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────────┐
│                   CORE:DESIGNSYSTEM MODULE                       │
│   Theme • Components • Tokens • Colors • Typography              │
└─────────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────────┐
│                      DOMAIN MODULE                               │
│   Use Cases • Models • Repository Interfaces • AppError          │
│   NetworkMonitor Interface • DataState                           │
└─────────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────────┐
│                       DATA MODULE                                │
│   Repository Impl • Datasources • API • Mappers                  │
│   NetworkMonitorImpl • ErrorMapper                               │
└─────────────────────────────────────────────────────────────────┘
```

## 🛠️ Tech Stack

| Category                 | Technology                |
|--------------------------|---------------------------|
| **Language**             | Kotlin                    |
| **UI Framework**         | Jetpack Compose           |
| **Architecture**         | MVVM + Clean Architecture |
| **Dependency Injection** | Koin                      |
| **Networking**           | Retrofit + OkHttp         |
| **Serialization**        | Gson                      |
| **Async**                | Coroutines + Flow         |
| **Image Loading**        | Coil 3                    |
| **Navigation**           | Compose Navigation        |
| **Logging**              | Timber                    |
| **Testing**              | JUnit, MockK, Turbine     |

## 📂 Project Structure

```
TmanyahPodcastTask/
├── app/                           # Presentation Layer
│   └── src/main/java/.../
│       ├── di/                    # Koin DI setup
│       ├── navigation/            # Bottom navigation
│       ├── ui/
│       │   ├── home/              # Home screen + ViewModel
│       │   │   └── components/    # Content item composables
│       │   └── search/            # Search screen + ViewModel
│       └── util/                  # ErrorMessageResolver
│
├── core/designsystem/             # UI Foundation
│   └── src/main/java/.../
│       ├── theme/                 # Colors, Typography, Theme
│       └── components/            # Reusable UI components
│           ├── buttons/
│           ├── cards/
│           ├── chips/
│           ├── images/
│           ├── loading/
│           ├── states/
│           └── text/
│
├── domain/                        # Business Logic Layer
│   └── src/main/java/.../
│       ├── error/                 # AppError sealed class
│       ├── network/               # NetworkMonitor interface
│       ├── models/                # Domain entities
│       ├── repository/            # Repository interfaces
│       ├── usecases/              # Business logic
│       └── utilis/                # DataState
│
├── data/                          # Data Layer
│   └── src/main/java/.../
│       ├── di/                    # Network & Data modules
│       ├── error/                 # ErrorMapper
│       ├── network/               # NetworkMonitorImpl
│       ├── datasource/            # RemoteDatasourceImpl
│       ├── mappers/               # DTO to Domain mappers
│       ├── remote/
│       │   ├── api/               # Retrofit interfaces
│       │   └── response/          # API response DTOs
│       └── repository/            # Repository implementations
│
└── apiurl.properties              # API configuration
```

## 🔧 Configuration

### API Configuration

Base URLs are configured in `apiurl.properties`:

```properties
BASE_URL="https://api-v2-b2sit6oh3a-uc.a.run.app/"
SEARCH_BASE_URL="https://mock.apidog.com/m1/735111-711675-default/"
```

### Endpoints

| Endpoint                      | Description                     |
|-------------------------------|---------------------------------|
| `GET /home_sections?page={n}` | Fetches paginated home sections |
| `GET /search?q={query}`       | Searches for podcasts           |

## 🧪 Testing

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific module tests
./gradlew :app:test
./gradlew :data:test
./gradlew :domain:test
```

### Test Coverage

| Module     | Tests                                               | Coverage                              |
|------------|-----------------------------------------------------|---------------------------------------|
| **app**    | HomeViewModelTest, SearchViewModelTest              | Pagination, filters, debounce, errors |
| **data**   | PodcastRepositoryImplTest, SearchRepositoryImplTest | Success/error flows                   |
| **domain** | FetchPodcastsUseCaseTest, SearchPodcastsUseCaseTest | Use case logic                        |

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11+
- Android SDK 35 (min SDK 26)

### Build & Run

```bash
# Clone and build
git clone <repository>
cd TmanyahPodcastTask
./gradlew assembleDebug

# Run tests
./gradlew test
```

## 🎨 Design System

The app includes a dedicated `core:designsystem` module with:

- **Theme**: Material 3 with custom Thmanyah colors
- **Dark/Light Mode**: Full support with automatic switching
- **Components**: Reusable buttons, cards, chips, loading states
- **Tokens**: Spacing, elevation, radius constants
- **Typography**: Custom text styles

## ⚡ Key Features Implementation

### Error Handling

- Type-safe `AppError` sealed class
- `ErrorMapper` converts exceptions to domain errors
- Localized error messages (English/Arabic)

### Network Monitoring

- Real-time connectivity status via `NetworkMonitor`
- Auto-retry when connection restored
- Offline banner in UI

### Pagination

- Parses `next_page` from API response
- Prevents duplicate page fetches
- Proper loading states