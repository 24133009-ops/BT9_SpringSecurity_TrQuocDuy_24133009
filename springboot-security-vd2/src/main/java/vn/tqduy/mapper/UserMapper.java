package vn.tqduy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vn.tqduy.dto.UserDTO;
import vn.tqduy.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "roleName", source = "role.name")
    UserDTO toDTO(User user);

    @Mapping(target = "role", ignore = true)
    User toEntity(UserDTO dto);
}
