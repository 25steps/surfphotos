/*
 * Copyright 2017 </>DevStudy.net.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.drew.surfphotos.web.security;

import com.drew.surfphotos.model.domain.Profile;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import static com.drew.surfphotos.web.security.SecurityUtils.TEMP_PASS;

/**
 *Данный класс служит для проверки если пользователь заходит под ранее зарегистрированным аккаунтом в систему
 *
 */
public class ProfileAuthenticationToken implements RememberMeAuthenticationToken {

    private static final long serialVersionUID = 4351226293244178431L;
    private final Profile profile;

    public ProfileAuthenticationToken(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Profile getPrincipal() {
        return profile;
    }

    @Override
    public String getCredentials() {
        return TEMP_PASS;
    }
}
