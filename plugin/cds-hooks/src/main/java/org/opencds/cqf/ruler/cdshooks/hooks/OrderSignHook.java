package org.opencds.cqf.ruler.cdshooks.hooks;

import org.opencds.cqf.ruler.cdshooks.request.JsonHelper;
import org.opencds.cqf.ruler.cdshooks.request.Request;
import com.google.gson.JsonElement;

public class OrderSignHook extends Hook {

    public OrderSignHook(Request request) {
        super(request);
    }

    @Override
    public JsonElement getContextResources() {
        return JsonHelper.getObjectRequired(getRequest().getContext().getContextJson(), "draftOrders");
    }
}
