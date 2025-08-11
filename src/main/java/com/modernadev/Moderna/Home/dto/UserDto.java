package com.modernadev.Moderna.Home.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.modernadev.Moderna.Home.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//AddressDto là một Data Transfer Object (DTO), dùng để truyền dữ liệu địa chỉ giữa các tầng của ứng dụng (Controller ↔ Service ↔ Client API).
// Khác với Entity (@Entity), DTO không ánh xạ trực tiếp xuống bảng database, mà chủ yếu để trao đổi dữ liệu ở dạng JSON hoặc giữa các class Java.
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // (Jackson) → Khi convert sang JSON, bỏ qua các trường null, không trả về trong response.
@JsonIgnoreProperties(ignoreUnknown = true) // (Jackson) → Nếu JSON gửi lên có thêm các field lạ không khai báo trong class, sẽ bỏ qua, tránh lỗi parse.
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;

    private String phoneNumber;
    private String userRole;
    private List<OrderItemDto> orderItemDtoList;
    private AddressDto addressDto;
}
