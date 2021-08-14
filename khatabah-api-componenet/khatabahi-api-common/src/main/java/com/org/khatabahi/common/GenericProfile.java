package com.org.khatabahi.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"customerInformation"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericProfile<CustomerInfo extends GenericCustomerInfo> {
    @JsonProperty("customerInformation")
    @Valid
    @NotNull(message = "customerInformation.required")
    private CustomerInfo customerInformation;
}
