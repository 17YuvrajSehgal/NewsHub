# NewsHub - Android News App

**NewsHub** is an Android application designed to provide users with the latest news across various categories. The app integrates APIs to fetch news articles, provides bookmarking functionality, and offers a smooth and user-friendly interface for browsing, searching, and categorizing news.

---

## Features

1. **Home News Feed**:
    - Displays the latest news headlines.
    - Supports filtering by language preferences.

2. **Category-Specific News**:
    - Browse news articles by categories like Health, Science, and more.

3. **Search Functionality**:
    - Search for news articles based on keywords.

4. **Bookmarks**:
    - Save articles for later reading using a Room database.

5. **Settings**:
    - Change app language preferences.
    - Clear all saved bookmarks.

---

## Code Structure

### API Integration

- **`ApiInterface`** (&#8203;:contentReference[oaicite:0]{index=0}):
    - Defines API endpoints to fetch news articles using `Retrofit`.
    - Supported operations:
        - Fetch top headlines.
        - Get news by category.
        - Search news articles by query.

- **`ApiUtilities`** (&#8203;:contentReference[oaicite:1]{index=1}):
    - Configures `Retrofit` with logging and a custom `OkHttpClient` for debugging API requests.

### Database

- **`AppDatabase`** (&#8203;:contentReference[oaicite:2]{index=2}):
    - Manages local data storage using Room database.
    - Stores bookmarked articles.

- **`BookmarkDao`** (&#8203;:contentReference[oaicite:3]{index=3}):
    - Provides methods to:
        - Insert, delete, and retrieve bookmarks.
        - Clear all bookmarks.

### Fragments

- **`HomeFragment`** (&#8203;:contentReference[oaicite:4]{index=4}):
    - Fetches and displays top headlines.
    - Utilizes `RecyclerView` and `Adapter` for displaying articles.

- **`HealthFragment`** (&#8203;:contentReference[oaicite:5]{index=5}) and **`ScienceFragment`** (&#8203;:contentReference[oaicite:6]{index=6}):
    - Fetches category-specific news (e.g., Health, Science).
    - Filters articles for quality and relevance.

- **`SearchFragment`** (&#8203;:contentReference[oaicite:7]{index=7}):
    - Allows users to search for news articles by keywords.
    - Dynamically updates the `RecyclerView` based on search results.

- **`BookmarkFragment`** (&#8203;:contentReference[oaicite:8]{index=8}):
    - Displays a list of bookmarked articles.
    - Fetches data from the Room database.

- **`SettingsFragment`** (&#8203;:contentReference[oaicite:9]{index=9}):
    - Allows users to change app language.
    - Provides an option to clear all bookmarks.

---

## Setup Instructions

1. Clone this repository:
   ```bash
   git clone <repository-url>
   cd NewsHub
