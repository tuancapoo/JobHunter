
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
