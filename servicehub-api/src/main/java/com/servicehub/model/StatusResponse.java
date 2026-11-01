package com.servicehub.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class StatusResponse {
    private String status;
    private String versao;
    private String mensagem;
}
