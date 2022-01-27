package org.opencds.cqf.ruler.behavior;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.opencds.cqf.ruler.utility.Resources;

public interface ResourceCreator extends FhirContextUser {
	@SuppressWarnings("unchecked")
	default <T extends IBaseResource, I extends IIdType> T newResource(I theId) {
		checkNotNull(theId, "theId is required");
		checkArgument(theId.getResourceType() != null, "theId must have a resourceType");

		IBaseResource newResource = this.getFhirContext().getResourceDefinition(theId.getResourceType()).newInstance();
		newResource.setId(theId);
		return (T) newResource;
	}

	default <T extends IBaseResource> T newResource(Class<T> theResourceClass, String theIdPart) {
		return Resources.newResource(theResourceClass, theIdPart);
	}
}
