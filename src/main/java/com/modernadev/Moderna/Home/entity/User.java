package com.modernadev.Moderna.Home.entity;

import com.modernadev.Moderna.Home.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data // Tự động sinh getter/setter, toString, equals, hashCode (Lombok)
@Entity //  Đánh dấu class này là một Entity, tương ứng với một bảng trong database.
@Table(name = "users") // Chỉ định tên bảng trong database là users.
@Builder // Cho phép khởi tạo đối tượng theo kiểu Builder Pattern
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required") //  (Jakarta Validation) → Không cho phép giá trị rỗng hoặc null.
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Column(name = "phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    private UserRole userRole;

    // Tham chiếu tới tên thuộc tính user trong entity OrderItem
    // Chỉ load danh sách khi gọi getter, tránh tải dữ liệu không cần thiết
    // Khi xóa/sửa User, các OrderItem liên quan cũng bị tác động.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

    @OneToOne( fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
