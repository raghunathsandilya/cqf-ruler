package org.opencds.cqf.ruler.cdshooks.builders.r4;

import org.opencds.cqf.ruler.cdshooks.builders.BaseBuilder;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.RelatedArtifact;
import org.hl7.fhir.exceptions.FHIRException;

public class RelatedArtifactBuilder extends BaseBuilder<RelatedArtifact> {

    public RelatedArtifactBuilder() {
        super(new RelatedArtifact());
    }

    // TODO - incomplete

    public RelatedArtifactBuilder buildType(String type) throws FHIRException {
        complexProperty.setType(RelatedArtifact.RelatedArtifactType.fromCode(type));
        return this;
    }

    public RelatedArtifactBuilder buildType(RelatedArtifact.RelatedArtifactType type) {
        complexProperty.setType(type);
        return this;
    }

    public RelatedArtifactBuilder buildDisplay(String display) {
        complexProperty.setDisplay(display);
        return this;
    }

    public RelatedArtifactBuilder buildUrl(String url) {
        complexProperty.setUrl(url);
        return this;
    }

    public RelatedArtifactBuilder buildDocument(Attachment document) {
        complexProperty.setDocument(document);
        return this;
    }
}
