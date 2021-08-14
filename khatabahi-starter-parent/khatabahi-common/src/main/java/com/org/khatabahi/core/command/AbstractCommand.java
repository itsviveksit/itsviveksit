package com.org.khatabahi.core.command;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.config.ValidatorConfig;
import com.org.khatabahi.core.aop.RestControllerAspect;
import com.org.khatabahi.core.validator.Validator;
import com.org.khatabahi.logger.KhatabahiLogger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.StringJoiner;

public abstract class AbstractCommand<T extends GenericRequest, R, S> implements Command<T, R> {
    private static final KhatabahiLogger log = KhatabahiLogger.getLogger(AbstractCommand.class);

    //logger instance
    private Validator<GenericRequest> validator;

    @Autowired
    private ValidatorConfig config;

    public Validator<GenericRequest> getValidator() {
        return validator;
    }

    public void setValidator(Validator<GenericRequest> validator) {
        this.validator = validator;
    }

    @Override
    public R execute(T request) {
        //PRE-PROCESS
        preProcess(request);

        //Validate
        validate(request);

        //PROCESS
        S model = process(request);
        R response = prepareResponse(request, model);
        //POST PROCESS
        return postProcess(response, request);
    }

    private void validate(T request){
        if(getValidator() == null){
            log.info("no validator configured for :", this.getClass().getSimpleName());
            return;
        }
        boolean enabled = true;
        StringJoiner joiner = new StringJoiner("_");
        joiner.add(this.getClass().getName());
        joiner.add(getValidator().getClass().getName());
        String validateConfigKey = joiner.toString();
        log.info("validatorConfigKey =: ", validateConfigKey);
        log.info("command =: ", this.getClass().getName());
        if(!config.getConfig().isEmpty() && config.getConfig().containsKey(validateConfigKey)){
            enabled = config.getConfig().get(validateConfigKey);
            log.info("API validation enabled in validatorCong.properties :", enabled);
        }
        log.info("validation enabled :", enabled);
        if (enabled){
            getValidator().validate(request);
        }

    }

    public abstract void preProcess(T request);

    public abstract S process(T request);

    public abstract R prepareResponse(T request, S model);

    public abstract R postProcess(R response, T request);
}
