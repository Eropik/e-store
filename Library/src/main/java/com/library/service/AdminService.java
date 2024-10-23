package com.library.service;

import com.library.dto.AdminDto;
import com.library.model.Admin;

public interface AdminService {
Admin findByUsername(String username);

Admin save(AdminDto adminDto);


}
