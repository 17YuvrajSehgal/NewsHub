# NewsHub - Android News App

**NewsHub** is an Android application that delivers the latest news from various categories. It integrates with the [NewsAPI](https://newsapi.org/) to fetch articles and provides features like bookmarking, searching, and language customization to enhance the user experience.

---

## Features

1. **Home News Feed**
    - Displays the latest top headlines.
    - Automatically updates content based on user preferences.

2. **Category-Specific News**
    - Browse news by categories: Health, Science, Sports, and Entertainment.

3. **Search Functionality**
    - Search for articles using keywords.

4. **Bookmarks**
    - Save articles for future reading using Room Database.

5. **Settings**
    - Change app language preferences.
    - Clear all saved bookmarks.

6. **Detailed Reading**
    - Open full articles using an in-app WebView.

7. **Animations and Smooth UI**
    - RecyclerView with custom animations for better user experience.

---

## Setup Instructions

### Prerequisites

- Android Studio installed on your machine.
- A valid API key from [NewsAPI](https://newsapi.org/).

### Steps to Setup

1. **Clone this Repository**
   ```bash
   git clone <repository-url>
   cd NewsHub


## Add Your API Key
Open the `Constants.java` file located in the `com.cosc3p97.newshub` package and replace the placeholder with your actual NewsAPI key:

```java
package com.cosc3p97.newshub;

public class Constants {
    public static final String API_KEY = "PUT_YOUR_KEY_HERE";
}
```

## Build and Run

1. **Open the project in Android Studio.**  
   Make sure you have the latest version of Android Studio installed.

2. **Sync the Gradle files.**  
   This can be done automatically when prompted, or by selecting
   **File → Sync Project with Gradle Files**.

3. **Run the app on an emulator or physical device.**  
   Choose your preferred device configuration in the toolbar and then click
   **Run** (the green play button).


## Code Structure

---

### API Integration

#### **ApiInterface**
- Defines API endpoints for fetching articles using Retrofit.
- **Supported operations**:
  - Fetch top headlines
  - Get articles by category
  - Search articles by keyword

#### **ApiUtilities**
- Configures Retrofit with `OkHttpClient` for handling API requests and responses.

---

### Database

#### **AppDatabase**
- Implements a Room Database to manage bookmarks locally.
- Stores bookmarked articles as `Model` objects.

#### **BookmarkDao**
- Provides methods for adding, removing, and retrieving bookmarks.
- Allows clearing all bookmarks from the database.

---

### UI Components

#### **Fragments**
- **HomeFragment:** Displays top news headlines.  
- **Category Fragments:** Include `HealthFragment`, `ScienceFragment`, `SportsFragment`, and `EntertainmentFragment`. These fetch news articles filtered by category.  
- **SearchFragment:** Handles keyword-based search and displays results.  
- **BookmarkFragment:** Lists all saved bookmarks retrieved from the database.  
- **SettingsFragment:** Allows users to select a preferred language and provides the option to clear bookmarks.

#### **Activities**
- **MainActivity:**  
  - Hosts the app’s navigation and manages fragment transitions.  
  - Includes bottom navigation for switching between categories.
- **ReadNewsActivity:**  
  - Displays a selected article using a `WebView` for detailed reading.

#### **Adapter**
- **Custom RecyclerView Adapter:**  
  - Renders articles in a list.  
  - Handles bookmarking actions.  
  - Implements smooth animations for list items.


## Key Files and Highlights

- **Constants.java**  
  Stores the API key for NewsAPI. Make sure to replace `PUT_YOUR_KEY_HERE` with your actual API key.

- **AndroidManifest.xml**  
  Includes necessary permissions and configures activities and app metadata.

- **Adapter.java**  
  Manages article rendering in `RecyclerView`. Implements click listeners for bookmarking articles and opening full articles in **ReadNewsActivity**.

- **MainActivity.java**  
  Acts as the app's entry point. Manages bottom navigation and handles fragment transitions.

- **ReadNewsActivity.java**  
  Uses a `WebView` to display the full content of articles. Ensures smooth error handling for unsupported pages.

---

## Technologies Used

- **Android SDK**  
- **Retrofit** for API requests  
- **Room Database** for local data storage  
- **Glide** for image loading  
- **RecyclerView** for dynamic UI rendering  
- **WebView** for detailed article reading  

---

## Future Enhancements

1. **Dark Mode**  
   Provide an option for light and dark themes.

2. **Offline Reading**  
   Allow users to save articles locally for offline access.

3. **Push Notifications**  
   Notify users of breaking news or trending topics.

4. **Personalized News Feed**  
   Implement user preferences to tailor the news feed.
