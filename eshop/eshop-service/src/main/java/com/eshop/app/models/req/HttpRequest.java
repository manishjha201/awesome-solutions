package com.eshop.app.models.req;

import java.io.Serializable;

public abstract class HttpRequest implements Serializable {
    public abstract boolean validate();
}
