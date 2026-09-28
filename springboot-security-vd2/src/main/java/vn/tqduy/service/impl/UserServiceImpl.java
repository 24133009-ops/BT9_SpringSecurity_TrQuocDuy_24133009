package vn.tqduy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tqduy.dto.UserDTO;
import vn.tqduy.entity.User;
import vn.tqduy.mapper.UserMapper;
import vn.tqduy.repository.UserRepository;
import vn.tqduy.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại: " + id));
        return userMapper.toDTO(user);
    }
}
