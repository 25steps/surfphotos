package com.drew.surfphotos.generator.alternative;



import com.drew.surfphotos.ejb.repository.jpa.StaticJPAQueryInitializer;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.Bean;

@Dependent
@TestDataGeneratorEnvironment
public class JPARepositoryFinderAlternative extends StaticJPAQueryInitializer.JPARepositoryFinder {
    @Override
    protected boolean isCandidateValid(Bean<?> bean) {
        return false;
    }
}
