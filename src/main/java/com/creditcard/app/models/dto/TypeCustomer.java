package com.creditcard.app.models.dto;

import com.creditcard.app.models.documents.SubType;
import lombok.Data;

@Data
public class TypeCustomer {
    private String id;
    private EnumTypeCustomer value;
    private SubType subType;
    public enum EnumTypeCustomer {
        EMPRESARIAL, PERSONAL
    }
}
