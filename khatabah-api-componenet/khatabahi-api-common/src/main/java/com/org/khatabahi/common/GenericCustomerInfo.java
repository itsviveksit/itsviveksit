package com.org.khatabahi.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"userId","groupId", "entityId", "companyId", "bankId"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericCustomerInfo {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("groupId")
    private String groupId;

    @JsonProperty("entityId")
    private String entityId;

    @JsonProperty("companyId")
    private String companyId;

    @JsonProperty("bankId")
    private String bankId;
}
