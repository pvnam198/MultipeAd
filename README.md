# 📢 Ads Initialization - Hệ thống khởi tạo quảng cáo

## ✅ Mục tiêu
Tổ chức lại quá trình **khởi tạo quảng cáo** trong ứng dụng Android một cách rõ ràng, có cấu trúc, dễ mở rộng và bảo trì.  
Hỗ trợ nhiều ad network như: **AppLovin**, **AdMob**, v.v.

---

## 🔧 Cấu trúc hệ thống

### 1. `AdInitializer` – Interface cơ bản

```kotlin
interface AdInitializer {
    fun initialize()
}
```

### 2. Các `AdInitializer` cụ thể

#### AppLovin (có hỗ trợ Test Device IDs)

```kotlin
class AppLovinAdInitializer(
    private val context: Context,
    private val sdkKey: String,
    private val testDeviceIds: List<String> = emptyList(),
    private val onInitializeComplete: (() -> Unit)? = null
) : AdInitializer {

    override fun initialize() {
        val initConfig =
            AppLovinSdkInitializationConfiguration.builder(sdkKey)
                .setMediationProvider(AppLovinMediationProvider.MAX)
                .setTestDeviceAdvertisingIds(testDeviceIds)
                .build()

        AppLovinSdk.getInstance(context).initialize(initConfig) { sdkConfig ->
            onInitializeComplete?.invoke()
        }
    }
}
```

#### AdMob

```kotlin
class AdMobAdInitializer(
    private val context: Context,
    private val onInitializeComplete: (() -> Unit)? = null
) : AdInitializer {
    override fun initialize() {
        MobileAds.initialize(context) {
            onInitializeComplete?.invoke()
        }
    }
}
```

### 3. `AdInitializationManager` – Giao diện quản lý chính

```kotlin
interface AdInitializationManager {
    fun initialize()
}
```

### 4. `AdInitializationManagerImpl` – Quản lý danh sách các network

```kotlin
class AdInitializationManagerImpl(
    private val initializers: List<AdInitializer>
) : AdInitializationManager {

    override fun initialize() {
        initializers.forEach { it.initialize() }
    }

}
```

---

## 🚀 Cách **KHỞI TẠO**

Thực hiện trong `Application`:

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeAds()
    }

    private fun initializeAds() {
        val configReader: ConfigReader = ConfigReaderImpl(this)
        val initializers = ArrayList<AdInitializer>()
        getMaxAdInitializer(configReader)?.let { initializers.add(it) }

        val adMobAdInitializer = AdMobAdInitializer(context = this)
        initializers.add(adMobAdInitializer)

        val adInitializationManager =
            AdInitializationManagerImpl(initializers)
        adInitializationManager.initialize()
    }

    private fun getMaxAdInitializer(configReader: ConfigReader): AdInitializer? {
        val sdkKey = configReader.readProperty("applovin.sdk.key") ?: return null

        val testDeviceIds = configReader.readProperty("applovin.test.device.ids")
            ?.split(",")
            ?.map { it.trim() }
            .orEmpty()

        return AppLovinAdInitializer(
            context = this,
            sdkKey = sdkKey,
            testDeviceIds = testDeviceIds
        )
    }
}
```

---

## ⚙️ Cấu hình SDK và plugin

### 1. Khai báo trong `libs.versions.toml`

```toml
[versions]
playServicesAds = "24.2.0"
applovinSdk = "13.2.0"
applovinqualityservicegradleplugin = "5.8.2"

[libraries]
play-services-ads = { module = "com.google.android.gms:play-services-ads", version.ref = "playServicesAds" }
applovin-sdk = { module = "com.applovin:applovin-sdk", version.ref = "applovinSdk" }
applovinqualityservicegradleplugin = { module = "com.applovin.quality:AppLovinQualityServiceGradlePlugin", version.ref = "applovinqualityservicegradleplugin" }

[plugins]
android-application = { id = "com.android.application", version = "8.9.1" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version = "2.1.20" }
```

### 2. Sử dụng trong `build.gradle.kts` của app module

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("applovin-quality-service")
}

applovin {
    apiKey = "your_applovin_api_key"
}

dependencies {
    implementation(libs.play.services.ads)
    implementation(libs.applovin.sdk)
}
```

### 3. Root `build.gradle.kts`

```kotlin
buildscript {
    repositories {
        maven { url = uri("https://artifacts.applovin.com/android") }
    }
    dependencies {
        classpath(libs.applovinqualityservicegradleplugin)
    }
}
```

---

