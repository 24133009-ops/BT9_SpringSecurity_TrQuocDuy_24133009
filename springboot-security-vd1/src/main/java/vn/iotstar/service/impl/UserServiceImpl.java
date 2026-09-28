package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.User;
import vn.iotstar.mapper.UserMapper;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại: " + id));
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDTO> findAll(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), Sort.by(Sort.Direction.DESC, "id"));
        if (keyword != null && !keyword.trim().isEmpty()) {
            return userRepository.findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
                    keyword.trim(), keyword.trim(), pageable).map(userMapper::toDto);
        }
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }
}
