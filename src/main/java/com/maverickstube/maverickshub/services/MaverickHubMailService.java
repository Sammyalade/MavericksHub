package com.maverickstube.maverickshub.services;

import org.springframework.stereotype.Service;
import software.xdev.brevo.client.ApiClient;
import software.xdev.brevo.client.Configuration;


@Service
public class MaverickHubMailService implements MailService{
    @Override
    public String sendMail(String email) {
        return "";
    }
}
