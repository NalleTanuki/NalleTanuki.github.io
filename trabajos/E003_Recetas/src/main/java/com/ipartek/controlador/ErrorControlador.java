package com.ipartek.controlador;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorControlador {
	@RequestMapping("/error")
    public String manejarError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int codigo = Integer.parseInt(status.toString());
            model.addAttribute("status", codigo);

            if (codigo == 404) {
                return "error/404";
            }
            if (codigo == 500) {
                return "error/500";
            }
        }

        return "error/error";
    }
}
