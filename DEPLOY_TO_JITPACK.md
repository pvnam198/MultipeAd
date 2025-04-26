# 📦 Deploy Library lên JitPack

## 1. Chuẩn bị project

- Repo phải **Public** trên GitHub.
- Cấu trức thư mục project chuẩn.
- Thêm `group` và `version` vào `build.gradle` của module:

```gradle
group = 'com.github.pvnam198'
version = '1.0.0'
```

## 2. Tạo Git Tag

JitPack yêu cầu phải có tag version.

```bash
git tag 1.0.0
git push origin 1.0.0
```

**Hoặc** vào GitHub → Releases → Draft a new release → Điền `1.0.0` → Publish.

## 3. Build trên JitPack

- Truy cập [https://jitpack.io/](https://jitpack.io/)
- Dán link GitHub repo → Click **Lookup** → Chọn version → **Get it!**
- Nếu build thành công, bạn sẽ thấy hướng dẫn để import lib.

## 4. Sử dụng lib (ví dụ)

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.pvnam198:your-library-name:1.0.0'
}
```

---

# 🌟 Ghi chú

- Nếu có lỗi build, kiểm tra lại:
  - `group` và `version` đã đúng chưa.
  - Đã push tag chưa.
  - Project có thể build thành công local không.
- Để repo **private** thì phải cấp token GitHub cho JitPack (không khuyến khích).

---

# ✅ Done!
