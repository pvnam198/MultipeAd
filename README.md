# Multipead - Multi-Platform Ad Integration

`Multipead` là một ứng dụng Android mẫu, tích hợp nhiều nền tảng quảng cáo (Admob và AppLovin) vào một hệ thống quản lý quảng cáo linh hoạt. Dự án sử dụng các **design pattern** như Strategy, Facade, và Composite để hỗ trợ việc tải và hiển thị quảng cáo banner từ nhiều nhà cung cấp một cách dễ dàng và hiệu quả.

## Mục lục

- [Tổng quan](#tổng-quan)
- [Yêu cầu](#yêu-cầu)
- [Cài đặt](#cài-đặt)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Cách sử dụng](#cách-sử-dụng)
  - [Khởi tạo quảng cáo](#khởi-tạo-quảng-cáo)
  - [Tích hợp BannerAdManager](#tích-hợp-banneradmanager)
- [Các thành phần chính](#các-thành-phần-chính)
- [Góp ý và hỗ trợ](#góp-ý-và-hỗ-trợ)

## Tổng quan

Dự án `Multipead` cung cấp một hệ thống quản lý quảng cáo banner, cho phép tích hợp và chuyển đổi giữa các nền tảng quảng cáo (Admob và AppLovin) một cách liền mạch. Các tính năng chính bao gồm:

- **Tích hợp đa nền tảng**: Hỗ trợ cả Admob và AppLovin với cấu hình riêng biệt.
- **Quản lý linh hoạt**: Sử dụng `BannerAdManager` để tải quảng cáo theo thứ tự ưu tiên, thử nền tảng tiếp theo nếu một nền tảng thất bại.
- **Hiển thị quảng cáo**: Sử dụng `BannerRendererManager` để hiển thị quảng cáo phù hợp với từng nền tảng.
- **Quản lý lifecycle**: Hỗ trợ các hành động `resume`, `pause`, và `destroy` để đảm bảo quảng cáo hoạt động đúng trong vòng đời của Activity.

Dự án được viết bằng **Kotlin** và sử dụng các thư viện Android tiêu chuẩn, cùng với SDK của Admob và AppLovin.

## Yêu cầu

Để chạy dự án, bạn cần:

- **Android Studio**: Phiên bản Arctic Fox (2020.3.1) hoặc mới hơn.
- **Kotlin**: Phiên bản 1.5 hoặc cao hơn.
- **SDK Android**: API 21 (Lollipop) trở lên.
- **Thư viện phụ thuộc**:
  - Google Mobile Ads SDK (`com.google.android.gms:play-services-ads`)
  - AppLovin MAX SDK (`com.applovin:applovin-sdk`)
- **Mạng Internet**: Để tải và hiển thị quảng cáo.

## Cài đặt

1. **Clone repository**:
   ```bash
   git clone <repository-url>
   ```

2. **Mở dự án trong Android Studio**:
   - Mở Android Studio và chọn `Open an existing project`.
   - Chọn thư mục gốc của dự án `multipead`.

3. **Thêm phụ thuộc**:
   Trong tệp `build.gradle` (module app), đảm bảo các phụ thuộc sau được thêm:
   ```gradle
   dependencies {
       implementation 'com.google.android.gms:play-services-ads:22.6.0'
       implementation 'com.applovin:applovin-sdk:12.4.1'
   }
   ```

4. **Cấu hình Manifest**:
   Đảm bảo tệp `AndroidManifest.xml` có quyền truy cập Internet:
   ```xml
   <uses-permission android:name="android.permission.INTERNET"/>
   ```

5. **Đồng bộ dự án**:
   Nhấn `Sync Project with Gradle Files` trong Android Studio để tải các phụ thuộc.

6. **Cấu hình khóa quảng cáo**:
   - **Admob**: Cập nhật `adUnitId` trong `AdmobBannerConfig` (file `MainActivity.kt`) với ID quảng cáo  thật của bạn.
   - **AppLovin**: Cập nhật `sdkKey` và `adUnitId` trong `App.kt` và `MainActivity.kt` với thông tin từ tài khoản AppLovin của bạn.

## Cấu trúc dự án

Dự án được tổ chức theo các package chính:

- **`com.wz.multipead`**:
  - `App.kt`: Khởi tạo ứng dụng và các SDK quảng cáo (Admob, AppLovin).
  - `MainActivity.kt`: Activity chính, nơi tích hợp và hiển thị quảng cáo banner.
- **`com.ads`**:
  - `AdInitializationManager.kt`: Quản lý khởi tạo các SDK quảng cáo.
  - `AdNetworkType.kt`: Enum định nghĩa các nền tảng quảng cáo (Admob, AppLovin).
- **`com.ads.admob`**: Chứa các lớp liên quan đến Admob (loader, renderer, config, result).
- **`com.ads.applovin`**: Chứa các lớp liên quan đến AppLovin (loader, renderer, config, result).
- **`com.ads.banner`**:
  - `loader`: Interface và triển khai để tải quảng cáo.
  - `manager`: `BannerAdManager` để quản lý việc tải quảng cáo.
  - `model`: Các model như `BannerAdConfig`, `BannerResult`, `BannerSize`.
  - `render`: `BannerRenderer` và `BannerRendererManager` để hiển thị quảng cáo.

## Cách sử dụng

### Khởi tạo quảng cáo

Quảng cáo được khởi tạo trong `App.kt` thông qua `AdInitializationManager`. Để thêm một nền tảng quảng cáo mới:

1. Tạo một class triển khai `AdInitializer` (tương tự `AdMobAdInitializer` hoặc `ApplovinAdInitializer`).
2. Thêm initializer vào danh sách trong `App.kt`:
   ```kotlin
   initializers.add(YourNewAdInitializer(context))
   ```
3. Gọi `AdInitializationManagerImpl(initializers).initialize()`.

### Tích hợp BannerAdManager

Để hiển thị quảng cáo banner trong một Activity:

1. **Tạo danh sách cặp AdNetworkType và BannerAdConfig**:
   ```kotlin
   val adNetworkConfigs = listOf(
       AdNetworkType.ADMOB to AdmobBannerConfig(adUnitId = "your_admob_ad_unit_id"),
       AdNetworkType.APPLOVIN to ApplovinBannerConfig(
           adUnitId = "your_applovin_ad_unit_id",
           parentView = binding.flBannerAd
       )
   )
   ```
   - Danh sách này xác định thứ tự ưu tiên: Admob được thử trước, nếu thất bại sẽ thử AppLovin.

2. **Khởi tạo BannerAdManager**:
   ```kotlin
   bannerAdManager = BannerAdManagerImpl(this, adNetworkConfigs)
   ```

3. **Tải và hiển thị quảng cáo**:
   ```kotlin
   bannerAdManager.fetchBannerAd(
       onSuccess = { result ->
           binding.tvBannerAdIsLoading.visibility = View.GONE
           binding.flBannerAd.visibility = View.VISIBLE
           val bannerRendererManager = BannerRendererManager()
           bannerRendererManager.registerRenderer(
               AdmobBannerRenderer { binding.flBannerAd.addView(it) }
           )
           bannerRendererManager.registerRenderer(
               ApplovinBannerRenderer { binding.flBannerAd.addView(it) }
           )
           bannerRendererManager.render(result)
           currentBanner = result
       },
       onFailure = {
           binding.flBannerAd.visibility = View.GONE
       }
   )
   ```
   - Hệ thống sẽ thử tải quảng cáo từ Admob trước. Nếu thành công, quảng cáo được hiển thị và quá trình dừng lại. Nếu thất bại, nó thử AppLovin. Nếu cả hai thất bại, khung quảng cáo bị ẩn.

4. **Quản lý lifecycle**:
   Đảm bảo gọi các phương thức lifecycle trong Activity để quản lý quảng cáo đúng cách:
   ```kotlin
   override fun onResume() {
       super.onResume()
       (currentBanner as? BannerResumeAble)?.resume()
   }
   
   override fun onPause() {
       super.onPause()
       (currentBanner as? BannerPauseAble)?.pause()
   }
   
   override fun onDestroy() {
       super.onDestroy()
       (currentBanner as? BannerDestroyable)?.destroy()
   }
   ```

## Các thành phần chính

1. **BannerAdManager**:
   - Quản lý việc tải quảng cáo từ nhiều nền tảng theo thứ tự ưu tiên.
   - Nhận danh sách cặp `(AdNetworkType, BannerAdConfig)` để đảm bảo mỗi nền tảng sử dụng config đúng.
   - Thử tải quảng cáo từ nền tảng đầu tiên trong danh sách; nếu thất bại, chuyển sang nền tảng tiếp theo.

2. **BannerRendererManager**:
   - Hiển thị quảng cáo bằng cách chọn renderer phù hợp (`AdmobBannerRenderer` hoặc `ApplovinBannerRenderer`) dựa trên loại `BannerResult`.
   - Sử dụng phương thức `canRender` để kiểm tra khả năng hiển thị.

3. **BannerLoader**:
   - Interface chung cho các loader (`AdmobBannerLoader`, `ApplovinBannerLoader`).
   - Mỗi loader chịu trách nhiệm tải quảng cáo từ nền tảng tương ứng.

4. **BannerResult**:
   - Interface biểu diễn kết quả quảng cáo, với các triển khai cụ thể như `AdmobBannerResult` và `ApplovinBannerResult`.
   - Hỗ trợ các hành động lifecycle (`resume`, `pause`, `destroy`).

## Góp ý và hỗ trợ

- **Báo lỗi**: Nếu gặp vấn đề, vui lòng tạo issue trên repository với mô tả chi tiết.
- **Đóng góp**: Fork repository, tạo pull request với các cải tiến hoặc sửa lỗi.
- **Liên hệ**: Liên hệ qua email hoặc các kênh hỗ trợ của dự án.

---

**Lưu ý**: Đảm bảo thay thế `adUnitId` và `sdkKey` bằng các giá trị thật từ tài khoản Admob và AppLovin của bạn trước khi chạy ứng dụng.