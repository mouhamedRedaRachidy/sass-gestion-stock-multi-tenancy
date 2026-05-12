package com.rachidy.sassgestionstockapp.auth.services;

import com.rachidy.sassgestionstockapp.auth.request.LoginRequest;
import com.rachidy.sassgestionstockapp.auth.response.LoginResponse;

public interface AuthentificationService {

    LoginResponse login(LoginRequest loginRequest);

}
