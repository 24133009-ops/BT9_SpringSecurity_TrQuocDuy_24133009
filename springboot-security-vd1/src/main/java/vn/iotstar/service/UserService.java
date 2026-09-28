package vn.iotstar.service;

import org.springframework.data.domain.Page;
import vn.iotstar.dto.UserDTO;

public interface UserService {
    UserDTO findById(Long id);
    Page<UserDTO> findAll(String keyword, int page, int size);
    long countUsers();
}
