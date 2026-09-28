package vn.tqduy.service;

import org.springframework.data.domain.Page;
import vn.tqduy.dto.UserDTO;

public interface UserService {
    UserDTO findById(Long id);
    Page<UserDTO> findAll(String keyword, int page, int size);
    long countUsers();
}
