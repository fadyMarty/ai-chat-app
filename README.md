<div align="center">

# 🤖 AI Chat

**Android chat application powered by GigaChat AI**

![Kotlin](https://img.shields.io/badge/Kotlin-2.3-7F52FF?style=flat-square&logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2026.02-4285F4?style=flat-square&logo=jetpackcompose)
![GigaChat](https://img.shields.io/badge/GigaChat-REST%20API-FF6B35?style=flat-square)
![Android](https://img.shields.io/badge/Android-API%2024+-3DDC84?style=flat-square&logo=android)

</div>

---

## About

**AI Chat** is an Android application for conversing with the [GigaChat](https://developers.sber.ru/portal/products/gigachat) AI model via its REST API. Responses stream in real-time token by token via Server-Sent Events, AI messages render as rich Markdown with syntax-highlighted code blocks, and voice messages are visualized with an animated audio waveform.

## Features

- 💬 Real-time AI responses via **Server-Sent Events** (token-by-token streaming)
- 📝 Full **Markdown rendering** in AI messages — including code blocks with syntax highlighting
- 🎙️ Voice message support with an **animated audio waveform** visualizer
- 💾 Persistent settings and API token storage via **DataStore**
- 🔍 HTTP logging for debug builds

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.3 |
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation Compose |
| DI | Koin (compose-viewmodel-navigation) |
| Networking | Retrofit 3 + OkHttp 5 |
| Streaming | OkHttp SSE (Server-Sent Events) |
| Serialization | Kotlinx Serialization JSON |
| Storage | DataStore Preferences |
| Markdown | mikepenz multiplatform-markdown-renderer (M3 + code) |
| Audio | compose-audiowaveform + Amplituda |
| Build | Gradle Kotlin DSL + Version Catalogs |

## Architecture

The app follows a clean layered architecture with Koin for dependency injection and a ViewModel-driven UI layer built on Jetpack Compose.

```
app/
├── data/
│   ├── network/       # Retrofit service, SSE client, models
│   └── repository/    # Repository implementations
├── domain/
│   └── usecase/       # Business logic
└── ui/
    ├── chat/          # Chat screen & ViewModel
    └── settings/      # Settings screen
```

## Getting Started

### Prerequisites

- Android Studio Meerkat or later
- JDK 17+
- Android SDK (API 24+)
- [GigaChat API credentials](https://developers.sber.ru/portal/products/gigachat)

### Installation

```bash
git clone https://github.com/fadyMarty/ai-chat-app.git
cd ai-chat-app
```

Add your GigaChat credentials to `local.properties`:

```properties
GIGACHAT_CLIENT_ID=your_client_id
GIGACHAT_CLIENT_SECRET=your_client_secret
```

Then open in Android Studio and run, or build via Gradle:

```bash
./gradlew assembleDebug
```

## ✦ How Streaming Works

GigaChat responses are consumed via **OkHttp Server-Sent Events**. Each SSE chunk contains a partial token that gets appended to the message in the UI as it arrives — giving a live typewriter effect identical to web AI chat interfaces.
