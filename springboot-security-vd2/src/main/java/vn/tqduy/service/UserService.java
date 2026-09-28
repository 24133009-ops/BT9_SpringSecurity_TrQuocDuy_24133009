package vn.tqduy.service;

import vn.tqduy.dto.UserDTO;

public interface UserService {
    UserDTO findById(Long id);
}
