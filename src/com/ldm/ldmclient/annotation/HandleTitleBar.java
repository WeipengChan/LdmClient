package com.ldm.ldmclient.annotation;

import java.lang.annotation.*;

/**
 * title handler
 * Created by LDM on 14-3-25. Email : nightkid-s@163.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HandleTitleBar {
    public boolean showBackBtn() default false;
    public int showBackText() default -1;
    public int showRightText() default -1;
    public int showTitleText() default -1;
}