## 🛠️ Cấu hình `local.properties` để bảo mật SDK Key và Test Device IDs

Thay vì hardcode vào source code, bạn nên cấu hình các giá trị nhạy cảm trong `local.properties` (đã mặc định bị ignore bởi Git):

### 🔹 Bước 1: Mở hoặc tạo file `local.properties` tại thư mục gốc project

```properties
# local.properties

applovin.sdk.key=your_real_applovin_sdk_key_here
applovin.test.device.ids=your_test_device_id_here
```

> Có thể thêm nhiều thiết bị test, phân cách bằng dấu `,`:
```properties
applovin.test.device.ids=abc-123,def-456
```

---

### 🔹 Bước 2: Tạo interface `ConfigReader` và implementation `ConfigReaderImpl`

```kotlin
// interface ConfigReader.kt
interface ConfigReader {
    fun readProperty(key: String): String?
}
```

```kotlin
// implementation ConfigReaderImpl.kt
class ConfigReaderImpl(
    private val context: Context
) : ConfigReader {

    override fun readProperty(key: String): String? {
        return try {
            val file = File(findRootDir(context), "local.properties")
            if (!file.exists()) return null
            val properties = Properties().apply { load(file.inputStream()) }
            properties.getProperty(key)
        } catch (_: Exception) {
            null
        }
    }

    private fun findRootDir(context: Context): File {
        var dir = context.filesDir
        while (dir.parentFile?.name != null && dir.parentFile?.name != "src") {
            dir = dir.parentFile!!
        }
        return dir.parentFile ?: context.filesDir
    }
}
```

---

## 🧪 Lấy Advertising ID để dùng làm test ID

```kotlin
val thread = Thread(object : Runnable {
    override fun run() {
        val info = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
        val id = info.id
        Log.d("TestAdId", "Advertising ID: $id")
    }
})
thread.start()
```

---

## 🧩 Mở rộng

- Khởi tạo quảng cáo có điều kiện theo BuildConfig, Firebase hoặc user đã mua VIP.
- Có thể kết hợp với Hilt hoặc Koin để inject `AdInitializer` hoặc `ConfigReader`.
---

## 📢 Hướng dẫn thêm Banner Ad vào Activity

### 🧩 Cách tích hợp

Trong `MainActivity`, bạn có thể cấu hình Banner Ad như sau:

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var bannerAdManager: IBannerAdManager
    private var bannerAd: BannerAd<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo ad manager với network tương ứng
        bannerAdManager = BannerAdManager(this, AdNetworkType.APPLOVIN)

        val bannerAdConfig = MaxBannerConfig(
            adUnitId = "your_applovin_banner_id",
            parentView = binding.flBannerAd
        )

        // Gọi load banner
        bannerAdManager.fetchBannerAd(config = bannerAdConfig, onSuccess = {
            bannerAd = it
            val adView = it.ad
            if (adView is View) {
                binding.flBannerAd.addView(adView)
            }
            binding.tvBannerAdIsLoading.visibility = View.GONE
            binding.flBannerAd.visibility = View.VISIBLE
        }, onFailure = {
            binding.tvBannerAdIsLoading.text = "Load failed: $it"
            binding.flBannerAd.visibility = View.GONE
        })
    }

    override fun onResume() {
        super.onResume()
        (bannerAd as? BannerResumeAble)?.resume()
    }

    override fun onPause() {
        super.onPause()
        (bannerAd as? BannerPauseAble)?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        (bannerAd as? BannerDestroyable)?.destroy()
    }
}
```

---

### 🧱 Các bước triển khai:

1. Tạo `BannerAdManager` với loại network: `AdNetworkType.APPLOVIN` hoặc `ADMOB`.
2. Gọi `fetchBannerAd(...)` để load banner.
3. Nhúng view banner vào `ViewGroup` như `FrameLayout`, ví dụ: `binding.flBannerAd`.
4. Gọi `.pause()`, `.resume()`, `.destroy()` theo vòng đời của Activity nếu hỗ trợ.

---

### 💡 Gợi ý `layout/activity_main.xml`

```xml
<FrameLayout
    android:id="@+id/fl_banner_ad"
    android:layout_width="match_parent"
    android:layout_height="56dp">

    <TextView
        android:id="@+id/tv_banner_ad_is_loading"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:gravity="center"
        android:text="Banner ad is loading..." />

</FrameLayout>
```

---

Bạn có thể mở rộng hệ thống này để hỗ trợ banner linh hoạt với nhiều vị trí, nhiều network, và kiểm soát refresh/timeout tự động.
