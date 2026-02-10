# Sử dụng JRE 17 nhẹ để chạy app (không cần Maven)
FROM eclipse-temurin:17-jre-alpine

# Thư mục làm việc
WORKDIR /app

# Copy file jar đã được GitHub Action build sẵn và đẩy lên
# (Lệnh mv trong script của bạn đã đổi tên nó thành app.jar)
COPY app.jar app.jar

# Mở port
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]