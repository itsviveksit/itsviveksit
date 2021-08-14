package com.org.khatabahi.dashboard.api;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.base.response.GenericResponseBody;
import com.org.khatabahi.common.base.response.GenericResponseHeader;
import com.org.khatabahi.core.annotation.CommandMapping;
import com.org.khatabahi.core.aop.RestControllerAspect;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@Import(RestControllerAspect.class)
@RequestMapping(
        value = "api/dashboard",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class DashboardController {

    @ApiOperation(value = "To return of details base on user information")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Successfully retrieve the dashboard details")
    })
    @PostMapping(value = "v1/user/details")
    @CommandMapping(command = "userDetailsListCommand", validator = "commonValidator")
    public GenericResponse<GenericResponseHeader, GenericResponseBody> getUserDetails(@RequestBody  GenericRequest genericRequest){
        return null;
    }
}
