
# JobHunter (Starter)

Một starter project Spring Boot bằng Java để triển khai RESTful API cho hệ thống JobHunter.

Mục đích README này:
- Mô tả ngắn dự án.
- Hướng dẫn cách build, chạy và cấu hình JWT (access + refresh tokens).
- Gợi ý các endpoint mẫu và nơi chứa các request mẫu.

Tiền đề
- Dự án sử dụng Gradle (wrapper có sẵn) và Spring Boot.
- Các cấu hình chính đặt trong `src/main/resources/application.properties`.

Cấu trúc chính (tóm tắt)
- `src/main/java` - mã nguồn Java.
- `src/main/resources` - cấu hình và template tĩnh.
- `requestHttp/` - thư mục chứa các file HTTP (sử dụng tiện ích HTTP client trong IDE) để thử API.

Yêu cầu
- Java 17+ (hoặc phiên bản tương thích với project). Nếu đang dùng Gradle wrapper thì sẽ tự download wrapper-specified Gradle.
- Git (tùy chọn).

Cấu hình JWT (quan trọng)
Dự án dùng `SecurityUtil` để tạo và kiểm tra Access/Refresh tokens. Các cấu hình JWT nằm trong `application.properties` (hoặc cấu hình môi trường):

- `tuan.jwt.base64-secret` - khóa bí mật mã hóa token (Base64). Bắt buộc phải an toàn và đủ dài cho thuật toán HS512.
- `tuan.jwt.access-token-validity-in-seconds` - thời gian sống của access token (giây).
- `tuan.jwt.refresh-token-validity-in-seconds` - thời gian sống của refresh token (giây).

Ví dụ (đặt trong `application.properties` hoặc dưới biến môi trường):

# application.properties (ví dụ)
# tuan.jwt.base64-secret=REPLACE_WITH_YOUR_BASE64_SECRET
# tuan.jwt.access-token-validity-in-seconds=3600
# tuan.jwt.refresh-token-validity-in-seconds=2592000

Lưu ý: Đừng commit khóa thật vào git. Tạo một chuỗi ngẫu nhiên và mã hóa Base64. Một ví dụ ngắn (không an toàn cho production): `bXlzZWNyZXRrZXkxMjM0NTY=`.

Các lệnh thông dụng (PowerShell trên Windows)
- Build project:
```
.\gradlew.bat clean build
```
- Chạy ứng dụng (theo Spring Boot):
```
.\gradlew.bat bootRun
```
- Chạy jar sau khi build:
```
java -jar build\libs\jobhunter-0.0.1-SNAPSHOT.jar
```
- Chạy test:
```
.\gradlew.bat test
```

Cách dùng JWT trong project
- `SecurityUtil#createAccessToken(email, ResLoginDTO)` trả về access token chứa thông tin user và quyền (claim `permission`).
- `SecurityUtil#createRefreshToken(email, ResLoginDTO)` trả về refresh token.
- `SecurityUtil#checkValidRefreshToken(token)` giải mã và xác thực refresh token.
- `SecurityUtil#getCurrentUserLogin()` và `getCurrentUserJWT()` giúp lấy thông tin user/token hiện tại từ SecurityContext.

Ví dụ gọi API (curl thích hợp trên Linux/macOS; trên Windows dùng HTTP client hoặc Postman):
- Đăng nhập (ví dụ):
  - Gửi request tới endpoint login (xem `requestHttp/Auth/login.http` hoặc file tương ứng trong `requestHttp/`).
  - Nhận về `accessToken` và `refreshToken`.

- Gọi API bảo vệ bằng Bearer token (ví dụ curl):
```
curl -H "Authorization: Bearer <ACCESS_TOKEN>" http://localhost:8080/api/some-protected-endpoint
```

- Lấy access token mới bằng refresh token: gửi refresh token tới endpoint refresh (project thường có endpoint xử lý refresh). Xem file `requestHttp/Auth/GetNewRefreshToken.http` để biết cách gửi.

Thử nghiệm nhanh (IDE)
- Mở thư mục `requestHttp/` trong IDE (hoặc dùng Postman) để thử các request có sẵn.

Gợi ý bảo mật và triển khai
- Không lưu `tuan.jwt.base64-secret` trong mã nguồn. Sử dụng biến môi trường hoặc công cụ quản lý secret (Vault, AWS Secrets Manager, v.v.).
- Sử dụng HTTPS trong production.
- Chọn secret đủ mạnh (ít nhất 256-bit cho HS256; HS512 đòi hỏi dài hơn).

Thêm:
- Mục `requestHttp/` chứa nhiều file .http để bạn thử endpoints (Auth, Users, Companies, Job, Resume, Skill, v.v.).
- Nếu muốn thay đổi cấu trúc token hoặc thêm claims, sửa `SecurityUtil.java`.

Hỗ trợ & phát triển
- Nếu cần các ví dụ cụ thể hơn (ví dụ: cài đặt biến môi trường CI/CD, script tạo base64 key, hoặc sửa `SecurityUtil` để dùng RSA keys), cho biết yêu cầu cụ thể để mình bổ sung.

Bản quyền
- Xem file `LICENSE.MD` và `LICENSE_VI.MD` trong repository để biết chi tiết license.


Yêu cầu kiểm tra (quick checklist):
- [x] README bằng tiếng Việt mô tả mục đích.
- [x] Hướng dẫn build/run/test.
- [x] Hướng dẫn cấu hình JWT cơ bản.
- [x] Tham chiếu tới các request mẫu trong `requestHttp/`.


