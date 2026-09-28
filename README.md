# BÀI TẬP 9: TÌM HIỂU VÀ THỰC HÀNH SPRING SECURITY 7 + SPRING BOOT + MAPSTRUCT + THYMELEAF + SQL SERVER

- **Môn học:** Lập trình Web (WEBPR330479)
- **Giảng viên hướng dẫn:** ThS. Nguyễn Hữu Trung
- **Sinh viên thực hiện:** Trương Quốc Duy
- **Mã số sinh viên (MSSV):** 24133009
- **Email:** 24133009@student.hcmute.edu.vn

---

## MỤC LỤC
1. [Tổng quan lý thuyết Spring Security](#1-tổng-quan-lý-thuyết-spring-security)
2. [Cấu trúc thư mục Workspace](#2-cấu-trúc-thư-mục-workspace)
3. [Chi tiết các bài thực hành](#3-chi-tiết-các-bài-thực-hành)
   - [Ví dụ 1: springboot-security-vd1 (Login cơ bản + Layout thuần)](#ví-dụ-1-springboot-security-vd1)
   - [Ví dụ 2: springboot-security-vd2 (Custom Login Username/Email + Avatar + Dialect)](#ví-dụ-2-springboot-security-vd2)
   - [Ví dụ 3: shop-springboot-4-1-1 (Hệ thống Shop Full Chức Năng)](#ví-dụ-3-shop-springboot-4-1-1)
4. [Hướng dẫn Cài đặt & Khởi chạy](#4-hướng-dẫn-cài-đặt--khởi-chạy)
5. [Hướng dẫn Upload lên GitHub và Nộp bài UTEx LMS](#5-hướng-dẫn-upload-lên-github-và-nộp-bài-utex-lms)

---

## 1. TỔNG QUAN LÝ THUYẾT SPRING SECURITY

Spring Security là framework bảo mật tiêu chuẩn mạnh mẽ trong hệ sinh thái Java & Spring Boot:
- **Cơ chế hoạt động:** Dựa trên chuỗi các bộ lọc (**Security Filter Chain**) chặn các HTTP request trước khi đến DispatcherServlet của Spring MVC.
- **Authentication (Xác thực):** Xác định danh tính người dùng (họ là ai?) thông qua thông tin đăng nhập (Username/Email và Password). Sử dụng `AuthenticationManager`, `DaoAuthenticationProvider`, `UserDetailsService`, và `PasswordEncoder` (`BCryptPasswordEncoder`).
- **Authorization (Phân quyền):** Xác định quyền hạn truy cập tài nguyên (họ được phép làm gì?). Sử dụng `.authorizeHttpRequests(...)`, `hasRole(...)`, `@EnableMethodSecurity`, `@PreAuthorize`.
- **Session Management:** Hỗ trợ quản lý phiên Stateful (Session lưu trên server, cookie `JSESSIONID`), hạn chế đăng nhập đồng thời (`maximumSessions(1)`).
- **Thymeleaf Integration:** Tích hợp với `thymeleaf-extras-springsecurity6` để hiển thị điều kiện giao diện (`sec:authorize`, `sec:authentication`).

---

## 2. CẤU TRÚC THƯ MỤC WORKSPACE

```
C:\BT9_VD1,2,3
│   pom.xml                               (Parent Aggregator POM quản lý 3 modules)
│   .gitignore                            (Bỏ qua target, .env, .idea, logs)
│   mvnw, mvnw.cmd, .mvn/                 (Maven Wrapper chuẩn)
│   README.md                             (Báo cáo tổng kết và hướng dẫn chi tiết)
│
├───springboot-security-vd1               (Ví dụ 1: Port 8088 - DB: webst4)
│   │   pom.xml
│   └───src/main/
│       ├───java/vn/iotstar/
│       │   ├───config/                   (SecurityConfig, EncodingConfig, DataInitializer)
│       │   ├───controller/               (AuthController, HomeController)
│       │   ├───dto/                      (UserDTO, LoginDTO)
│       │   ├───entity/                   (User, Role, Product)
│       │   ├───mapper/                   (UserMapper MapStruct)
│       │   ├───repository/               (UserRepository, RoleRepository)
│       │   ├───security/                 (CustomUserDetailsService)
│       │   └───service/                  (UserService, UserServiceImpl)
│       └───resources/
│           ├───application.properties
│           ├───static/css/app.css
│           └───templates/                (Layout thuần th:replace không dùng Dialect)
│
├───springboot-security-vd2               (Ví dụ 2: Port 8081 - DB: webst9)
│   │   pom.xml
│   └───src/main/
│       ├───java/vn/iotstar/
│       │   ├───config/                   (SecurityConfig, EncodingConfig, DataInitializer)
│       │   ├───controller/               (AuthController, HomeController)
│       │   ├───dto/                      (UserDTO, LoginDTO)
│       │   ├───entity/                   (User, Role)
│       │   ├───mapper/                   (UserMapper MapStruct)
│       │   ├───repository/               (UserRepository, RoleRepository)
│       │   ├───security/                 (CustomUserDetails, CustomUserDetailsService)
│       │   └───service/                  (UserService, UserServiceImpl)
│       └───resources/
│           ├───application.properties
│           ├───static/css/app.css
│           ├───static/images/            (user.png, admin.png, avatar-default.png)
│           └───templates/                (Sử dụng Thymeleaf Layout Dialect)
│
└───shop-springboot-4-1-1                 (Ví dụ 3: Port 8080 - DB: webst3)
    │   pom.xml
    │   .env                              (Biến môi trường DB, Mail SMTP, Cloudinary)
    └───src/main/
        ├───java/vn/iotstar/
        │   ├───config/                   (SecurityConfig, CloudinaryConfig, EncodingConfig, DataInitializer)
        │   ├───controller/               (AuthController, UserController, ProductController, HomeController, ErrorController)
        │   ├───dto/                      (UserDTO, ProductDTO, RegisterDTO, LoginDTO, VerifyOtpDTO, ForgotPasswordDTO, ResetPasswordDTO)
        │   ├───entity/                   (User, Role, Product, OtpToken)
        │   ├───mapper/                   (UserMapper, ProductMapper MapStruct)
        │   ├───repository/               (UserRepository, ProductRepository, RoleRepository, OtpTokenRepository)
        │   ├───security/                 (CustomUserDetails, CustomUserDetailsService)
        │   └───service/                  (UserService, ProductService, AuthService, OtpService, EmailService, CloudinaryService)
        └───resources/
            ├───application.properties
            ├───static/css/app.css
            └───templates/                (auth/, fragments/, layouts/, products/, users/, home.html, error.html)
```

---

## 3. CHI TIẾT CÁC BÀI THỰC HÀNH

### VÍ DỤ 1: `springboot-security-vd1`
- **Mục tiêu:** Xây dựng chức năng Login cơ bản với Spring Security, phân quyền theo Role, hiển thị thông tin ở `header.html` bằng **Thymeleaf thuần (không dùng Thymeleaf Layout Dialect)**.
- **Port:** `8088`
- **Database:** SQL Server `webst4`
- **Tài khoản kiểm thử:**
  - **Quản trị viên (ADMIN):** `trungnh@hcmute.edu.vn` | Password: `123456` (Truy cập được Dashboard Admin)
  - **Người dùng (USER):** `user01@gmail.com` | Password: `123456`
- **Đường dẫn truy cập:**
  - Trang chủ: [http://localhost:8088/](http://localhost:8088/)
  - Đăng nhập: [http://localhost:8088/login](http://localhost:8088/login)
  - Dashboard Admin: [http://localhost:8088/dashboard](http://localhost:8088/dashboard) (yêu cầu quyền ADMIN)

---

### VÍ DỤ 2: `springboot-security-vd2`
- **Mục tiêu:** Xây dựng chức năng **Custom Login** cho phép đăng nhập bằng cả **Username HOẶC Email**, lưu thông tin người dùng trong `CustomUserDetails` (bao gồm `id`, `username`, `email`, `fullName`, `images`, `role`), hiển thị ảnh đại diện (avatar) tròn, họ tên, email, vai trò trên `header.html` sử dụng **Thymeleaf Layout Dialect**.
- **Port:** `8081`
- **Database:** SQL Server `webst9`
- **Tài khoản kiểm thử:**
  - **Người dùng (USER):**
    - Đăng nhập bằng username: `user01` | Password: `123456`
    - HOẶC đăng nhập bằng email: `user01@gmail.com` | Password: `123456`
    - Hiển thị Avatar xanh, Họ tên: **Nguyễn Hữu Trung**
  - **Quản trị viên (ADMIN):**
    - Đăng nhập bằng: `admin` hoặc `admin@gmail.com` | Password: `123456`
    - Hiển thị Avatar đỏ, Họ tên: **Administrator**, truy cập được [http://localhost:8081/admin](http://localhost:8081/admin)
- **Đường dẫn truy cập:**
  - Trang chủ: [http://localhost:8081/](http://localhost:8081/)
  - Đăng nhập: [http://localhost:8081/login](http://localhost:8081/login)
  - Admin: [http://localhost:8081/admin](http://localhost:8081/admin)

---

### VÍ DỤ 3: `shop-springboot-4-1-1`
- **Mục tiêu:** Hệ thống quản lý Shop bán hàng hoàn chỉnh với kiến trúc phân tầng chuyên nghiệp:
  1. **Authentication:**
     - **Đăng ký (Register):** Nhập username, email, họ tên, password -> Gửi mã OTP 6 số bảo mật qua Gmail SMTP -> Chuyển đến trang xác nhận OTP -> Kích hoạt tài khoản (`enabled = true`).
     - **Xác nhận OTP & Gửi lại OTP (Resend OTP):** Mã OTP có hiệu lực 5 phút, lưu hash trong bảng `otp_tokens`, tối đa 5 lần thử. Hỗ trợ hiển thị OTP trực tiếp tại console log khi test offline.
     - **Đăng nhập (Login) & Lưu Session:** Xác thực bằng BCrypt, giới hạn 1 phiên đồng thời (`maximumSessions(1)`).
     - **Quên mật khẩu (Forgot Password):** Gửi OTP xác minh qua email -> Đặt lại mật khẩu mới.
     - **Đăng xuất (Logout):** Xóa cookie JSESSIONID, vô hiệu hóa session.
  2. **Quản lý User (Chỉ dành cho ADMIN):**
     - CRUD User (Thêm, Xem, Sửa, Xóa).
     - Tìm kiếm linh hoạt theo username, email hoặc họ tên.
     - Phân trang hiển thị dữ liệu (Pagination).
     - Đếm số lượng sản phẩm của từng User.
  3. **Quản lý Product:**
     - CRUD Product (Thêm, Sửa, Xóa).
     - Tự động gán sản phẩm cho User đang đăng nhập (`user_id`).
     - Upload ảnh sản phẩm lên đám mây **Cloudinary**, lưu định dạng `url|publicId`.
     - Tự động xóa ảnh cũ trên Cloudinary khi cập nhật ảnh mới hoặc khi xóa sản phẩm.
     - Tìm kiếm sản phẩm theo tên hoặc mô tả, phân trang hiển thị.
  4. **Dashboard:**
     - Thống kê tổng số User và tổng số Product trong hệ thống.
  5. **Công nghệ sử dụng:**
     - Spring Boot + Spring Security
     - MapStruct 1.6.3 (ánh xạ DTO <-> Entity 2 chiều)
     - Cloudinary HTTP5 SDK
     - Spring Mail (JavaMailSender)
     - Thymeleaf + Thymeleaf Layout Dialect
     - Microsoft SQL Server (`webst3`)
- **Port:** `8080`
- **Database:** SQL Server `webst3`
- **Tài khoản kiểm thử:**
  - **Admin:** `admin` | Password: `123456`
  - **User:** `trungnh` | Password: `123456`
- **Đường dẫn truy cập:**
  - Trang chủ/Dashboard: [http://localhost:8080/](http://localhost:8080/)
  - Đăng nhập: [http://localhost:8080/login](http://localhost:8080/login)
  - Đăng ký tài khoản: [http://localhost:8080/register](http://localhost:8080/register)
  - Quên mật khẩu: [http://localhost:8080/forgot-password](http://localhost:8080/forgot-password)
  - Quản lý Sản phẩm: [http://localhost:8080/products](http://localhost:8080/products)
  - Quản lý Người dùng: [http://localhost:8080/users](http://localhost:8080/users) (Quyền ADMIN)

---

## 4. HƯỚNG DẪN CÀI ĐẶT & KHỞI CHẠY

### 4.1. Chuẩn bị Cơ sở dữ liệu SQL Server
Đảm bảo dịch vụ SQL Server (MSSQLSERVER) đang chạy trên cổng mặc định `1433`.
Mở SQL Server Management Studio (SSMS) hoặc chạy lệnh sau để tạo 3 databases và kích hoạt tài khoản `sa`:
```sql
CREATE DATABASE webst3;
CREATE DATABASE webst4;
CREATE DATABASE webst9;

ALTER LOGIN sa ENABLE;
ALTER LOGIN sa WITH PASSWORD = '123';
```

### 4.2. Build toàn bộ dự án với Maven
Mở terminal tại thư mục gốc `C:\BT9_VD1,2,3` và chạy:
```bash
.\mvnw.cmd clean compile
```
Tất cả 3 modules sẽ được build thành công:
```
[INFO] Reactor Summary:
[INFO] springboot-security-vd1 1.0 ........................ SUCCESS
[INFO] springboot-security-vd2 1.0 ........................ SUCCESS
[INFO] shop-springboot-4-1-1 1.0.0 ........................ SUCCESS
[INFO] BT9 - Spring Boot Security Demos (VD1, VD2, VD3) ... SUCCESS
```

### 4.3. Khởi chạy từng bài ví dụ

- **Chạy Ví dụ 1:**
  ```bash
  .\mvnw.cmd spring-boot:run -pl springboot-security-vd1
  ```
  Truy cập: `http://localhost:8088`

- **Chạy Ví dụ 2:**
  ```bash
  .\mvnw.cmd spring-boot:run -pl springboot-security-vd2
  ```
  Truy cập: `http://localhost:8081`

- **Chạy Ví dụ 3:**
  ```bash
  .\mvnw.cmd spring-boot:run -pl shop-springboot-4-1-1
  ```
  Truy cập: `http://localhost:8080`

---

## 5. HƯỚNG DẪN UPLOAD LÊN GITHUB VÀ NỘP BÀI UTEX LMS

Sinh viên thực hiện các bước sau để đẩy toàn bộ mã nguồn lên GitHub:

### Bước 1: Tạo repository mới trên GitHub
1. Đăng nhập vào tài khoản GitHub cá nhân: [https://github.com](https://github.com)
2. Nhấn nút **New repository** (hoặc truy cập [https://github.com/new](https://github.com/new)).
3. Đặt tên repository: `BT9_SpringSecurity_TrQuocDuy_24133009` (hoặc tên theo quy ước lớp).
4. Chọn chế độ **Public**.
5. **Không** tích chọn "Add a README file" hay ".gitignore" (vì trong project đã có sẵn).
6. Nhấn nút **Create repository**.

### Bước 2: Đẩy mã nguồn từ máy lên GitHub
Mở PowerShell tại thư mục `C:\BT9_VD1,2,3` và chạy các lệnh:
```bash
# 1. Khởi tạo Git repository (nếu chưa có)
git init -b main

# 2. Thêm tất cả file vào git
git add .

# 3. Tạo commit đầu tiên
git commit -m "feat: Hoan thanh Bai tap 9 Spring Security Vi du 1, 2, 3 - Truong Quoc Duy 24133009"

# 4. Liên kết với remote repository trên GitHub (thay URL của bạn vào)
git remote add origin https://github.com/<your-username>/BT9_SpringSecurity_TrQuocDuy_24133009.git

# 5. Đẩy code lên nhánh main
git push -u origin main
```

### Bước 3: Nộp bài trên UTEx LMS
1. Copy đường link repository GitHub vừa tạo (Ví dụ: `https://github.com/your-username/BT9_SpringSecurity_TrQuocDuy_24133009`).
2. Đăng nhập vào trang UTEx LMS môn **Lập trình Web** của ThS. Nguyễn Hữu Trung.
3. Tìm mục nộp bài của **Bài tập 9 - Spring Security**.
4. Dán link GitHub vào ô nộp bài và nhấn **Lưu/Nộp bài**.
