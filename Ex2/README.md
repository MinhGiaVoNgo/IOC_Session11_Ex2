# [BÀI TẬP 2 - KHÁ] QUẢN LÝ CẤP ĐỘ LOG VÀ CẤU HÌNH CONSOLE

## 1. Mục tiêu
- **Kiến thức:** Phân biệt và làm chủ các cấp độ Log (Log Levels): `TRACE < DEBUG < INFO < WARN < ERROR < OFF`.
- **Kỹ năng:** Cấu hình linh hoạt `logging.level` trong file `application.properties` để lọc thông tin cần thiết giữa môi trường Development (cần xem chi tiết SQL, Trace luồng) và Production (chỉ cần xem INFO/ERROR).

---

## 2. Cấu hình kỹ thuật trong `application.properties`

```properties
# 1. Log của toàn bộ ứng dụng ở mức INFO
logging.level.root=INFO

# 2. Log của Hibernate/JPA ở mức DEBUG (để xem câu lệnh SQL gửi xuống Database)
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.orm.jdbc.bind=TRACE

# 3. Log của package com.medical.service ở mức TRACE (mức độ chi tiết nhất)
logging.level.com.medical.service=TRACE
```

---

## 3. Phân cấp mức độ Log (Logging Level Hierarchy)

| Level | Ý nghĩa & Khi nào sử dụng |
|:---:|:---|
| **TRACE** | Mức độ chi tiết nhất, dùng để trace từng bước nhỏ vào/ra hàm (`com.medical.service`). |
| **DEBUG** | Dành cho môi trường phát triển (Dev) để chẩn đoán lỗi, hiển thị câu lệnh SQL (`org.hibernate.SQL`). |
| **INFO** | Thông điệp thông tin chung về trạng thái hoạt động của hệ thống (mức mặc định của `root`). |
| **WARN** | Cảnh báo tình huống bất thường nhưng chưa làm gián đoạn chương trình. |
| **ERROR** | Lỗi nghiêm trọng, ngoại lệ làm ảnh hưởng đến quá trình xử lý yêu cầu. |

---

## 4. Kết quả thực tế trên màn hình Console
Khi gọi API tìm kiếm bệnh nhân `GET /api/v1/patients/search?name=Nguyễn`, màn hình Console xuất hiện:
```text
2026-11-10 10:20:01.123 [INFO ] c.m.controller.PatientController     : [Controller] Nhận request tìm kiếm bệnh nhân với từ khóa: 'Nguyễn'
2026-11-10 10:20:01.125 [TRACE] c.medical.service.PatientService     : [TRACE] Bắt đầu thực thi phương thức searchPatients với từ khóa: 'Nguyễn'
2026-11-10 10:20:01.126 [DEBUG] c.medical.service.PatientService     : [DEBUG] Đang chuẩn bị gửi câu truy vấn SQL tìm kiếm bệnh nhân xuống Database...
2026-11-10 10:20:01.128 [DEBUG] org.hibernate.SQL                    : 
    select
        p1_0.id,
        p1_0.address,
        p1_0.age,
        p1_0.gender,
        p1_0.name,
        p1_0.phone 
    from
        patients p1_0 
    where
        upper(p1_0.name) like upper(?) escape '\'
2026-11-10 10:20:01.130 [DEBUG] c.medical.service.PatientService     : [DEBUG] Truy vấn hoàn tất, nhận được 1 bản ghi từ Database.
2026-11-10 10:20:01.131 [INFO ] c.medical.service.PatientService     : [INFO] Tìm kiếm bệnh nhân thành công: từ khóa='Nguyễn', số lượng kết quả=1
2026-11-10 10:20:01.132 [TRACE] c.medical.service.PatientService     : [TRACE] Kết thúc thực thi phương thức searchPatients.
```

---

## 5. Chạy kiểm thử tự động
```bash
cd M:\JAVA_BACKEND\BTVN_moi\SS11\Ex2
mvn test
```
