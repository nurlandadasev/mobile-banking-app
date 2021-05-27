package com.ma.mobilebankingapp.services.mappers;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);


    @Mappings({
            @Mapping(source = "currency.currency", target = "currency")
    })
    AccountDto mapToDto(Account account);

    List<AccountDto> mapListToDtoList(List<Account> accountList);

}
