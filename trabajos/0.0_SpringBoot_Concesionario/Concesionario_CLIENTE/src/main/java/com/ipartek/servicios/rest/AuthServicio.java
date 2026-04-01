package com.ipartek.servicios.rest;

import com.ipartek.pojos.AuthResponse;

public interface AuthServicio {

	AuthResponse login(String user, String pass);
}
