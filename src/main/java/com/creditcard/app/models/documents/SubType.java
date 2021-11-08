package com.creditcard.app.models.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubType {
    private String id;
    private EnumSubType value;
    public enum EnumSubType{
        NORMAL, VIP, PYME
    }
}
