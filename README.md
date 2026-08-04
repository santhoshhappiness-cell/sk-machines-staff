# Daily Activity Tracker App

A beautiful, animated Android app to track your daily activities and tasks with local storage and notifications.

## Features

✨ **Fully Animated UI** - Every interaction has smooth animations and transitions
📝 **Task Management** - Add, edit, delete, and complete tasks
🎨 **Material Design 3** - Modern UI with Material Design principles
💾 **Local Storage** - All data saved locally using Room Database
🔔 **Notifications** - Get notified when tasks are added or completed
⚡ **Fast & Responsive** - Built with Jetpack Compose for optimal performance
📊 **Daily Summary** - Track your progress with activity statistics
🎯 **Priority & Categories** - Organize tasks by priority and category

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Database**: Room
- **Dependency Injection**: Hilt
- **Notifications**: Android WorkManager
- **Architecture**: MVVM

## Building the App

### Prerequisites
- Android Studio (latest version)
- Android SDK 34
- Minimum API Level: 24

### Steps to Build & Install

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd sk-machines-staff
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the project folder
   - Wait for Gradle sync to complete

3. **Build the APK**
   - Go to `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
   - Wait for the build to complete

4. **Install on Device/Emulator**
   - Connect your Android device via USB
   - Enable Developer Mode (tap Build Number 7 times in Settings)
   - Enable USB Debugging
   - Click `Run` → `Run 'app'` in Android Studio
   - Or manually install the generated APK:
     ```bash
     adb install -r app/build/outputs/apk/debug/app-debug.apk
     ```

### Building Release APK

1. Generate a signing key (if you don't have one):
   ```bash
   keytool -genkey -v -keystore release.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias release
   ```

2. In Android Studio:
   - Go to `Build` → `Generate Signed Bundle/APK`
   - Select APK
   - Choose your keystore file and enter credentials
   - Select Release build type
   - Click Finish

3. The release APK will be generated at:
   ```
   app/build/outputs/apk/release/app-release.apk
   ```

## Installation Without Android Studio

1. Download the APK file to your Android device
2. Open a file manager
3. Navigate to the APK file
4. Tap it to install
5. Allow installation from unknown sources (if prompted)

## Usage

### Adding a Task
1. Tap the floating action button (+)
2. Enter task title (required)
3. Add description (optional)
4. Select priority (Low, Medium, High)
5. Choose category (Work, Personal, Health, Shopping, Learning, Other)
6. Tap "Add Task"

### Completing a Task
- Tap the checkmark icon on any task
- Task moves to "Completed" section

### Deleting a Task
- Tap the delete icon on any task
- Task is immediately removed

### Viewing Statistics
- See active and completed task counts at the top
- Expand the "Completed" section to view finished tasks

## Permissions

The app requires:
- `POST_NOTIFICATIONS` - For task reminders and notifications
- `INTERNET` - For future cloud sync features (optional)
- `VIBRATE` - For notification feedback

## Storage

All task data is stored locally on your device using:
- **Room Database**: `/data/data/com.santhosh.dailyactivitytracker/databases/task_database`
- Data is encrypted and backed up by Android

## Notifications

The app sends notifications for:
- ✅ Task completion celebrations
- 📌 Task reminders (if enabled)
- 📊 Daily activity summary (at 8 PM)

## Future Features

- [ ] Cloud sync with Firebase
- [ ] Recurring tasks
- [ ] Task reminders with custom times
- [ ] Export to CSV
- [ ] Offline sync
- [ ] Widgets for home screen
- [ ] Dark/Light theme toggle

## Troubleshooting

### APK Installation Issues
- Ensure your device has enough storage (min 50MB free)
- Clear app cache: Settings → Apps → Daily Activity Tracker → Clear Cache
- Uninstall previous versions before installing new builds

### Notifications Not Working
- Check app permissions: Settings → Apps → Daily Activity Tracker → Permissions
- Ensure "Notifications" is enabled
- Check device battery settings (disable battery optimization for the app)

### Database Errors
- Clear app cache and data
- Uninstall and reinstall the app
- All data will be lost (consider exporting first)

## Performance Tips

- Regularly clear completed tasks to keep the database lean
- Disable notifications if not needed to save battery
- Close other apps if experiencing lag

## Support & Feedback

For issues, suggestions, or feature requests, please create an issue in the repository.

## License

This project is open source and available under the MIT License.

---

**Built with ❤️ for daily productivity**