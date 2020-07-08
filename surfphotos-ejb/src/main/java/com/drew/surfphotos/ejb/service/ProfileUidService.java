package com.drew.surfphotos.ejb.service;


import java.util.List;

public interface ProfileUidService {

    List<String> generateProfileUidCandidates(String englishFirstName, String englishLastName);
}
