package org.opencds.cqf.ruler.cdshooks.builders.r4;

import java.util.List;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.Extension;
import org.opencds.cqf.ruler.cdshooks.builders.BaseBuilder;

public class AttachmentBuilder extends BaseBuilder<Attachment> {

    public AttachmentBuilder() {
        super(new Attachment());
    }

    // TODO - incomplete

    public AttachmentBuilder buildUrl(String url) {
        complexProperty.setUrl(url);
        return this;
    }

    public AttachmentBuilder buildTitle(String title) {
        complexProperty.setTitle(title);
        return this;
    }

    public AttachmentBuilder buildExtension(List<Extension> extensions) {
        complexProperty.setExtension(extensions);
        return this;
    }
}
