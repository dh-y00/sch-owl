package com.sch.owl.application.converter.auth;

import com.sch.owl.application.request.auth.LoginCommand;
import com.sch.owl.domain.dto.auth.LoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginAppConverter {

    LoginAppConverter INSTANCE = Mappers.getMapper(LoginAppConverter.class);

    LoginDTO converter(LoginCommand loginCommand);

}
