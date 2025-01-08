package com.dawex.sigourney.trustframework.vc.core.integration;

import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberType;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberVC;

public interface NotaryService {

	RegistrationNumberVC getRegistrationNumberVC(String registrationNumber,
			RegistrationNumberType registrationNumberType,
			String vcId) throws NotaryServiceException;
}
