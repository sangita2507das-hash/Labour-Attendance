LABOUR ATTENDANCE - ANDROID APK PROJECT

এই ZIP-এ Android Studio project এবং GitHub Actions workflow দেওয়া আছে। এখানে তৈরি APK এখনো নেই; APK build করতে Android SDK/Gradle প্রয়োজন।

সহজে APK বানানোর পদ্ধতি (কম্পিউটার/ব্রাউজার দিয়ে):
1. ZIP ফাইল Extract করুন।
2. GitHub.com-এ সাইন ইন করে New repository তৈরি করুন (যেমন labour-attendance)।
3. Extract করা ফোল্ডারের ভেতরের সব ফাইল GitHub repository-তে upload করুন। .github ফোল্ডারটিও অবশ্যই upload করবেন।
4. GitHub repository-র Actions tab খুলুন। প্রথমবার চাইলে “I understand my workflows, go ahead and enable them” চাপুন।
5. “Build Labour Attendance APK” workflow নির্বাচন করে “Run workflow” চাপুন।
6. Build সফল হলে Actions-এর ওই run-এর নিচে Artifacts থেকে “Labour-Attendance-APK” ডাউনলোড করুন।
7. ZIP extract করলে app-debug.apk পাবেন। Android ফোনে APK খুলে Install দিন। প্রয়োজন হলে Android Settings-এ “Install unknown apps” অনুমতি দিন।

অ্যাপের সুবিধা:
- সর্বোচ্চ ৫টি সাইট; নাম যোগ, পরিবর্তন ও মুছে ফেলা
- শ্রমিক যোগ, সম্পাদনা ও মুছে ফেলা
- Full Day / Half Day / Absent
- Daily Wage ও Monthly Salary হিসাব
- Attendance report, CSV export, backup ও restore
- অ্যাপের ভেতরের নাম ও লোগো পরিবর্তন
- ডেটা ফোনের অ্যাপের ভেতরে লোকালি থাকে

গুরুত্বপূর্ণ সীমাবদ্ধতা:
- এটি debug APK হবে, Play Store-এ প্রকাশের জন্য release signing আলাদা করতে হবে।
- Settings-এ বদলানো নাম অ্যাপের ভেতরে বদলাবে; ফোনের Home Screen/launcher-এ দেখা ইনস্টল করা অ্যাপের নাম বদলাতে নতুন APK build করতে হবে।
- অ্যাপ uninstall করলে লোকাল ডেটা মুছে যেতে পারে। Settings থেকে নিয়মিত Backup নিন।
